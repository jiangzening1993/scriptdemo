package com.jiang.scriptdemo.controller;

import com.jiang.scriptdemo.dto.FileDto;
import com.jiang.scriptdemo.dto.ResultDto;
import com.jiang.scriptdemo.entity.Files;
import com.jiang.scriptdemo.response.ResponseCode;
import com.jiang.scriptdemo.response.Result;
import com.jiang.scriptdemo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/api")
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object upLoadFiles(@RequestParam("file") MultipartFile multipartFile){
        if (multipartFile.isEmpty()){
            return new Result(ResponseCode.FILE_EMPTY.getCode(),ResponseCode.FILE_EMPTY.getMsg(),null);
        }
        fileService.upLoadFiles(multipartFile);
        return "redirect:/publish";
    }

    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public void downloadFiles(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response){
        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[1024];
        Files files = fileService.getFileById(id);
        String fileName = files.getFileName();
        // 获取输出流
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +  new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.setContentType("application/force-download");
            inputStream=fileService.getFileInputStream(files);
            bufferedInputStream=new BufferedInputStream(inputStream);
            outputStream = response.getOutputStream();
            int i=bufferedInputStream.read(bytes);
            while (i!=-1){
                outputStream.write(bytes,0,i);
                i=bufferedInputStream.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (outputStream!=null){
                    outputStream.close();
                }
                if (bufferedInputStream!=null){
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}