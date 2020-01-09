package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.Album;

import java.util.Map;

public interface AlbumService {
    //查所有
    public Map queryAll(Integer page,Integer rows);
    //添加
    public void insert(Album album);
    //删除
    public void delete(String[] ids);
    //修改
    public void update(Album album);
}
