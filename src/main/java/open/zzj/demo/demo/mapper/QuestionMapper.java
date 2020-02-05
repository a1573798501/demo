package open.zzj.demo.demo.mapper;


import open.zzj.demo.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into QUESTION (title,description,GMT_CREATE,GMT_MODIFIED,CREATOR_ID,tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void create(Question question);


    @Select("select * from question")
    List<Question> list();
}
