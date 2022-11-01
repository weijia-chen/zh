package com.weijia.zh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
public interface UserService{

   R register(UserEntity user, MultipartFile uploadFile,Integer verifyCode, HttpServletResponse response, HttpSession session);

   /**
    * 发送验证码到指定邮箱  并将验证码和邮箱存入redis
    * @param email
    */
   void sendEmail(String email);

   R login(UserEntity user,HttpSession session,HttpServletResponse response);

   UserEntity showUser(Long userId);

   /**
    * 修改密码
    * @param newPass
    * @param oldPass
    * @return
    */
   R updatePassword(String newPass,String oldPass);

   boolean isAdvice(Long userId);

   boolean cleanAdvice(Long userId);
}

