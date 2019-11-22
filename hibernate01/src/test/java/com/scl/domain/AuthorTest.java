package com.scl.domain;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/20
 * Description:
 */
public class AuthorTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;

    @Before
    public void init() {
        Configuration cfg = new Configuration().configure();
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }

    @After
    public void destory() {
        tx.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void save() {
        Author author = new Author();
        author.setName("孔子");
        author.setAge(101);
        session.save(author);

    }

    /**
     * 都是根据id查询一条数据
     * get: id不存在，返回null ------->直接加载
     * load: id不存在，抛异常   ------>懒加载
     */
    @Test
    public void demo01() {
//        Author author = session.get(Author.class, 1);
//        System.out.println(author);
        Author load = session.load(Author.class, 1);
        System.out.println(load);
    }

    /**
     * 1、先根据id获取对象，然后删除对象
     * 2、直接创建对象，设置id属性，然后删除
     */
    @Test
    public void delete() {
        Author author = session.get(Author.class, 2);
        session.delete(author);
    }

    /**
     * 更新
     * update:有id会更新，没有会报错
     * save:有id会更新，没有会报错
     * saveOrUpdate:判断是否有id;有就更新数据，无就保存数据
     */
    @Test
    public void update() {
        for (int i = 0; i < 10; i++) {
            Author author = new Author();
            author.setName("孔子"+i);
            author.setAge(12+i);
            session.saveOrUpdate(author);
        }
//        session.update(author);
//        session.save(author);

    }

    @Test
    public void test01(){
        Criteria criteria = session.createCriteria(Author.class);
        List<Author> list = criteria.list();
        System.out.println(list);
//        Author author = session.find(Author.class, 1);
//        System.out.println(author);
    }


}
