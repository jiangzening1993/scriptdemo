package com.jiang.scriptdemo.service.Impl;

import com.jiang.scriptdemo.dto.FileDto;
import com.jiang.scriptdemo.dto.QuestionDto;
import com.jiang.scriptdemo.entity.Files;
import com.jiang.scriptdemo.entity.Question;
import com.jiang.scriptdemo.entity.User;
import com.jiang.scriptdemo.mapper.FileMapper;
import com.jiang.scriptdemo.mapper.QuestionMapper;
import com.jiang.scriptdemo.mapper.UserMapper;
import com.jiang.scriptdemo.response.ResponseCode;
import com.jiang.scriptdemo.response.Result;
import com.jiang.scriptdemo.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.*;

@Service
public class FileServiceImpl implements FileService {


    @Value("${file.save-path}")
    private String savePath;
    @Autowired
    private FileMapper fileMapper;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    public Result upLoadFiles(MultipartFile file) {
        FileDto fileDto = new FileDto();
        long MAX_SIZE=2097152L;
        String fileName=file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)){
            return new Result(ResponseCode.FILE_NAME_EMPTY.getCode(),ResponseCode.FILE_NAME_EMPTY.getMsg(),null);
        }
        if (file.getSize()>MAX_SIZE){
            return new Result(ResponseCode.FILE_MAX_SIZE.getCode(),ResponseCode.FILE_MAX_SIZE.getMsg(),null);
        }
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String newName = System.currentTimeMillis() + suffixName;
        File newFile=new File(savePath,newName);
        if (!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        try {
            //文件写入
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files files=new Files(newFile.getPath(),fileName,suffixName);
        fileMapper.insertFile(files);
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),"数据上传成功");
    }

    @Override
    public Files getFileById(String id) {
        Files files = fileMapper.selectFileById(id);
        return files;
    }

    @Override
    public InputStream getFileInputStream(Files files) {
        File file=new File(files.getFilePath());
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}