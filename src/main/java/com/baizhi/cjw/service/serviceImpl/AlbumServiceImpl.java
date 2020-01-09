package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.AlbumDao;
import com.baizhi.cjw.entity.Album;
import com.baizhi.cjw.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    //分页查询
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAll(Integer page,Integer rows) {
        // records 总条数 page 当前页 rows 数据 total 总页数
        HashMap hashMap = new HashMap();
        //总条数
        Integer records = albumDao.selectCount(null);
        //总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",albums);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    public void insert(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void delete(String[] ids) {
        albumDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }
}
