package com.weijia.zh.user.service;

import com.weijia.zh.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface ImgService {


    String upload(MultipartFile uploadFile);
}
