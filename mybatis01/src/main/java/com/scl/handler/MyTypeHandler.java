package com.scl.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/25
 * Description: 自定义类型处理器 int --> boolean
 */
public class MyTypeHandler implements TypeHandler {
    // author.flag = null flag设置为0
    // 把数据写入数据库
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter !=null){
            Boolean flag = (Boolean)parameter;
            if (flag == true){
                ps.setInt(i,1);
                return;
            }
        }
        ps.setInt(i,0);
    }

    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        int flag = rs.getInt(columnName);
        Boolean myFlag = Boolean.FALSE;
        if (flag ==1)
            myFlag = Boolean.TRUE;
        return myFlag;
    }

    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
