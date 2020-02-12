package open.zzj.demo.demo.mapper;


import open.zzj.demo.demo.dto.QuestionDto;
import open.zzj.demo.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into QUESTION (title,description,GMT_CREATE,GMT_MODIFIED,CREATOR_ID,tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void create(Question question);


    @Select("select * from QUESTION limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from  QUESTION")
    Integer count();


    @Select("select * from QUESTION where CREATOR_ID = #{userId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from  QUESTION where CREATOR_ID = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from QUESTION where id = #{id}")
    Question getById(@Param(value = "id") Integer id);
}
