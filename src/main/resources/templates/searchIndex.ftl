<#include "inc/layout.ftl" />

<@layout "搜索 - ">

<#include "inc/header-panel.ftl" />

<div class="layui-container">
    <div class="layui-row layui-col-space15">

        <div class="layui-col-md8">
            <div class="fly-panel">
                <div class="fly-panel-title fly-filter">
                    <a>您正在搜索关键字 “ ${title} ” - 共有 <strong></strong> 条记录</a>
                    <a href="#signin" class="layui-hide-sm layui-show-xs-block fly-right" id="LAY_goSignin" style="color: #FF5722;">去签到</a>
                </div>
                <@postsSix title=title >
                    <ul class="fly-list">
                        <#list articless as post>
                            <@plisting post></@plisting>
                        </#list>
                    </ul>
                </@postsSix>
<#--            <@paging pageData></@paging>-->
            </div>
        </div>

        <#include "inc/right.ftl" />

    </div>
</div>
</@layout>