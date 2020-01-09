package com.baizhi.cjw.dao;

import com.baizhi.cjw.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
}
