package com.jiang.scriptdemo.entity;

import java.io.Serializable;

public class Files implements Serializable {

    private static final long serialVersionUID=1L;
    private String filePath;
    private String fileName;
    private String fileSuffix;

    public Files(String filePath, String fileName, String fileSuffix) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSuffix = fileSuffix;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }


}