package com.weijia.zh.qa.service;

import com.weijia.zh.common.entity.MessageEntity;

public interface RabbitmqProduct {

    boolean messageFollow(MessageEntity message);

    boolean messageMe(MessageEntity message);

    boolean messageTopic(MessageEntity message);

    boolean messageProblem(MessageEntity message);
}
