package com.wjg.boke.boke.service.impl;

import com.wjg.boke.boke.im.vo.ImMess;
import com.wjg.boke.boke.service.IChat;
import com.wjg.boke.boke.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatImpl implements IChat {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void setGroupHistoryMsg(ImMess message) {
        redisTemplate.boundSetOps("message").add(message);
    }

    @Override
    public Set<Object> getGroupHistoryMsg() {
        //根据大小获取对应的信息
        return redisTemplate.boundSetOps("message").members();
    }
}
