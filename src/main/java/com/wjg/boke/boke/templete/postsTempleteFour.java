package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.service.ISysarticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//解决重复代码
@Component
public class postsTempleteFour extends TemplateDirective {

    @Autowired
    public ISysarticles sysarticles;

    @Override
    public String getName() {
        return "postsFour";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer customerid = handler.getInteger("userid");
        Integer curr = handler.getInteger("curr", 1);
        Integer size = handler.getInteger("size", 2);
        //Long categoryId = handler.getLong("categoryId");
        page pagelist=new page();

        pagelist.setUserList(sysarticles.selectWz(size,curr,customerid));
        pagelist.setTotal(sysarticles.selectCountColl(customerid));
        pagelist.setCurrent(curr);
        pagelist.setPage(size);
        handler.put(COLLECTION, pagelist).render();
    }
}
