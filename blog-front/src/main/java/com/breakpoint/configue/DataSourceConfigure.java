package com.breakpoint.configue;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/29 0029.
 */
@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfigure {


    @Value("${spring.datasource.driverclassName}")
    String driverclassName;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.initialSize}")
    int initialSize;
    @Value("${spring.datasource.minIdle}")
    int minIdle;
    @Value("${spring.datasource.maxActive}")
    int maxActive;//=20
    @Value("${spring.datasource.maxWait}")
    long maxWait;//=60000
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    String validationQuery;//=SELECT 1 FROM DUAL
    @Value("${spring.datasource.testWhileIdle}")
    boolean testWhileIdle;//=true
    @Value("${spring.datasource.testOnBorrow}")
    boolean testOnBorrow;//=false
    @Value("${spring.datasource.testOnReturn}")
    boolean testOnReturn;//=false
    @Value("${spring.datasource.poolPreparedStatements}")
    boolean poolPreparedStatements;//=true
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    int maxPoolPreparedStatementPerConnectionSize;//=20
    String filters;//=stat,wall,log4j
    String connectionProperties;//=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

    /**
     * 获取数据源
     *
     * @return
     */
    @Primary
    @Bean
    public DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverclassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        List<String> list=new ArrayList<>(1);
        list.add("set names utf8mb4");
        druidDataSource.setConnectionInitSqls(list);
        //druidDataSource.setFilters(filters);
        //druidDataSource.setConnectProperties(connectionProperties);
        return druidDataSource;
    }

}
