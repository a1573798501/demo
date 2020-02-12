package open.zzj.demo.demo.mapper;


import open.zzj.demo.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id=#{id}")
    User findById(@Param("id") Integer creator_id);

    @Select("SELECT * FROM USER where find_in_set(#{accountId},ACCOUNT_ID)")
    User findByAccountId(@Param("accountId") Integer accountId);

    @Insert("INSERT INTO USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void updateUser(User user);

}
