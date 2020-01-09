package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //分页查所有
    public Map queryAll(Integer page,Integer rows);
    //查所有
    public List<Guru> showAllGuru();
    //添加
    public void insert(Guru guru);
    //删除
    public void delete(String[] id);
    //修改
    public void update(Guru guru);
}
