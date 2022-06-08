package com.wjg.boke.boke.po;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_user_friends
 * @author 
 */
@Data
public class SysUserFriends implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer userFirendId;

    private String userNote;

    private String userStatus;

    private static final long serialVersionUID = 1L;
}