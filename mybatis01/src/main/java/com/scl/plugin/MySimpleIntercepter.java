package com.scl.plugin;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/25
 * Description: 自定义拦截器
 */
@Intercepts( // 只能在注解中声明拦截的方法
        {
                @Signature(
                        type = Executor.class,
                        method = "query",
                        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
                )
        }
)
public class MySimpleIntercepter implements Interceptor {
    /**
     * @param invocation 代理对象，被监控的方法对象，用到的实参
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("被拦截方法之前。。。。。。。。");
        Object obj = invocation.proceed();// 执行拦截的方法

        System.out.println("被拦截方法之后。。。。。。。。");
        return obj;
    }

    /**
     * @param target 被拦截的对象，是Executor的实例对象
     *               作用：
     *               如果拦截对象有实现接口---》生成代理类
     * @return
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
