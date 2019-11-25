package com.scl.domain;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/23
 * Description:
 */
public class Author {

    private Integer id;

    private String name;

    private Integer age;

    private boolean flag;
    // 数据库中不存在此字段，可以通过自定义ObjectFactory来定义类的实例的属性设置
    private String country;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                ", country='" + country + '\'' +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

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
