package open.zzj.demo.demo.controller;

import open.zzj.demo.demo.dto.CommentCreateDto;
import open.zzj.demo.demo.dto.CommentDto;
import open.zzj.demo.demo.dto.ResultDto;
import open.zzj.demo.demo.enums.CommenTypeEnums;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.model.Comment;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commetnDto,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commetnDto == null || StringUtils.isBlank(commetnDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commetnDto.getParentId());
        comment.setContent(commetnDto.getContent());
        comment.setType(commetnDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount((long) 0);
        comment.setCommentCount(0);
        commentService.insert(comment);
        return ResultDto.okOf();

    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comment(@PathVariable(name = "id") Long id){
        List<CommentDto> secondCommentDtoList =  commentService.listByTargetId(id, CommenTypeEnums.COMMNET.getType());
        return ResultDto.okOf(secondCommentDtoList);
    }


}
