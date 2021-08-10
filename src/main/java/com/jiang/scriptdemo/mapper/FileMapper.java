package com.jiang.scriptdemo.mapper;

import com.jiang.scriptdemo.entity.Files;
import com.jiang.scriptdemo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {
    /**
     * 将数据信息插入到数据库
     * @param files
     */
    void insertFile(Files files);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    Files selectFileById(String id);

    /**
     * 根据id获取Question
     * @param id
     * @return
     */
    String selectQuestionId(String id);

    @Select("select * from file where id=#{id}")
    Files getbyId(int id);
}