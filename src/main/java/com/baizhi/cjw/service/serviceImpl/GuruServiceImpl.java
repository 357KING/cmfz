package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.GuruDao;
import com.baizhi.cjw.entity.Guru;
import com.baizhi.cjw.service.GuruService;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAll(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //总条数
        int i = guruDao.selectCount(null);
        //总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        //当前页的数据
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("page",page);
        hashMap.put("records",i);
        hashMap.put("total",i1);
        hashMap.put("rows",gurus);
        return hashMap;
    }

    @Override
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> showAllGuru() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public void delete(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
}
