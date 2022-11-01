package com.weijia.zh.qa.controller;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.Collect;
import com.weijia.zh.qa.entity.Favorites;
import com.weijia.zh.qa.service.CollectReplyService;
import com.weijia.zh.qa.vo.ReplyVo;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectReplyService collectReplyService;

    /**
     * 收藏回答
     * @param replyId ：回答的id
     * @param fid ： 收藏夹的id
     * @return
     */
    @GetMapping("/collectReply")
    public R collectReply(Long replyId,Long fid){

        log.info("replyId:{}",replyId);
        log.info("fid:{}",fid);
        boolean bl = this.collectReplyService.collectReply(replyId, fid);
        if (bl)
            return new R(RespBeanEnum.REPLY_COLLECT_SUCCESS,null);
        return new R(RespBeanEnum.REPLY_COLLECT_ERROR,null);
    }

    /**
     * 点击收藏时弹出用户的所有收藏夹，如果当前回答已经收藏到某个收藏夹中，那么该收藏夹就显示已收藏，参考csdn的实现
     * @param replyId 回答的id
     * @return
     */
    @GetMapping("/searchFavorites/{replyId}")
    public R searchFavorites(@PathVariable("replyId")Long replyId){

        log.info("searchFavorites，replyId:{}",replyId);
        List<Favorites> favorites = this.collectReplyService.searchFavorites(replyId);

        return new R(RespBeanEnum.SUCCESS,favorites);
    }

    /**
     * 取消收藏：将回答从指定的收藏夹移出
     * @param replyId 回答id
     * @param fid 收藏夹id
     * @return
     */
    @GetMapping("/delCollect/{replyId}/{fid}")
    public R delCollect(@PathVariable("replyId")Long replyId,@PathVariable("fid") Long fid){
        log.info("delCollect，replyId：{}；fid:{}",replyId,fid);
        boolean b = this.collectReplyService.delCollect(replyId, fid);
        if (b)
            return new R(RespBeanEnum.REPLY_DELETE_COLLECT_SUCCESS,null);
        return new R(RespBeanEnum.REPLY_DELETE_COLLECT_ERROR,null);
    }

    /**
     * 用户浏览指定收藏夹中的回答
     * 浏览界面提供的信息：问题标题，回答内容（前20，30个字）
     * @param fid 收藏夹id
     * @return
     */
    @GetMapping("/searchCollectReplyByFavorites/{fid}")
    public R searchCollectReplyByFavorites(@PathVariable("fid")Long fid){
        log.info("searchCollectReplyByFavorites,fid:{}",fid);
        List<ReplyVo> replyVos = this.collectReplyService.searchCollectReplyByFavorites(fid);
        return new R(RespBeanEnum.SUCCESS,replyVos);
    }

    /**
     * 创建收藏夹
     * @param name 收藏夹名称
      * @return
     */
    @GetMapping("/insertFavorites/{name}")
    public R insertFavorites(@PathVariable("name")String name){
        boolean bl =  this.collectReplyService.insertFavorites(name);
        if (bl)
            return new R(RespBeanEnum.FAVORITES_INSERT_SUCCESS,null);
        return new R(RespBeanEnum.FAVORITES_INSERT_ERROR,null);
    }

    /**
     * 删除收藏夹
     * @param fid 收藏夹id
     * @return
     */
    @GetMapping("/delFavorites/{fid}")
    public R delFavorites(@PathVariable("fid")Long fid){
        boolean bl = this.collectReplyService.delFavorites(fid);
        if(bl)
            return new R(RespBeanEnum.FAVORITES_DELETE_SUCCESS,null);
        return new R(RespBeanEnum.FAVORITES_DELETE_ERROR,null);
    }

    /**
     * 查看用户的所有收藏夹
     * @return
     */
    @GetMapping("/searchFavoritesByUser")
    public R searchFavoritesByUser(){
        List<Favorites> favorites = this.collectReplyService.searchFavorites();
        return new R(RespBeanEnum.SUCCESS,favorites);
    }
}
