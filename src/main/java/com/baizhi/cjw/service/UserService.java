package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查所有
    public Map qqueryAllUser(Integer page,Integer rows);
    //添加
    public void insert(User user);
    //修改
    public void update(User user);
    //删除
    public void delete(String[] id);
    //根据时间查询
    public Integer queryUserByTime(String sex,Integer day);
    //根据性别和城市查询地区分布
    public List<Map> queryUserByCity(String sex);
    //根据手机号查一个
    public User queryByPhone(String phone);
    //随机查6个用户
    public List<User> quwerySixUser(String id);
}
