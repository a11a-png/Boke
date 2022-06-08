package com.wjg.boke.boke.po;

import java.io.Serializable;
import java.util.Date;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * sys_message
 * @author 
 */
@Data
public class SysMessage implements Serializable {
    private Integer messageId;

    private Integer fromuserid;

    private Integer touserid;

    private Integer potsid;

    private Integer commentid;

    private String message;

    /**
     * 0系统消息   1评论消息   2回复消息
     */
    private Byte type;

    /**
     * 0已读   1未读
     */
    private Byte status;

    private String fromName; //发送方姓名

    private String toName; //接收方姓名

    private String articlesTitle; //文章标题

    private Date messDate; //日期

    private Integer postUserId; //

    private static final long serialVersionUID = 1L;
}