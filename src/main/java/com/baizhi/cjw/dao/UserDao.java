package com.baizhi.cjw.dao;

import com.baizhi.cjw.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    //根据性别和注册时间查询注册趋势
    public Integer queryUserByTime(@Param("sex")String sex,@Param("day")Integer day);
    //根据性别和城市查询地区分布
    public List<Map> queryUserByCity(String sex);

    //随机查询6个
    public List<User> querySixUser();

}
