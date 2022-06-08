package com.wjg.boke.boke.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_articles
 * @author 
 */
@Data
public class SysArticles implements Serializable {
    private Integer articlesId;

    private Integer userId;

    private String articlesTitle;

    private String articlesContent;

    private Integer articlesViews;

    private Integer articlesCount;

    private Date articlesDate;

    private Integer likeCount;

    private String userName;

    private String articlesCover;

    private String sortName;

    private Integer sortId;

    private String title;

    private Integer itemID;

    private static final long serialVersionUID = 1L;
}