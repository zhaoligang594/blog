package com.android.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstimateTableExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public EstimateTableExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEsIdIsNull() {
            addCriterion("es_id is null");
            return (Criteria) this;
        }

        public Criteria andEsIdIsNotNull() {
            addCriterion("es_id is not null");
            return (Criteria) this;
        }

        public Criteria andEsIdEqualTo(Long value) {
            addCriterion("es_id =", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdNotEqualTo(Long value) {
            addCriterion("es_id <>", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdGreaterThan(Long value) {
            addCriterion("es_id >", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("es_id >=", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdLessThan(Long value) {
            addCriterion("es_id <", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdLessThanOrEqualTo(Long value) {
            addCriterion("es_id <=", value, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdIn(List<Long> values) {
            addCriterion("es_id in", values, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdNotIn(List<Long> values) {
            addCriterion("es_id not in", values, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdBetween(Long value1, Long value2) {
            addCriterion("es_id between", value1, value2, "esId");
            return (Criteria) this;
        }

        public Criteria andEsIdNotBetween(Long value1, Long value2) {
            addCriterion("es_id not between", value1, value2, "esId");
            return (Criteria) this;
        }

        public Criteria andEsTextIsNull() {
            addCriterion("es_text is null");
            return (Criteria) this;
        }

        public Criteria andEsTextIsNotNull() {
            addCriterion("es_text is not null");
            return (Criteria) this;
        }

        public Criteria andEsTextEqualTo(String value) {
            addCriterion("es_text =", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextNotEqualTo(String value) {
            addCriterion("es_text <>", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextGreaterThan(String value) {
            addCriterion("es_text >", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextGreaterThanOrEqualTo(String value) {
            addCriterion("es_text >=", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextLessThan(String value) {
            addCriterion("es_text <", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextLessThanOrEqualTo(String value) {
            addCriterion("es_text <=", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextLike(String value) {
            addCriterion("es_text like", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextNotLike(String value) {
            addCriterion("es_text not like", value, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextIn(List<String> values) {
            addCriterion("es_text in", values, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextNotIn(List<String> values) {
            addCriterion("es_text not in", values, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextBetween(String value1, String value2) {
            addCriterion("es_text between", value1, value2, "esText");
            return (Criteria) this;
        }

        public Criteria andEsTextNotBetween(String value1, String value2) {
            addCriterion("es_text not between", value1, value2, "esText");
            return (Criteria) this;
        }

        public Criteria andUIdIsNull() {
            addCriterion("u_id is null");
            return (Criteria) this;
        }

        public Criteria andUIdIsNotNull() {
            addCriterion("u_id is not null");
            return (Criteria) this;
        }

        public Criteria andUIdEqualTo(Integer value) {
            addCriterion("u_id =", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotEqualTo(Integer value) {
            addCriterion("u_id <>", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThan(Integer value) {
            addCriterion("u_id >", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("u_id >=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThan(Integer value) {
            addCriterion("u_id <", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThanOrEqualTo(Integer value) {
            addCriterion("u_id <=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdIn(List<Integer> values) {
            addCriterion("u_id in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotIn(List<Integer> values) {
            addCriterion("u_id not in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdBetween(Integer value1, Integer value2) {
            addCriterion("u_id between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotBetween(Integer value1, Integer value2) {
            addCriterion("u_id not between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andDyIdIsNull() {
            addCriterion("dy_id is null");
            return (Criteria) this;
        }

        public Criteria andDyIdIsNotNull() {
            addCriterion("dy_id is not null");
            return (Criteria) this;
        }

        public Criteria andDyIdEqualTo(Integer value) {
            addCriterion("dy_id =", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdNotEqualTo(Integer value) {
            addCriterion("dy_id <>", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdGreaterThan(Integer value) {
            addCriterion("dy_id >", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dy_id >=", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdLessThan(Integer value) {
            addCriterion("dy_id <", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdLessThanOrEqualTo(Integer value) {
            addCriterion("dy_id <=", value, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdIn(List<Integer> values) {
            addCriterion("dy_id in", values, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdNotIn(List<Integer> values) {
            addCriterion("dy_id not in", values, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdBetween(Integer value1, Integer value2) {
            addCriterion("dy_id between", value1, value2, "dyId");
            return (Criteria) this;
        }

        public Criteria andDyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dy_id not between", value1, value2, "dyId");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIsNull() {
            addCriterion("gmt_update is null");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIsNotNull() {
            addCriterion("gmt_update is not null");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateEqualTo(Date value) {
            addCriterion("gmt_update =", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotEqualTo(Date value) {
            addCriterion("gmt_update <>", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateGreaterThan(Date value) {
            addCriterion("gmt_update >", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_update >=", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateLessThan(Date value) {
            addCriterion("gmt_update <", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_update <=", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIn(List<Date> values) {
            addCriterion("gmt_update in", values, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotIn(List<Date> values) {
            addCriterion("gmt_update not in", values, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateBetween(Date value1, Date value2) {
            addCriterion("gmt_update between", value1, value2, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_update not between", value1, value2, "gmtUpdate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table estimate_table
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table estimate_table
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}