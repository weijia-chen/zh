package com.weijia.zh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.user.entity.MessageTopicEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 14:00:41
 */
public interface MessageTopicService {


    R<RespData> searchMessageTopics(Integer current);
}

