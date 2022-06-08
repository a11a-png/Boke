package com.wjg.boke.boke.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * sys_customer
 * @author 
 */
@Data
public class SysCustomer implements Serializable {
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

    private static final long serialVersionUID = 1L;
}