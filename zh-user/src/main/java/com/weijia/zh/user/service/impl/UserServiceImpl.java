package com.weijia.zh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.common.utils.UUIDUtil;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.feign.QaFeign;
import com.weijia.zh.user.utils.MultipartFileUtil;
import com.weijia.zh.user.utils.RedisUtil;
import com.weijia.zh.user.vo.RespBeanEnum;
import com.weijia.zh.user.vo.SearchUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.user.dao.UserDao;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

@Validated
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Value("${file.databaseImg}")
    private String databaseImg; //保存到数据库的地址

    @Value("${file.discImg}")
    private String discImg; //磁盘的绝对地址

    @Value("${cookieKey.userTicket}")
    private String userTicket; //cookie 保存的键名

    @Value("${origin}")
    private String origin;

    @Value("${redisKey.code}")
    private String codeKey;

    @Autowired
    private UserDao userDao;

    @Resource
    private ThreadPoolTaskExecutor executor;//线程池

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private QaFeign qaFeign;

    /**
     * 前提：验证码正确
     * 1、对密码加盐后进行md5加密
     * 2、文件保存到磁盘中并得到一个虚拟的地址（异步去做）
     * 3、将新用户保存到数据库中，并得到该用户的id
     * 3、使用uuid生成一个唯一id，以分布式seeison的方式将uuid-用户保证到redis中
     * 4、将3中的唯一id写入浏览器的cookie
     * 5、返回结果
     * @param user
     * @param uploadFile
     * @return
     */
    @Override
    public R register(UserEntity user, MultipartFile uploadFile,Integer verifyCode,HttpServletResponse response, HttpSession session) {

//        前提：
        Integer code = (Integer) redisUtil.get(this.codeKey + ":" + user.getEmail());
        if(!verifyCode.equals(code))
            return new R(RespBeanEnum.REGISTER_ERROR_CODE,null);

//          1、对密码加盐后进行md5加密
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            String salt = UUIDUtil.uuid(); //得到盐
            String pas = user.getPassword() + salt;//密码加盐
            String password = DigestUtils.md5DigestAsHex(pas.getBytes());
            user.setPassword(password);
            user.setSalt(salt);
            log.info("future1==={}",Thread.currentThread().getName());

        }, this.executor);

