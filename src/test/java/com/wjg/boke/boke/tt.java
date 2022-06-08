package com.wjg.boke.boke;

import com.wjg.boke.boke.dao.SysCommentsDao;
import com.wjg.boke.boke.po.SysComments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class tt {

    @Resource
    public SysCommentsDao commentsDao;
    @Autowired
    public RedisTemplate redisTemplate;

    @Test
    public void te() throws ParseException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        List<String> dt=new ArrayList<>();
        for (int i=0;i<7;i++){
            int time=i*86400000;
            Long datetime=new Date().getTime()-time;
            dt.add(dateFormat.format(datetime));
        }
        //1、查询7天内的评论
        List<SysComments> comments=commentsDao.selectBydate(dt.get(6),dt.get(0));
    }

    @Test
    public void t2(){
//          redisTemplate.boundZSetOps("t1").add("k1",1);
//          redisTemplate.boundZSetOps("t2").add("k1",3);
//          redisTemplate.boundZSetOps("t3").add("k1",4);
//          List keyList=new ArrayList<>(Arrays.asList("t1","t2"));
//          redisTemplate.opsForZSet().unionAndStore("t5",keyList,"t6");
//            Set<String> arr=redisTemplate.boundZSetOps("count").reverseRange(0,0);
//            System.out.println(arr);
//        Set<String> ar=new HashSet<>();
//        for (String a:ar){
//            System.out.println("123");
//        }
        String str="post13";
        System.out.println(Integer.valueOf(str.replace("post","")));
    }
}
