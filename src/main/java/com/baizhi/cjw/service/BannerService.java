package com.baizhi.cjw.service;

import com.baizhi.cjw.entity.Banner;
import com.baizhi.cjw.entity.BannerPageDto;

import java.util.List;

public interface BannerService {
    //添加
    public void insert(Banner banner);
    //删除
    public void delete(String[] id);
    //修改
    public void update(Banner banner);
    //查所有
    public List<Banner> queryAll();
    //分页查询
    public BannerPageDto queryByPage(Banner banner, Integer curPage, Integer pageSize);
}
