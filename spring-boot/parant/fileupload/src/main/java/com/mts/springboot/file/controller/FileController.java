/**
 *
 */
package com.mts.springboot.file.controller;

import com.mts.springboot.common.config.ExceptionEnum;
import com.mts.springboot.common.util.*;
import com.mts.springboot.file.model.FileInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * 文件上传下载
 *
 */

@Api(tags = "文件相关API")
@RestController
@RequestMapping("/file")
public class FileController {
    protected final static Logger logger = LoggerFactory.getLogger(FileController.class);

    // 文件存储路径
    public static String FILE_PATH;

    public static String UN_ZIP_PATH;

    @Value("${file.filepath}")
    private String filePath;

    @Value("${file.zip.unUrl}")
    private String unZipPath;

    @PostConstruct
    public void init() {
        FILE_PATH = this.filePath;
        UN_ZIP_PATH = this.unZipPath;
        logger.debug("-----> FILE_PATH : " + FILE_PATH);
    }

    /**
     * 获取MINE文件
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> getMimeMappings() {
        Map<String, String> mimes = new HashMap<String, String>();

        mimes.put(".txt", "text/plain");
        mimes.put(".jpg", "image/JPEG");
        mimes.put(".png", "image/PNG");
        mimes.put(".gif", "image/GIF");
        mimes.put(".doc", "application/msword");
        mimes.put(".xls", "application/vnd.ms-excel");
        mimes.put(".xlsx", "application/vnd.ms-excel");
        mimes.put(".pdf", "application/pdf");
        mimes.put(".zip", "application/zip");
        mimes.put(".rar", "application/octet-stream");
        mimes.put(".apk", "application/vnd.android.package-archive");
        mimes.put("*", "application/octet-stream");

        return mimes;
    }

    /**
     * 多文件上传，form中的字段名如果没有设置，则默认为file, 文件上传后会直接存储
     *
     * @param request
     * @return
     */
    // 多文件上传
    @ApiOperation(value = "多文件上传，form中的字段名必须为file")
    @PostMapping(value = "/upload")
    public Object handleFileUpload(HttpServletRequest request, String companyName) {
        // 路径
        if (org.apache.commons.lang3.StringUtils.isBlank(companyName)) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        File dirFile = new File(FILE_PATH);
        String storePath = trimeStorePath("") + "/" + DateTimeUtils.parseDateToStr(new Date(), "yyyyMMdd");
        storePath = trimeStorePath(storePath);

        File parentFile = StringUtils.hasText(storePath) ? new File(dirFile, storePath) : dirFile;

        List<FileInfo> succFiles = new ArrayList<>();

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        boolean flag = false;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();

                String storeName = companyName + UuidUtils.randomUUID() + getFileSuffix(fileName);

                File upFile = new File(parentFile, storeName);
                if (!upFile.getParentFile().exists()) {
                    upFile.getParentFile().mkdirs();
                }

                // 获取上传的路径
                String dirPath = dirFile.getAbsolutePath();
                String upPath = upFile.getAbsolutePath();

                String relaPath = upPath.substring(dirPath.length());
                relaPath = relaPath.replaceAll("\\\\", "/");
                if (relaPath.startsWith("/")) {
                    relaPath = relaPath.substring(1);
                }

                // 保存
                BufferedOutputStream stream = null;
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(upFile));
                    stream.write(bytes);

                    try {
                        stream.flush();
                        stream.close();
                    } catch (Exception e) {
                    }
                    FileInfo f = new FileInfo();
                    f.setFileId(UuidUtils.randomUUID());
                    f.setFileName(fileName);
                    f.setStorePath(relaPath);
                    f.setHref(upPath);
                    // 上传 成功的路径
                    succFiles.add(f);
                    FileUtils.deleteAll(UN_ZIP_PATH + File.separator + companyName);
                    flag = ZipUtil.unzip(f.getHref(), UN_ZIP_PATH + File.separator + companyName);

                } catch (Exception e) {
                    return ResultUtil.error(500, "上传解压失败");
                } finally {
                    if (stream != null) {
                        try {
                            stream.flush();
                            stream.close();
                        } catch (Exception e) {
                        }
                    }
                }
            } else {
                return ResultUtil.error(500, "上传解压失败");
            }
        }
        if (flag) {
            return ResultUtil.success("上传解压成功");
        } else {
            return ResultUtil.error(500, "上传解压失败");
        }
    }

    private static String trimeStorePath(String path) {
        String storePath = StringUtils.hasText(path) ? path : "";
        if (storePath.startsWith("/")) {
            storePath = storePath.substring(1);
        }

        if (storePath.endsWith("/")) {
            storePath = storePath.substring(0, storePath.length() - 1);
        }

        return storePath;
    }

    private String getFileSuffix(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos >= 0) {
            return fileName.substring(pos);
        }

        return "";
    }

    private String getFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos >= 0) {
            return fileName.substring(0, pos);
        }

        return "";
    }
}