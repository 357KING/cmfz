package com.baizhi.cjw.service.serviceImpl;

import com.baizhi.cjw.dao.AdminDao;
import com.baizhi.cjw.entity.Admin;
import com.baizhi.cjw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin selectOne(Admin admin) {
        return adminDao.selectOne(admin);
    }
}
