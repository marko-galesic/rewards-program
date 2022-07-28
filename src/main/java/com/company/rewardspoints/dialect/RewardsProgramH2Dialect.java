package com.company.rewardspoints.dialect;


import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class RewardsProgramH2Dialect extends H2Dialect {
    public RewardsProgramH2Dialect() {
        super();
        registerFunction("DATE_TRUNC", new StandardSQLFunction("DATE_TRUNC"));
    }
}
