package com.wjg.boke.boke.im.vo;


import lombok.Data;

import java.util.Date;

//消息体
@Data
public class ImMess {
   private String username;  //用户名
   private String avatar;  //头像
   private String type;//聊天窗口来源类型，从发送消息传递的to里面获取
   private String content;  //消息内容
   private Integer cid;
   private Boolean mine; //是否本人发送的消息
   private Integer fromid;  //用户ID
   private Date timestamp;  //消息时间
   private Integer id;//消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id

}
