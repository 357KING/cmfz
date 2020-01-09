package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.UserDao;
import com.baizhi.cjw.entity.User;
import com.baizhi.cjw.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map qqueryAllUser(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //总条数
        int i = userDao.selectCount(null);
        //总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        //当前页的数据
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("page",page);
        hashMap.put("total",i1);
        hashMap.put("records",i);
        hashMap.put("rows",users);
        return hashMap;
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        User user1 = userDao.selectOne(user);
        return user1;
    }

    @Override
    public List<Map> queryUserByCity(String sex) {
        List<Map> users = userDao.queryUserByCity(sex);
        return users;
    }

    @Override
    public Integer queryUserByTime(String sex, Integer day) {
        Integer integer = userDao.queryUserByTime(sex, day);
        return integer;
    }

    @Override
    public List<User> quwerySixUser(String id) {
        List<User> users = userDao.querySixUser();
        for (User user : users) {
            //判断获取的用户是否是自己
            if (id.equals(user.getId())){
                //当前用户是自己，从该集合中移除
                users.remove(user);
            }else {
                //获取子集合的前五个
                users = users.subList(0, 4);
            }
        }
        return users;
    }

    @Override
    public void delete(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }
}
