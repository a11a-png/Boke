package com.wjg.boke.boke.im.vo;

import lombok.Data;

@Data
public class mineUser {
    private int id;
    private String username;  //姓名
    private String avatar;  // 头像
    private String status;  //在线状态 online：在线、hide：隐身
    private String sign;       //我的签名
    private Boolean mine;      //是否我发送的消息
    private String content;    // 消息内容
}
