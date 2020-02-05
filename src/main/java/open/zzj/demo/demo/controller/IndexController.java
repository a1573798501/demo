package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.mapper.QuestionMapper;
import open.zzj.demo.demo.mapper.UserMapper;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    //public String hello(@RequestParam(name = "name") String name, Model model){
        //model.addAttribute("name",name);
    public String index(HttpServletRequest request,
                        Model model){

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }

}
