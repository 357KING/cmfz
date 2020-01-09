package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.ArticleDao;
import com.baizhi.cjw.entity.Article;
import com.baizhi.cjw.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAll(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //总条数
        int i = articleDao.selectCount(null);
        //总页数
        int i1 = i % rows == 0 ? i / rows : i / rows + 1;
        //当前页的数据
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        //将数据存入map
        hashMap.put("page",page);
        hashMap.put("total",i1);
        hashMap.put("records",i);
        hashMap.put("rows",articles);
        return hashMap;
    }

    @Override
    public void insert(Article article) {
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.insert(article);
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }
}
