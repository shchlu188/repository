package com.scl.core;

import com.scl.util.AnnotationUtil;
import com.scl.util.Dom4jUtil;
import org.dom4j.Document;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description: 解析核心配置文件类
 */
public class ORMConfig {
    private static String classpath; //classpath路径

    private static File cfgFile; // 核心配置文件

    private static Map<String, String> propConfig; // 储存标签中的数据

    private static Set<String> mappingSet; //映射文件配置路径

    private static Set<String> entitySet; // 实体类

    public static List<Mapper> mapperList; // 映射信息

    static {
        // 得到classpath路径
        classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 转码
        try {
            classpath = URLDecoder.decode(classpath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("encoding is trouble");
            e.printStackTrace();
        }
        // 获取核心配置文件
        cfgFile = new File(classpath + "miniORM.cfg.xml");
        if (cfgFile.exists()) {
            // 解析配置文件
            Document document = Dom4jUtil.getXMLByFilePath(cfgFile.getPath());
            propConfig = Dom4jUtil.Elements2Map(document, "property", "name");
            mappingSet = Dom4jUtil.Elements2Set(document, "mapping", "resource");
            entitySet = Dom4jUtil.Elements2Set(document, "entity", "package");
        } else {
            cfgFile = null;
            System.out.println("not Found configuration file: miniORM.cfg.xml");
        }
    }

    // 从propConfig获取数据连接数据库
    private Connection getConnection() throws Exception {
        String url = propConfig.get("connection.url");
        String driverClass = propConfig.get("connection.driverClass");
        String username = propConfig.get("connection.username");
        String password = propConfig.get("connection.password");

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
        return connection;
    }

    // 解析mapping--->mapperList
    private void getMapping() throws ClassNotFoundException {
        mapperList = new ArrayList<>();
        parseXml();
        parseAnnotation();
    }
    // 2、解析注解
    private void parseAnnotation() throws ClassNotFoundException {
        for (String packagePath : entitySet) {
            Set<String> nameSet = AnnotationUtil.getClassNameByPackage(packagePath);
            for (String name : nameSet) {
                Class<?> clazz = Class.forName(name);
                String className = AnnotationUtil.getClassName(clazz);
                String tableName = AnnotationUtil.getTableName(clazz);
                Map<String, String> idMapper = AnnotationUtil.getIdMapper(clazz);
                Map<String, String> propMapping = AnnotationUtil.getPropMapping(clazz);
                Mapper mapper = getMapper(className, tableName, idMapper, propMapping);
                mapperList.add(mapper);
            }
        }
    }
    // 1、解析xml
    private void parseXml() {
        for (String xmlPath : mappingSet) {
            Document document = Dom4jUtil.getXMLByFilePath(classpath + xmlPath);
            String className = Dom4jUtil.getPropValue(document, "class", "name");
            String table = Dom4jUtil.getPropValue(document, "class", "table");
            Map<String, String> id = Dom4jUtil.ElementsID2Map(document);
            Map<String, String> mapping = Dom4jUtil.Elements2Map(document);

            Mapper mapper = getMapper(className, table, id, mapping);

            mapperList.add(mapper);
        }
    }

    private Mapper getMapper(String className, String table, Map<String, String> id, Map<String, String> mapping) {
        Mapper mapper = new Mapper();
        mapper.setClassName(className);
        mapper.setTableName(table);
        mapper.setIdMapper(id);
        mapper.setPropMapper(mapping);
        return mapper;
    }

    public ORMSession buildORMSession(){
        // 连接数据库
        try {
            Connection connection  = getConnection();
            // 得到映射数据
            getMapping();
            // 创建ORMSession
            return new ORMSession(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
