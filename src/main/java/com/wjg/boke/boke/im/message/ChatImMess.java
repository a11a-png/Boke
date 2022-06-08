package com.wjg.boke.boke.im.message;

import com.wjg.boke.boke.im.vo.ImTo;
import com.wjg.boke.boke.im.vo.mineUser;
import lombok.Data;

//发送消息
@Data
public class ChatImMess {

   private mineUser mine;  //发送
   private ImTo to;  //接收
}
