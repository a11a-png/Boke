package com.wjg.boke.boke.comment;

import lombok.Data;

import java.util.List;

//分页类
@Data
public class page<T> {

    public Integer total;  //总数

    public Integer page; //显示页数

    public Integer current; //跳转页

    public List<T> userList; //列表内容
}
