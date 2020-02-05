package open.zzj.demo.demo.service;


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

    public List<QuestionDto> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions){
            User user = userMapper.findById(question.getCreatorId());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question , questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
