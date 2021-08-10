package com.jiang.scriptdemo.service;

import com.jiang.scriptdemo.entity.Files;
import com.jiang.scriptdemo.response.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    Result upLoadFiles(MultipartFile file);

    /**
     * 根据id获取数据流
     * @param files
     * @return
     */
    InputStream getFileInputStream(Files files);

    public Files getFileById(String id);
}