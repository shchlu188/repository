package com.scl.dao;

import com.scl.domain.Author;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description:
 */
public class AuthorDaoTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private AuthorDao mapper;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(AuthorDao.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findAll() throws IOException {

        List<Author> authors = mapper.findAll();
        System.out.println(authors);
        sqlSession.close();
    }

    @Test
    public void insertOne() {
        Author author = new Author();
        author.setName("赵本上");
        author.setAge(32);
        author.setFlag(true);
        mapper.insertOne(author);
        sqlSession.commit();


    }

}