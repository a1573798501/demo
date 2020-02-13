package open.zzj.demo.demo.service;


import open.zzj.demo.demo.dto.PaginationDto;
import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.exception.CustomizeException;
import open.zzj.demo.demo.mapper.QuestionMapper;
import open.zzj.demo.demo.mapper.UserMapper;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.QuestionExample;
import open.zzj.demo.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size) {


        QuestionExample questionExample = new QuestionExample();
        Integer totalcount = (int) questionMapper.countByExample(questionExample);
        Integer totalPage;
        if(totalcount%size == 0){
            totalPage = totalcount/size;
        }else {
            totalPage = totalcount/size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }

        Integer offset = size*(page-1);

        QuestionExample questionExampleList = new QuestionExample();
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExampleList,new RowBounds(offset,size));
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question , questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPagInation(totalPage,page);
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;
    }

    public PaginationDto list(Integer userId, Integer page, Integer size) {

        QuestionExample questionExampleUserCount = new QuestionExample();
        questionExampleUserCount.createCriteria()
                .andCreatorIdEqualTo(userId);
        Integer totalcount = (int) questionMapper.countByExample(questionExampleUserCount);
        Integer totalPage;
        if(totalcount%size == 0){
            totalPage = totalcount/size;
        }else {
            totalPage = totalcount/size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }

        Integer offset = size*(page-1);

        QuestionExample questionExampleList = new QuestionExample();
        questionExampleList.createCriteria()
                .andCreatorIdEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExampleList,new RowBounds(offset,size));
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question , questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setPagInation(totalPage,page);
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;

    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        questionDto.setUser(user);
        return questionDto;

    }

    public void createOrUpdate(Question question) {

        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
        }else {
            Question questionUpdate = new Question();
            questionUpdate.setGmtModified(System.currentTimeMillis());
            questionUpdate.setTitle(question.getTitle());
            questionUpdate.setTag(question.getTag());
            questionUpdate.setDescription(question.getDescription());
            QuestionExample questionExampleUpdate = new QuestionExample();
            questionExampleUpdate.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(questionUpdate,questionExampleUpdate);
            System.out.println("update" + update);
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            }
        }

    }
}
