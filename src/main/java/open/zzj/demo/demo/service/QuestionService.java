package open.zzj.demo.demo.service;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import open.zzj.demo.demo.dto.PaginationDto;
import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.mapper.QuestionMapper;
import open.zzj.demo.demo.mapper.UserMapper;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.User;
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


        Integer totalcount = questionMapper.count();
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

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreatorId());
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

        Integer totalcount = questionMapper.countByUserId(userId);
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

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreatorId());
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
        Question question = questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.findById(question.getCreatorId());
        questionDto.setUser(user);
        return questionDto;

    }
}
