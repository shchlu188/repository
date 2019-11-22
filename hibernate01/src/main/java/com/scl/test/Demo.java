package com.scl.test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/21
 * Description:E:
 */
public class Demo {
    public User getUser() {
        User user = new User("aa");
        return user;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Date());
        }
    }
}
