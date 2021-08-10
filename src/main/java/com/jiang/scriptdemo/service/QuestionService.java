package com.jiang.scriptdemo.service;

import com.jiang.scriptdemo.dto.PageDto;
import com.jiang.scriptdemo.dto.QuestionDto;
import com.jiang.scriptdemo.entity.Question;
import com.jiang.scriptdemo.entity.User;
import com.jiang.scriptdemo.mapper.QuestionMapper;
import com.jiang.scriptdemo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    public PageDto list(int page, int size){
        PageDto pageDto = new PageDto();
        int totalcount = questionMapper.count();
        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDto> questiondtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreateid());
            QuestionDto questiondto = new QuestionDto();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questiondto);
            questiondto.setUser(user);
            questiondtoList.add(questiondto);
        }
        pageDto.setData(questiondtoList);
        return pageDto;
    }

    public PageDto list(int userid, int page, int size) {
        PageDto pageDto = new PageDto();
        int totalcount = questionMapper.countbyid(userid);
        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.listbyid(userid,offset, size);
        List<QuestionDto> questiondtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreateid());
            QuestionDto questiondto = new QuestionDto();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questiondto);
            questiondto.setUser(user);
            questiondtoList.add(questiondto);
        }
        pageDto.setData(questiondtoList);
        return pageDto;
    }

    public QuestionDto getbyid(int id) {
        QuestionDto questiondto=new QuestionDto();
        Question question=questionMapper.getbyId(id);
        //把第一个对象的所有属性拷贝到第二个对象中
        BeanUtils.copyProperties(question, questiondto);
        User user = userMapper.findById(question.getCreateid());
        questiondto.setUser(user);
        return questiondto;
    }
}
