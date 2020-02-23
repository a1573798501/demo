package open.zzj.demo.demo.controller;


import open.zzj.demo.demo.dto.CommentDto;
import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.enums.CommenTypeEnums;
import open.zzj.demo.demo.service.CommentService;
import open.zzj.demo.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> commentDtoList =  commentService.listByTargetId(id, CommenTypeEnums.QUESTION.getType());
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",commentDtoList);
        return "question";

    }

}
