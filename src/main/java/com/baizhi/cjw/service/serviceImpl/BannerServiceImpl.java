package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.BannerDao;
import com.baizhi.cjw.entity.Banner;
import com.baizhi.cjw.entity.BannerPageDto;
import com.baizhi.cjw.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerDao bannerDao;
    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public void delete(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));

        /*
        * 假删除
        * */
        //根据id查询
        //Banner banner1 = bannerDao.selectOne(banner);
        //修改banner状态
        //banner1.setStatus("0");
        //bannerDao.updateByPrimaryKeySelective(banner1);
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public List<Banner> queryAll() {
        return bannerDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public BannerPageDto queryByPage(Banner banner, Integer curPage, Integer pageSize) {
        BannerPageDto dto = new BannerPageDto();
        //设置当前页
        dto.setPage(curPage);
        //总行数
        int count = bannerDao.selectCount(banner);
        dto.setRecords(count);
        //总页数
        dto.setTotal(count%pageSize==0 ? count/pageSize : count/pageSize+1);
        //当前页的数据行(当前页需进行计算，通用mapper分页从0开始)
        dto.setRows(bannerDao.selectByRowBounds(banner,new RowBounds((curPage-1)*pageSize,pageSize)));
        return dto;
    }
}
