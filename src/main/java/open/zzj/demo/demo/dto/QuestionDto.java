package open.zzj.demo.demo.dto;


import lombok.Data;
import open.zzj.demo.demo.model.User;

@Data
public class QuestionDto {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;

}
