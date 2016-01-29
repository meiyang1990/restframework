/*
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.restx.persist.typehandler;


import com.netfinworks.common.util.money.Money;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p></p>
 *
 * @author zhangjiewen
 * @version $Id: MoneyTypeHandler.java, v 0.1 13-12-13 下午5:47 zhangjiewen Exp $
 */
@MappedTypes(Money.class)
public class MoneyTypeHandler extends BaseTypeHandler<Money> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setBigDecimal(i,parameter.getAmount());
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        BigDecimal value = rs.getBigDecimal(columnName);
        if(value != null){
            return new Money(value);
        }
        return null;
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal value = rs.getBigDecimal(columnIndex);
        if(value != null){
            return new Money(value);
        }
        return null;
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        BigDecimal value = cs.getBigDecimal(columnIndex);
        if(value != null){
            return new Money(value);
        }
        return null;
    }
}
