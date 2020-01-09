package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.ChapterDao;
import com.baizhi.cjw.entity.Chapter;
import com.baizhi.cjw.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAll(Integer page,Integer rows,String albumId) {
        Chapter chapter = new Chapter();
        //设置所属专辑id
        chapter.setAlbumId(albumId);
        // records 总条数 page 当前页 rows 数据 total 总页数
        HashMap hashMap = new HashMap();
        //总条数
        Integer records = chapterDao.selectCount(chapter);
        //总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        //展示对应的专辑下的文章列表当前页数据
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",chapters);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void delete(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }
}
