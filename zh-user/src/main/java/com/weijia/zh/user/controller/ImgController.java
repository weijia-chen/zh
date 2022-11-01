package com.weijia.zh.user.controller;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.service.ImgService;
import com.weijia.zh.user.vo.ResponseImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片管理
 */
@Slf4j
@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    /**
     * 上传图片
     * @param file ：图片的key值 ，value只为图片序列化后的值
     * @return
     */
    @PostMapping("/upload")
    public ResponseImg upload(MultipartFile file){
        log.info("上传图片:{}",file == null);
        if (file == null){
            return new ResponseImg(1,"上传图片失败，图片为空");
        }
        String upload = this.imgService.upload(file);
        log.info("上传图片的相对路径：{}",upload);
        return new ResponseImg(0,upload,upload);
    }

}
