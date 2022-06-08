package com.wjg.boke.boke.comment;

import com.wjg.boke.boke.po.SysMenu;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.service.ISysmenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

//页面返回内容
@Component
public class coment implements ServletContextAware,ApplicationRunner {
    @Autowired
    public ISysmenu iSysmenu;
    @Autowired
    public ISysarticles sysarticles;

    ServletContext servletContext;

    //后执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SysMenu> menus= iSysmenu.selectmenu();
        servletContext.setAttribute("menus",menus);

        //本周热议初始化
        sysarticles.initialization();
    }

//    public void setiSysmenu(){
//        List<SysMenu> menus= iSysmenu.selectmenu();
//        servletContext.setAttribute("menus",menus);
//    }

    //先执行
    @Override
    public void setServletContext(ServletContext servletContext) {
          this.servletContext = servletContext;
    }
}
