package com.breakpoint.plugins;

import com.breakpoint.dto.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/11/10
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PagePlugin implements Interceptor {

    private static String dialect = "";
    /**
     * id的正则表达式
     */
    private static String pageSqlId = "";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        Method method = invocation.getMethod();


        if (target instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) target;

            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");

            /**
             * 专门匹配需要分页的查询  有的查询是不需要分页的
             */
            if (mappedStatement.getId().matches(pageSqlId)) {
                log.info("开始分页查询 start");
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject error");
                } else {

                    Connection connection = (Connection) invocation.getArgs()[0];

                    String sql = boundSql.getSql();
                    String countSql = "select count(0) from (" + sql + ") myCount";

                    log.info("总数sql 语句:{}", countSql);

                    PreparedStatement countStmt = connection.prepareStatement(countSql);

                    BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);

                    setParameters(countStmt, mappedStatement, countBS, parameterObject);

                    ResultSet rs = countStmt.executeQuery();


                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    countStmt.close();


                    PageInfo pageInfo = null;

                    /**
                     * 设置结果的返回值
                     */
                    if (parameterObject instanceof PageInfo) {
                        pageInfo = (PageInfo) parameterObject;
                        pageInfo.setPageTotalByTotalCount(count);
                    } else if (parameterObject instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) parameterObject;
                        pageInfo = (PageInfo) map.get("pageInfo");
                        if (pageInfo == null)
                            pageInfo = new PageInfo();
                        pageInfo.setPageTotalByTotalCount(count);
                    } else {
                        Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "pageInfo");
                        if (pageField != null) {
                            pageInfo = (PageInfo) ReflectHelper.getValueByFieldName(parameterObject, "pageInfo");
                            if (pageInfo == null)
                                pageInfo = new PageInfo();
                            pageInfo.setPageTotalByTotalCount(count);
                            ReflectHelper.setValueByFieldName(parameterObject, "pageInfo", pageInfo);
                        } else {
                            throw new NoSuchFieldException(parameterObject.getClass().getName());
                        }
                    }

                    String pageSql = generatePageSql(sql, pageInfo);
                    ;


                    log.info("pageSql={}", pageSql);

                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);

                }


            }
        }
        log.info("target={}", target);
        log.info("method={}", method);

        return invocation.proceed();
    }

    /**
     * 生成分页的sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, PageInfo page) {

        if (page != null && (dialect != null || !dialect.equals(""))) {

            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + (page.getDataStart()) + "," + page.getPageSize());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(")  tmp_tb where ROWNUM<=");
                pageSql.append(page.getDataEnd());
                pageSql.append(") where row_id>=");
                pageSql.append(page.getDataStart());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * 设置参数操作
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    /**
     * 设置属性
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        dialect = properties.getProperty("dialect");
        if (dialect == null || dialect.equals("")) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }


        pageSqlId = properties.getProperty("pageSqlId");
        if (pageSqlId == null || pageSqlId.equals("")) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }


    }
}
