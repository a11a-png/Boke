package com.wjg.boke.boke.shiro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class shiroFile implements Serializable {
    private Integer userid;

    @NotBlank(message = "账号不能为空")
    private String userip;

    private String userName;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

    private String userEmail;

    private String userPhoto;

    private Date createTime;

    private Date userBirthday;

    private Integer userAge;

    private String userPhone;

    private String userZsname;
}
