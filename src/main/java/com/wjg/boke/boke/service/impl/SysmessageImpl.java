package com.wjg.boke.boke.service.impl;

import com.wjg.boke.boke.dao.SysMessageDao;
import com.wjg.boke.boke.po.SysMessage;
import com.wjg.boke.boke.service.ISysmessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysmessageImpl implements ISysmessage {

    @Resource
    public SysMessageDao sysmessage;

    @Override
    public List<SysMessage> selectAll(Integer curr,Integer page, Integer userID) {
        return sysmessage.selectAll(curr,page,userID);
    }

    @Override
    public int insert(SysMessage record) {
        return sysmessage.insert(record);
    }

    @Override
    public int selectWD(Integer userid) {
        return sysmessage.selectWD(userid);
    }

    @Override
    public int selectCount(Integer userid) {
        return sysmessage.selectCount(userid);
    }

    @Override
    public int updateYD(Integer userid) {
        return sysmessage.updateYD(userid);
    }
}
