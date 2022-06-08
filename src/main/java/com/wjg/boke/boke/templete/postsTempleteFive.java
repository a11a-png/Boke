package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.service.ISysmessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//解决重复代码
@Component
public class postsTempleteFive extends TemplateDirective {

    @Autowired
    public ISysmessage sysmessage;

    @Override
    public String getName() {
        return "postsFive";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer customerid = handler.getInteger("userid");
        Integer curr = handler.getInteger("curr", 1);
        Integer size = handler.getInteger("size", 4);
        page pagedata=new page();
        pagedata.setUserList(sysmessage.selectAll((curr-1)*4,4,customerid));
        pagedata.setTotal(sysmessage.selectCount(customerid));
        pagedata.setCurrent(curr);
        pagedata.setPage(size);
        handler.put(MESSAGE, pagedata).render();
    }
}
