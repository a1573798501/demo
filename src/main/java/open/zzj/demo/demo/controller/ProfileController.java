package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.PaginationDto;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {




    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }


        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的帖子");
        }
        if (action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }


        PaginationDto pagination = questionService.list(user.getId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }





}
