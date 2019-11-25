package com.scl.factory;

import com.scl.domain.Author;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/25
 * Description: 自定义objectFactory
 */
public class MyObjectFactory extends DefaultObjectFactory {
    // 重新定义Author类实例对象的创建规则，其他类是咧对象不做改变
    @Override
    public Object create(Class type) {
        if (type == Author.class) {
            // 依靠父类创建Author类实例对象
            Author author = (Author) super.create(type);
            // 设置规则
            author.setCountry("China");
            return author;
        }
        return super.create(type);
    }
}
