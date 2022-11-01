package com.weijia.zh.manage.service.impl;

import com.weijia.zh.manage.dao.CauseDao;
import com.weijia.zh.manage.entity.Cause;
import com.weijia.zh.manage.service.CauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CauseServiceImpl implements CauseService {

    @Autowired
    private CauseDao causeDao;

    @Override
    public List<Cause> searchCauseList() {
        return this.causeDao.selectList(null);
    }
}
