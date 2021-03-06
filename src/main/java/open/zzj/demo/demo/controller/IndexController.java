package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.PaginationDto;
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
    private QuestionService questionService;

    @GetMapping("/")
    //public String hello(@RequestParam(name = "name") String name, Model model){
        //model.addAttribute("name",name);
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size,
                        @RequestParam(name="search",required = false) String search){


        PaginationDto pagination = questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
