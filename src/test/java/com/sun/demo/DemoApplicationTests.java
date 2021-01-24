package com.sun.demo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.demo.entity.User;
import com.sun.demo.mapper.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {


    @Autowired
    private UserDao userDao;


    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("22144");
        for (int i = 0; i<10 ;i++){
            userDao.insert(user);
        }
        Page<User> objects = PageHelper.startPage(1, 5);
        List<User> users = userDao.selectList(null);
        PageInfo pageInfo = new PageInfo(users, 5);
        System.out.println();
    }

}
