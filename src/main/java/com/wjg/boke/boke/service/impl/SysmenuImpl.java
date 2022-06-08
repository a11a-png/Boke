package com.wjg.boke.boke.service.impl;

import com.wjg.boke.boke.dao.SysMenuDao;
import com.wjg.boke.boke.po.SysMenu;
import com.wjg.boke.boke.service.ISysmenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysmenuImpl implements ISysmenu {

    @Resource
    public SysMenuDao menuDao;


    @Override
    public List<SysMenu> selectmenu() {
        return menuDao.selectmenu();
    }
}
