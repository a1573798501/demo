package open.zzj.demo.demo.controller;

import open.zzj.demo.demo.dto.CommetnDto;
import open.zzj.demo.demo.dto.ResultDto;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.mapper.CommentMapper;
import open.zzj.demo.demo.model.Comment;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommetnDto commetnDto,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            return ResultDto.errorOf(2002,);
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commetnDto.getParentId());
        comment.setContent(commetnDto.getContent());
        comment.setType(commetnDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount((long) 0);
        commentService.insert(comment);
        return ResultDto.okOf();

    }


}
