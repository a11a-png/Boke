package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.po.SysComments;
import com.wjg.boke.boke.service.ISysComments;
import com.wjg.boke.boke.service.ISysarticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//解决重复代码
@Component
public class postsTempleteTwo extends TemplateDirective {

    @Autowired
    public ISysComments sysComments;

    @Override
    public String getName() {
        return "postsTwo";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer articleId = handler.getInteger("articleId");
        Integer curr = handler.getInteger("curr", 1);
        Integer size = handler.getInteger("size", 2);
        //Long categoryId = handler.getLong("categoryId");
        page pagelist=new page();
        pagelist.setUserList(sysComments.selectAll(curr,size,articleId));
        pagelist.setTotal(sysComments.selectCount(articleId));
        pagelist.setCurrent(curr);
        pagelist.setPage(size);

        handler.put(ARTICLES, pagelist).render();
    }
}
