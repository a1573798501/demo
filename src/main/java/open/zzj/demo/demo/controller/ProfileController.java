package open.zzj.demo.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam;

@Controller
public class ProfileController {




    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model){

        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的帖子");
        }

        return "profile";
    }





}
