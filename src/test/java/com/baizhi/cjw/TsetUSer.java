package com.baizhi.cjw;

import com.baizhi.cjw.dao.UserDao;
import com.baizhi.cjw.entity.User;
import com.baizhi.cjw.service.UserService;
import com.baizhi.cjw.util.SmsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest
public class TsetUSer {
    @Autowired
    UserService userService;

    @Test
    public void testlog() {
        User user = new User();
        user.setPhone("465");
        User user1 = userDao.selectOne(user);
        System.out.println("user1 = " + user1);
    }

    @Test
    public void test1(){
        List users = userService.queryUserByCity("0");
        for (Object user : users) {
            System.out.println("user = " + user);
        }
    }

    @Autowired
    UserDao userDao;
    @Test
    public void test2(){
        List list = userDao.queryUserByCity("0");
        System.out.println("list = " + list);
    }

    @Test
    public void test15() {
        SmsUtil.send("15566286920","4564");
    }


    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void hhh() {
        String h = stringRedisTemplate.opsForValue().get("h");
        System.out.println("h = " + h);
    }

    @Test
    public void te() {
        List<User> users = userDao.querySixUser();
        for (User user : users) {
            System.out.println("user = " + user);

        }
    }

    @Test
    void name() {
        List<User> users = userService.quwerySixUser("1");
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
}
