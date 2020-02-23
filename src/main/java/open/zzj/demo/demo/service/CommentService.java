package open.zzj.demo.demo.service;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import open.zzj.demo.demo.dto.CommentCreateDto;
import open.zzj.demo.demo.dto.CommentDto;
import open.zzj.demo.demo.enums.CommenTypeEnums;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.exception.CustomizeException;
import open.zzj.demo.demo.mapper.*;
import open.zzj.demo.demo.model.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.ec.ECDSAOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtensionMapper questionExtensionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtensionMapper commentExtensionMapper;


    @Transactional
    public void insert(Comment comment) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommenTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommenTypeEnums.COMMNET.getType()) {
            //回复评论
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria()
                    .andParentIdEqualTo(comment.getParentId());
            List<Comment> commentDb = commentMapper.selectByExample(commentExample);
            if (commentDb == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //增加评论数
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            commentExtensionMapper.updateByExampleIncCommentCount(parentComment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionExtensionMapper.updateByExampleIncCommentCount(question);
        }

    }

    public List<CommentDto> listByTargetId(Long id, Integer type) {

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type);
        commentExample.setOrderByClause("GMT_CREATE desc");
        List<Comment> commentList  = commentMapper.selectByExample(commentExample);
        if (commentList.size() == 0){
            return new ArrayList<>();
        }
        //获取去重复的评论人
        Set<Long> commentatorSet = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIdList = new ArrayList<>();
        userIdList.addAll(commentatorSet);
        UserExample userExample = new UserExample();
        //获取评论人，并且转换成Map
        userExample.createCriteria()
                .andIdIn(userIdList);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long,User> userMap =  userList.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
        //转换comment为commentDto
        List<CommentDto> commentDtoList = commentList.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtoList;

    }
}
