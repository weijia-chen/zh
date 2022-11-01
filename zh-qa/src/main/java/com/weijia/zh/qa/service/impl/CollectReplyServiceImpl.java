package com.weijia.zh.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.dao.CollectDao;
import com.weijia.zh.qa.dao.FavoritesDao;
import com.weijia.zh.qa.dao.ReplyDao;
import com.weijia.zh.qa.entity.Collect;
import com.weijia.zh.qa.entity.Favorites;
import com.weijia.zh.qa.service.CollectReplyService;
import com.weijia.zh.qa.vo.ReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CollectReplyServiceImpl implements CollectReplyService {

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private FavoritesDao favoritesDao;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;
    /**
     * 收藏回答到收藏夹:
     * 1.写入数据率
     * 2.回答的收藏数量加1
     * @param replyId 回答id
     * @param fid 收藏夹id
     * @return
     */
    @Override
    public boolean collectReply(Long replyId, Long fid) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        Collect collect = new Collect();
        collect.setCollectTime(new Date());
        collect.setReplyId(replyId);
        collect.setFavoritesId(fid);
        collect.setUserId(user.getId());

        try {
            this.collectDao.insert(collect);
//            收藏数及时统计，不再以标记为主
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long countCollectReply(Long replyId) {
       return this.collectDao.selectCountCollectReplyByUser(replyId);
    }

    /**
     * 1.查询所有收藏夹
     * @return
     */
    @Override
    public List<Favorites> searchFavorites() {

        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.orderByDesc("id");//id降序，即时间降序
        return this.favoritesDao.selectList(wrapper);

    }
    /**
     * 1.查询用户的所有收藏夹
     * 2.查询用户关注这篇博客的所有收藏夹
     * 3.在所有收藏夹中标记
     * @return
     */
    @Override
    public List<Favorites> searchFavorites(Long replyId) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        List<Favorites> favorites = this.searchFavorites();
        HashSet<Long> set = this.collectDao.searchFavoritesIdByReplyIdAndUserId(user.getId(), replyId);
        for (Favorites favorite : favorites) {
            if (set.contains(favorite.getId())){
                favorite.setFlag(1);
            }
        }
        return favorites;
    }

    @Override
    public boolean delCollect(Long replyId, Long fid) {

        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("reply_id",replyId);
        wrapper.eq("favorites_id",fid);

        try {
            this.collectDao.delete(wrapper);
//          回答收藏数减1
            this.replyDao.incrCollect(replyId,-1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 查询指定收藏夹的回答
     * @param fid
     * @return
     */
    @Override
    public List<ReplyVo> searchCollectReplyByFavorites(Long fid) {
        return this.replyDao.searchRepliesByFavorites(fid);
    }

    @Override
    public boolean insertFavorites(String name) {
        UserEntity user = HandlerInterceptor.loginUser.get();
        Favorites favorites = new Favorites();
        favorites.setName(name);
        favorites.setUserId(user.getId());
        try {
            int insert = this.favoritesDao.insert(favorites);
            if (insert<=0)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 1、验证是否是收藏夹的主人
     * 2、删除收藏夹,收藏记录
     * @param fid
     * @return
     */
    @Override
    public boolean delFavorites(Long fid) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("id",fid);
        boolean exists = this.favoritesDao.exists(wrapper);
        if (!exists)
            return false;
        this.favoritesDao.deleteById(fid);
        return true;
    }
}
