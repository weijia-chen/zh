package com.weijia.zh.qa.dao;

import com.weijia.zh.qa.entity.ReplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.qa.vo.ReplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 回答
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Mapper
public interface ReplyDao extends BaseMapper<ReplyEntity> {

    int incrReplyLike(@Param("id")Long id,@Param("number") Long number);

//    查询用户的所有回答，按照回答时间降序,issue：0为草稿，1为已发布
    List<ReplyVo> searchReplies(@Param("userId")Long userId,@Param("issue")Integer issue);
    int incrCollect(@Param("replyId")Long replyId,@Param("number") Integer number);

//    查询指定收藏夹,按照时间降序
    List<ReplyVo> searchRepliesByFavorites(Long fid);

    //根据问题逐个查询回答，分页优化
    ReplyEntity searchReplyByProblemId(@Param("problem_id")Long problemId,@Param("current")Long current);
}
