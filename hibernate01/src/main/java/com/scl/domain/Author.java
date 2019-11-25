package com.scl.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description:
 */
@Entity
@Table(name = "t_author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author_name")
    private String name;

    @Column(name = "author_age")
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