//      2、文件保存到磁盘中并得到一个虚拟的地址（异步去做）
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            String imgName = this.databaseImg + MultipartFileUtil.MuFile(uploadFile, this.discImg);
            user.setAvatar(imgName);
            log.info("future2==={}",Thread.currentThread().getName());
        }, executor);

        CompletableFuture<String> combine = future1.thenCombine(future2, (a, b) -> {
            int insert = userDao.insert(user);
            log.info("combine==={}",Thread.currentThread().getName());
            return UUIDUtil.uuid();

        });

        CompletableFuture<Void> sessionAsync = combine.thenAcceptAsync(uuid -> {
            session.setAttribute(uuid, user);
            log.info("sessionAsync==={}",Thread.currentThread().getName());
        }, executor);

        CompletableFuture<Void> cookieAsync = combine.thenAcceptAsync(uuid -> {
            Cookie cookie = new Cookie(this.userTicket, uuid);
            cookie.setPath(this.origin);
            response.addCookie(cookie);
            log.info("cookieAsync==={}",Thread.currentThread().getName());
        }, executor);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(sessionAsync, cookieAsync);

        try {
            allOf.get();
        } catch (ExecutionException e) {
            log.info("ExecutionException=========错误");
            return new R(RespBeanEnum.REGISTER_ERROR,null); //邮箱已经存在

        } catch (InterruptedException e) {
            log.info("InterruptedException=========错误");
           throw new RuntimeException("网络中断");
        }

        return new R(RespBeanEnum.REGISTER_SUCCESS,user.getAvatar());
    }

    @Override
    public void sendEmail(String email) {

        MimeMessage message = this.javaMailSender.createMimeMessage();
        int code = RandomUtils.nextInt(10000, 99999); //随机生成一个5位数的验证码

        this.redisUtil.set(this.codeKey+":"+email,code,60*10);//10分钟内有效
        log.info("{}的验证码为：{}",email,code);
        log.info("sendEmail==当前线程是：{}",Thread.currentThread().getName());
        try {
            //组装
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //正文
            //主题
            helper.setSubject("欢迎注册之乎问答平台！");
            //开启html模式
            helper.setText("<h1>您的验证码为:"+code+"</h1><h1>验证码10分钟内有效</h1>", true);
            helper.setTo(email);
            helper.setFrom("1761807892@qq.com");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 1、根据邮箱查询用户
     * 2、将用户的盐拼接到待验证的用户的密码后面，然后进行md5加密
     * 3、将加密后的密码与真实密码比较
     *      ---失败，返回
     *      ---成功
     *          写入session，返回cookie
     * @param user
     * @param session
     * @param response
     * @return
     */
    @Override
    public R login(UserEntity user,HttpSession session,HttpServletResponse response) {

//        1、
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("email",user.getEmail());
        UserEntity userEntity = userDao.selectOne(wrapper);

//        查询不到
        if (userEntity == null)
            return new R(RespBeanEnum.LOGIN_ERROR_EMAIL,null);
//        2、
        String password = user.getPassword() + userEntity.getSalt();
        String pass = DigestUtils.md5DigestAsHex(password.getBytes());

//        成功
        if(userEntity.getPassword().equals(pass)){

            //判断是否封号
            if (userEntity.getCondition() == 1){
                return new R(null,4003,userEntity.getCause());
            }
            String uuid = UUIDUtil.uuid();
            session.setAttribute(uuid,userEntity);
            Cookie cookie = new Cookie(this.userTicket,uuid);
            cookie.setPath(this.origin);
            response.addCookie(cookie);
            return  new R(RespBeanEnum.LOGIN_SUCCESS,userEntity.getAvatar());
        }
        return new R(RespBeanEnum.LOGIN_ERROR,null);
    }

    @Override
    public UserEntity showUser(Long userId) {

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id","nick_name","fans_count","follow_count");
        wrapper.eq("id",userId);
        UserEntity user = this.userDao.selectOne(wrapper);
        Long sumLike = this.qaFeign.sumLikeByUserId(userId);
        user.setThumbUp(sumLike);

        return user;

    }

    @Override
    public R updatePassword(String newPass, String oldPass) {
        //获得用户信息
        UserEntity user = HandlerInterceptor.loginUser.get();
        String password = oldPass + user.getSalt();
        String pass = DigestUtils.md5DigestAsHex(password.getBytes());
        //设置新密码，盐不变
        RespBeanEnum respBeanEnum = RespBeanEnum.UPDATE_PASSWORD_SUCCESS;
        if (user.getPassword().equals(pass)){
            newPass += user.getSalt();
            String newPassword = DigestUtils.md5DigestAsHex(newPass.getBytes());
            UserEntity newUser = new UserEntity();
            newUser.setId(user.getId());
            newUser.setPassword(newPassword);
            this.userDao.updateById(newUser);
            respBeanEnum = RespBeanEnum.UPDATE_PASSWORD_SUCCESS;
        }
        return new R(respBeanEnum,null);
    }

    @Override
    public boolean isAdvice(Long userId) {

        int i = this.userDao.searchAdvice(userId);
        if (i<=0)
            return false;
        return true;
    }

    @Override
    public boolean cleanAdvice(Long userId) {
        int i = this.userDao.updateAdvice(userId, 0);//清空
        if (i<=0)
            return false;
        return true;
    }


    @Override
    public R<RespData> userList(SearchUserVo searchUserVo) {
        Page<UserEntity> page = new Page<>(searchUserVo.getCurrent(), searchUserVo.getSize());
        IPage<UserEntity> users = this.userDao.searchUsersByNameOrCondition(page,searchUserVo);
        return new R(RespBeanEnum.SUCCESS,new RespData(users.getRecords(),users.getTotal()));
    }
}