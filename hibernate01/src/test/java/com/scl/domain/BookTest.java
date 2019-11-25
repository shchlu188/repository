package com.scl.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/20
 * Description:
 */
public class BookTest {
    @Test
    public void insert() {
        Configuration cfg = new Configuration().configure();

        SessionFactory sessionFactory = cfg.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Book book = new Book();
        book.setName("斗罗大陆");
        book.setPrice(23.5);
        session.save(book);
        tx.commit();
        session.close();

    }
}
