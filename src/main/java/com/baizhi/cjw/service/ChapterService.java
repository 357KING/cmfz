package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //分页查所有
    public Map queryAll(Integer page,Integer rows,String albumId);
    //添加
    public void insert(Chapter chapter);
    //删除
    public void delete(String[] id);
    //修改
    public void update(Chapter chapter);
}
