package com.wjg.boke.boke.im.message;

import com.wjg.boke.boke.im.vo.ImMess;
import lombok.Data;

@Data
public class ChatOutMess {
    private String emit;
    private ImMess data;
}
