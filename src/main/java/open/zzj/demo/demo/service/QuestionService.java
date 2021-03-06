package open.zzj.demo.demo.service;


import open.zzj.demo.demo.dto.PaginationDto;
import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.exception.CustomizeException;
import open.zzj.demo.demo.mapper.QuestionExtensionMapper;
import open.zzj.demo.demo.mapper.QuestionMapper;
import open.zzj.demo.demo.mapper.UserMapper;
import open.zzj.demo.demo.model.Question;
import open.zzj.demo.demo.model.QuestionExample;
import open.zzj.demo.demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtensionMapper questionExtensionMapper;

    public PaginationDto list(String search, Integer page, Integer size) {


        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


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

    public PaginationDto list(Long userId, Integer page, Integer size) {

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

    public QuestionDto getById(Long id) {
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
            question.setViewCount(0);
            question.setLikeConunt(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
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
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            }
        }

    }

    public void incView(Long id) {
        Question questionUpdate = new Question();
        questionUpdate.setId(id);
        questionExtensionMapper.updateByExampleIncView(questionUpdate);
    }
}
