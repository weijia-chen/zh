package com.weijia.zh.user.service.impl;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.service.ImgService;
import com.weijia.zh.user.utils.MultipartFileUtil;
import com.weijia.zh.user.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgServiceImpl implements ImgService {

    @Value("${file.discImg}")
    private String discImg; //磁盘的绝对地址

    @Value("${file.databaseImg}")
    private String databaseImg; //保存到数据库的地址
    @Override
    public String upload(MultipartFile uploadFile) {
        String imgName = this.databaseImg + MultipartFileUtil.MuFile(uploadFile, this.discImg);
        return imgName;
    }
}
