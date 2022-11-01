package com.weijia.zh.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 富文本编辑器wangEditor 上传图片后要求响应的格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseImg {
    private int errno;
    private String message;
    private ResponseImgData data;

    public ResponseImg(int errno, String message,String url,String alt,String href) {
        this.errno = errno;
        this.message = message;
        this.data = new ResponseImgData(url,alt,href);
    }
    public ResponseImg(int errno,String url,String alt,String href) {
        this.errno = errno;
        this.data = new ResponseImgData(url,alt,href);
    }
    public ResponseImg(int errno,String message) {
        this.errno = errno;
        this.message = message;
    }

    public ResponseImg(int errno,String url,String href) {
        this.errno = errno;
        this.data = new ResponseImgData(url,href);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    class ResponseImgData{
        private String url;
        private String alt;
        private String href;

        public ResponseImgData(String url, String href) {
            this.url = url;
            this.href = href;
        }
    }
}
