package open.zzj.demo.demo.dto;

import lombok.Data;
import open.zzj.demo.demo.model.User;



@Data
public class CommentDto {

    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private User user;

    private Integer commentCount;

}
