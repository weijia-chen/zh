package com.weijia.zh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijia.zh.user.dao.MessageFollowDao;
import com.weijia.zh.user.dao.MessageMeDao;
import com.weijia.zh.user.dao.MessageProblemDao;
import com.weijia.zh.user.dao.MessageTopicDao;
import com.weijia.zh.user.entity.MessageFollowEntity;
import com.weijia.zh.user.service.MessageManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageManageServiceImpl implements MessageManageService {

    @Autowired
    private MessageFollowDao messageFollowDao;

    @Autowired
    private MessageMeDao messageMeDao;

    @Autowired
    private MessageTopicDao messageTopicDao;

    @Autowired
    private MessageProblemDao messageProblemDao;
    /**
     * 所有消息表全部删除
     * @param problemId
     * @return
     */
    @Override
    public boolean delMessageByProblemId(Long problemId) {

        Map<String,Object> map = new HashMap<>();
        map.put("problem_id",problemId);
        this.messageFollowDao.deleteByMap(map);
        this.messageProblemDao.deleteByMap(map);
        this.messageTopicDao.deleteByMap(map);
        this.messageMeDao.deleteByMap(map);
        return true;
    }

    @Override
    public boolean delMessageByReplyId(Long replyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("reply_id",replyId);
        this.messageMeDao.deleteByMap(map);
        this.messageProblemDao.deleteByMap(map);
        this.messageFollowDao.deleteByMap(map);
        return true;
    }
}
