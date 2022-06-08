package com.wjg.boke.boke.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_comments
 * @author 
 */
@Data
public class SysComments implements Serializable {
    private Integer commentId;

    private Long userId;

    private Long articleId;

    private Long likeCount;

    private Date commentDate;

    private String commentContent;

    private Integer parentCommentId;

    private String userName; //发评论人

    private static final long serialVersionUID = 1L;
}