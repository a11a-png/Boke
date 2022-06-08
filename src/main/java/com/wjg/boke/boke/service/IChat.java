package com.wjg.boke.boke.service;

import com.wjg.boke.boke.im.vo.ImMess;

import java.util.List;
import java.util.Set;

public interface IChat {

    //设置消息内容
    void setGroupHistoryMsg(ImMess message);

    //获取消息内容
    Set<Object> getGroupHistoryMsg();

}
