package com.jiang.scriptdemo.controller;

import com.jiang.scriptdemo.dto.PageDto;
import com.jiang.scriptdemo.entity.User;
import com.jiang.scriptdemo.mapper.UserMapper;
import com.jiang.scriptdemo.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionService questionService;
    //@Resource
    //private NotificationMapper notificationMapper;

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size) {
        //查找cookies，观察是否有token存在
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login";
        }
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findBytoken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //获取未读的消息数量
                    //int unreadnum=notificationMapper.getunreadcount(user.getId());
                    //request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }
        }
        PageDto pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);

        //获取阅读量最高的十篇问题
        //List<Question> questions= questionService.gettopten();
        //model.addAttribute("topquestion",questions);
        return "index";
    }

}
