package com.jiang.scriptdemo.controller;

import com.jiang.scriptdemo.entity.Question;
import com.jiang.scriptdemo.entity.User;
import com.jiang.scriptdemo.mapper.QuestionMapper;
import com.jiang.scriptdemo.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String publishquestion(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            @RequestParam(value = "id",defaultValue = "-1")int id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //防止输入的问题为空
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //获取当前登陆用户的信息
        User user=null;
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                user=userMapper.findBytoken(token);
            }
        }
        //将问题上传到数据库
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreateid( user.getId());
        question.setCreatetime(System.currentTimeMillis());

        questionMapper.createquestion(question);
        return "redirect:/index";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")int id,
                       Model model){
        Question question=questionMapper.getbyId(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        //用来标识问题是修改而不是重新创建
        model.addAttribute("id",question.getId());
        return "publish";
    }
}
