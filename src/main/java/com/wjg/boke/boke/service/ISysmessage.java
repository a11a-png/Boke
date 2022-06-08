package com.wjg.boke.boke.service;

import com.wjg.boke.boke.po.SysMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISysmessage {

    List<SysMessage> selectAll(Integer curr,Integer page, Integer userID);

    int insert(SysMessage record);

    int selectWD(Integer userid);

    int selectCount(Integer userid);

    //设置已读状态
    int updateYD(@Param("userid") Integer userid);
}
