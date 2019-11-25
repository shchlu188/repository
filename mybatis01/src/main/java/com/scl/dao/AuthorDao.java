package com.scl.dao;

import com.scl.domain.Author;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description:
 */
public interface AuthorDao {
    List<Author> findAll();

    void insertOne(Author author);
}
