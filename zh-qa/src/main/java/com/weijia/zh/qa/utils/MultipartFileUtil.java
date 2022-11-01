package com.weijia.zh.qa.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MultipartFileUtil {


    /**
     *返回文件保存在本地的文件名（包含后缀）
     * @param file 文件
     * @param discPath 真实保存的路径
     * @param
     */
    public static String  MuFile(MultipartFile file,String discPath)  {

        try {
            if (file.getSize()<=0)//文件的大小是空无需保存
                return "";

            //将上传的文件保存到本机真实目录下目录下,必须是本机的绝对路径
            File destFile = new File(discPath);
            //使用uuid作为文件随机名称
            String fileName = UUID.randomUUID().toString().replaceAll("-", "");
            //使用FileNameUtils获取上传文件名的后缀
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            //创建新的文件名称
            String newFileName = fileName + "." + extension;
            //创建要保存文件的File对象
            File afile = new File(destFile, newFileName);
            //保存文件到本地磁盘
            file.transferTo(afile);
            //得到保存的完整名称，路径由调用方设置
            return newFileName;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 截取文件的名字（包含后缀）
     * @param path
     * @return
     */
    public static String subFileName(String path){
        int i = path.lastIndexOf("/");

        return path.substring(i + 1, path.length());

    }

    public static Boolean delPathFile(String path){

        return FileSystemUtils.deleteRecursively(new File(path));

    }
}
