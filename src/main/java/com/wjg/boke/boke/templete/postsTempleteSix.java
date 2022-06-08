package com.wjg.boke.boke.templete;

import cn.hutool.json.JSONArray;
import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.service.ISysmessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//解决重复代码
@Component
public class postsTempleteSix extends TemplateDirective {

    @Autowired
    public ISysmessage sysmessage;
    @Autowired
    public ISysarticles sysarticles;

    @Override
    public String getName() {
        return "postsSix";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
          String title = handler.getString("title");
//        Integer curr = handler.getInteger("curr", 1);
//        Integer size = handler.getInteger("size", 4);
//        page pagedata=new page();
//        pagedata.setUserList(sysmessage.selectAll((curr-1)*4,4,customerid));
//        pagedata.setTotal(sysmessage.selectCount(customerid));
//        pagedata.setCurrent(curr);
//        pagedata.setPage(size);
        List<Map<String,Object>> objdata=sysarticles.selectByEs(title);
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(objdata);
        List<SysArticles> articlesList=jsonArray.toList(SysArticles.class);
        handler.put(ARTICLESS, articlesList).render();
    }
}
