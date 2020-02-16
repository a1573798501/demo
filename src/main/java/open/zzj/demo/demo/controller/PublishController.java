package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId") Long questionId,
                       Model model){

        QuestionDto question = questionService.getById(questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("questionId",questionId);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title" , required = false) String title,
            @RequestParam(value = "description" , required = false) String description,
            @RequestParam(value = "tag" , required = false) String tag,
            @RequestParam(value = "questionId", required = false) Long questionId,
            HttpServletRequest request,
            Model model
//            @RequestParam("CREATOR_ID") String CREATOR_ID
                            ){

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if(title == null|| title.equals("")){
            model.addAttribute("error","标题不能为空！！");
            return "publish";
        }

        if(description == null|| description.equals("")){
            model.addAttribute("error","补充不能为空！！");
            return "publish";
        }

        if(tag == null|| tag.equals("")){
            model.addAttribute("error","标签不能为空！！");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());
        question.setId(questionId);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

}
