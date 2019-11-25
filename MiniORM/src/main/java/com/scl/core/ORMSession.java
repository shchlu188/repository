package com.scl.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/24
 * Description:
 */
public class ORMSession {
    private Connection connection;

    public ORMSession(Connection connection) {
        this.connection = connection;
    }

    // 保存数据
    public void save(Object entity) throws IllegalAccessException {
        // 从ORMConfig中获取映射信息
        List<Mapper> mapperList = ORMConfig.mapperList;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        // 遍历集合，从集合中找到和entity参数相对应的mapper对象
        for (Mapper mapper : mapperList) {
            if (mapper.getClassName().equals(entity.getClass().getName())) {
                String tableName = mapper.getTableName();
                sb.append("insert into ").append(tableName).append("(");
                sb1.append(") values (");
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    // 根据属性得到字段名
                    String columnName = mapper.getPropMapper().get(field.getName());
                    // 根据属性得到值
                    String columnValue = field.get(entity).toString();
                    sb.append(columnName).append(',');
                    sb1.append(columnValue).append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb1.length() - 1).append(')');
                break;
            }
        }
        sb.append(sb1);
        System.out.println("MiniORM-save:" + sb.toString());
        String insertSql = sb.toString();
        updateSql(insertSql);


    }

    // 执行sql
    private void updateSql(String sql) {
        // jdbc
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据主键删除 delete from 表名 where 主键 = 值
    public void delete(Object entity) throws Exception {
        // 从ORMConfig中获取映射信息
        List<Mapper> mapperList = ORMConfig.mapperList;
        StringBuilder sb = new StringBuilder();
        sb.append("delete from").append(" ");
        StringBuilder sb1 = new StringBuilder();
        // 遍历集合，从集合中找到和entity参数相对应的mapper对象
        for (Mapper mapper : mapperList) {
            if (mapper.getClassName().equals(entity.getClass().getName())) {
                String tableName = mapper.getTableName();
                sb.append(tableName).append(" ").append("where");
                Object[] idProp = mapper.getIdMapper().keySet().toArray();
                Object[] idColumn = mapper.getIdMapper().values().toArray();
                Field field = entity.getClass().getDeclaredField(idProp[0].toString());
                field.setAccessible(true);
                String idValue = field.get(entity).toString();
                sb.append(idColumn).append(" = ").append(idValue);
                break;
            }
        }
        updateSql(sb.toString());
    }

    // 根据主键查询
    // select * from 表名 where 主键字段 = 值;
    public Object findOne(Class clz, Object id) {
        List<Mapper> mapperList = ORMConfig.mapperList;
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        for (Mapper mapper : mapperList) {
            if (mapper.getClassName().equals(clz.getName())) {
                String tableName = mapper.getTableName();
                Object[] idColumn = mapper.getIdMapper().values().toArray();
                sb.append(tableName).append(" ").append("where").append(" ")
                        .append(idColumn[0].toString()).append(" = ").append(id);
                break;
            }
        }
        System.out.println("MiniORM-findOne:" + sb.toString());
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        Object obj = null;

        try {
            prepareStatement = connection.prepareStatement(sb.toString());
            resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                obj = clz.newInstance();
                for (Mapper mapper : mapperList) {
                    if (mapper.getClassName().equals(clz.getName())) {
                        // 得到存有属性和字段的信息
                        Map<String, String> propMapper = mapper.getPropMapper();
                        // 遍历拿到属性名和字段名
                        Set<String> keySet = propMapper.keySet();
                        for (String prop : keySet) {
                            String column = propMapper.get(prop);
                            Field field = clz.getDeclaredField(prop);
                            field.setAccessible(true);
                            field.set(obj, resultSet.getObject(column));
                        }
                    }
                    break;
                }
            } else {
                return null;
            }
            // 封装结果集
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(prepareStatement, resultSet,connection);
        }

        return obj;

    }

    private void close(PreparedStatement prepareStatement, ResultSet resultSet, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (prepareStatement != null) {
            try {
                prepareStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                this.connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭连接
    public void close() {
        close(null, null, connection);
    }

}
