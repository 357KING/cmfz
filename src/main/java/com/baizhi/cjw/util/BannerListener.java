package com.baizhi.cjw.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.cjw.dao.BannerDao;
import com.baizhi.cjw.entity.Banner;

import java.util.ArrayList;
import java.util.List;

public class BannerListener extends AnalysisEventListener<Banner> {
    List<Banner> list = new ArrayList<Banner>();

    @Override
    // 针对每行数据 进行的Banner实体类封装
    public void invoke(Banner banners, AnalysisContext analysisContext) {
        list.add(banners);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
         BannerDao bannerDao = (BannerDao) ToMyBean.getByClazz(BannerDao.class);
        for (Banner banner : list) {
         bannerDao.insert(banner);
            System.out.println("banner = " + banner);
        }
    }
}
