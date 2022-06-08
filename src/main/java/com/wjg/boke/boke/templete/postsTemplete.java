package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.service.ISysarticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//解决重复代码
@Component
public class postsTemplete extends TemplateDirective {

    @Autowired
    public ISysarticles sysarticles;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        page pagelist=new page();
        Integer sortId = handler.getInteger("sortId");
        Integer curr = handler.getInteger("curr", 1);
        Integer size = handler.getInteger("size", 2);
        //Long categoryId = handler.getLong("categoryId");

        pagelist.setUserList(sysarticles.selectAll(size,curr,sortId,null));
        pagelist.setTotal(sysarticles.selectCount(sortId,null));
        pagelist.setCurrent(curr);
        pagelist.setPage(size);

        handler.put(ARTICLES, pagelist).render();
    }
}
