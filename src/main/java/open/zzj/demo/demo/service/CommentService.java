package open.zzj.demo.demo.service;


import open.zzj.demo.demo.enums.CommenTypeEnums;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.exception.CustomizeException;
import open.zzj.demo.demo.mapper.CommentMapper;
import open.zzj.demo.demo.mapper.QuestionExtensionMapper;
import open.zzj.demo.demo.mapper.QuestionMapper;
import open.zzj.demo.demo.model.Comment;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtensionMapper questionExtensionMapper;


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
            Comment commentDb = commentMapper.selectByPrimaryKey(comment.getId());
            if (commentDb == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            }
            commentMapper.insert(comment);
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
}
