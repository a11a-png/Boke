package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.service.ISysComments;
import com.wjg.boke.boke.service.ISysarticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//解决重复代码
@Component
public class postsTempleteThree extends TemplateDirective {

    @Autowired
    public ISysarticles sysarticles;

    @Override
    public String getName() {
        return "postsThree";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer customerid = handler.getInteger("userid");
        Integer curr = handler.getInteger("curr", 1);
        Integer size = handler.getInteger("size", 2);
        //Long categoryId = handler.getLong("categoryId");
        page pagelist=new page();
        //page pagelist2=new page();
        pagelist.setUserList(sysarticles.selectAll(size,curr,null,customerid));
        pagelist.setTotal(sysarticles.selectCount(null,customerid));
        pagelist.setCurrent(curr);
        pagelist.setPage(size);

        handler.put(ARTICLES, pagelist).render();
    }
}
