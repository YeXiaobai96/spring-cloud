package com.yofc.trace.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.slf4j.Slf4j;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 文件上传工具包
 */
@Slf4j
public class FileUtils {

    /**
     * springBoot项目根目录
     * jar文件同级目录
     */
    static ApplicationHome apphome = new ApplicationHome(FileUtils.class);
    public static File jarfile = apphome.getSource();

    
    /**
     * 图片验证
     * @param inputStream
     * @return
     */
    public static boolean isImage(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        Image img;
        try {
            img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }
    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static String upload(MultipartFile file, String path, String fileName){

        String jarPath = jarfile.getParentFile().toString();
    	//String jarPath = apphome.toString();
        log.info("jar地址"+jarPath);
        //window地址
        //String filePath = jarPath.substring(0, jarPath.lastIndexOf("\\"));
        // 生成新的文件名
        //String realPath =  filePath + "/" + path + "/" + FileNameUtils.getFileName(fileName);
        //linux地址
        String realPath =  jarPath + "/" + path + "/" + FileNameUtils.getFileName(fileName);
        log.info("图片地址"+realPath);
        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return dest.getName();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }

    }


}
