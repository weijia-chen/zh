package com.weijia.zh.qa.vo;

import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 问题详情页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProblemAndReply {

    private ProblemEntity problem;
    private List<ReplyEntity> replies; //按时间降序评论
    private List<ReplyEntity> hotReplies; //5条热回复，点赞最多的五条。直接在数据库查
}
