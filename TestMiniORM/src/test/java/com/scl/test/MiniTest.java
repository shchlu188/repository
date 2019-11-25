package com.scl.test;

import com.scl.core.ORMConfig;
import com.scl.core.ORMSession;
import com.scl.domain.User;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/24
 * Description:
 */
public class MiniTest {
    @Test
    public void test(){
        ORMConfig ormConfig = new ORMConfig();
        ORMSession ormSession = ormConfig.buildORMSession();
        User one = (User) ormSession.findOne(User.class, 1);
        System.out.println(one);
        ormSession.close();
    }
}
