package com.scl.util;

import com.scl.annotation.Column;
import com.scl.annotation.Id;
import com.scl.annotation.Table;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
    使用反射解析实体类中注解的工具类
 */
public class AnnotationUtil {

    /*
    得到的类名
     */
    public static String getClassName(Class clz) {
        return clz.getName();
    }

    /*
    得到Table注解中的表名
     */
    public static String getTableName(Class clz) {
        if (clz.isAnnotationPresent(Table.class)) {
            Table Table = (Table) clz.getAnnotation(Table.class);
            return Table.name();
        } else {
            System.out.println("缺少Table注解");
            return null;
        }
    }

    /*
    得到主键属性和对应的字段
     */
    public static Map<String, String> getIdMapper(Class clz) {
        boolean flag = true;
        Map<String, String> map = new HashMap<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                flag = false;
                String fieldName = field.getName();
                if (field.isAnnotationPresent(Column.class)) {
                    Column Column = field.getAnnotation(Column.class);
                    String columnName = Column.name();
                    map.put(fieldName, columnName);
                    break;
                } else {
                    System.out.println("缺少Column注解");
                }
            }
        }
        if (flag) {
            System.out.println("缺少ORMId注解");
        }
        return map;
    }

    /*
    得到类中所有属性和对应的字段
     */
    public static Map<String, String> getPropMapping(Class clz) {
        Map<String, String> map = new HashMap<>();
        map.putAll(getIdMapper(clz));
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column Column = field.getAnnotation(Column.class);
                String fieldName = field.getName();
                String columnName = Column.name();
                map.put(fieldName, columnName);
            }
        }
        return map;
    }

    /*
    获得某包下面的所有类名
     */
    public static Set<String> getClassNameByPackage(String packagePath) {  //cn.itcast.orm.entity
        Set<String> names = new HashSet<>();
        String packageFile = packagePath.replace(".", "/");
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if (classpath == null) {
            classpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        }
        try {
            classpath = java.net.URLDecoder.decode(classpath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File dir = new File(classpath + packageFile);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                String name = f.getName();
                if (f.isFile() && name.endsWith(".class")) {
                    name = packagePath + "." + name.substring(0, name.lastIndexOf("."));
                    names.add(name);
                }
            }
        } else {
            System.out.println("包路径不存在");
        }
        return names;
    }
}
