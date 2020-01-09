package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.Article;

import java.util.Map;

public interface ArticleService {
    //查所有
    public Map queryAll(Integer page,Integer rows);
    //删除
    public void delete(String[] id);
    //添加
    public void insert(Article article);
    //修改
    public void update(Article article);
}
