package com.scl.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/22
 * Description: Hibernate Query Language 面向对象查询
 *
 * HQL : hibernate查询语言，不做add操作
 */
public class HQLTest {
    Session session = null;
    @Before
    public void init(){
        Configuration cfg = new Configuration().configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
    }
    @After
    public void destory(){
        session.close();
    }

    /**
     * sql : select * from t_author where id = 2;
     *
     * hql = "select new Author() from Author where id = ?";
     */
    @Test
    public void query() {
        String hql = "from Author";
        Query query = session.createQuery(hql);
        List<Author> list = query.list();

        System.out.println(list);

    }
    @Test
    public void query01(){
        Query query = session.createQuery("from Author");
        List<Map<String,Object>> list=query.list();
        System.out.println(list);
    }
    // sql: select * from t_author limit 0,2;
    @Test
    public void query02(){
        Query query = session.createQuery("from Author");
        query.setFirstResult(0);
        query.setMaxResults(3);
        List list = query.list();
        System.out.println(list);


    }
    // sql: delete from t_author where id = 6;
    @Test
    public void delete(){
        Transaction tx = session.beginTransaction();
        String hql = "delete from Author where id = 5";
        Query query = session.createQuery(hql);
        int rest = query.executeUpdate();
        System.out.println(rest);
        tx.commit();

    }
   @Test
   public void update(){
       Transaction tx = session.beginTransaction();
       String hql = "update Author set name = 'zhangsan' , age =23 where id=4";
       Query query = session.createQuery(hql);
       int rest = query.executeUpdate();
       System.out.println(rest);
       tx.commit();
   }
}
