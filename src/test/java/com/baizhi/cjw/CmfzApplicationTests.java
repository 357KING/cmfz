package com.baizhi.cjw;

import com.baizhi.cjw.dao.AdminDao;
import com.baizhi.cjw.entity.Admin;
import com.baizhi.cjw.entity.Banner;
import com.baizhi.cjw.entity.BannerPageDto;
import com.baizhi.cjw.service.BannerService;
import com.baizhi.cjw.service.serviceImpl.BannerServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CmfzApplicationTests {

    @Autowired
    AdminDao adminDao;
    @Test
    public void contextLoads() {
        Admin admin = new Admin(null,"admin",null);
        //admin.setUsername("admin");
        //admin.setPassword("admin");
        //admin.setId("1");
        Admin list = adminDao.selectOne(admin);
        System.out.println("list = " + list);
    }

    @Autowired
    BannerService bannerService;

    @Test
    public void name() {
        BannerPageDto bannerPageDto = bannerService.queryByPage(null, 3, 5);
        System.out.println(bannerPageDto);
    }
}
