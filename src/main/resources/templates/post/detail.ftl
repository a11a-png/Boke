<#include "../inc/layout.ftl" />

<@layout "博客详情">

  <#include "../inc/header-panel.ftl" />

<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8 content detail">
            <div class="fly-panel detail-box">
                <div class="fly-detail-info">
                    <!-- <span class="layui-badge">审核中</span> -->
                    <span class="layui-badge layui-bg-green fly-detail-column">${postData.articlesTitle}</span>

<#--                    <#if post.level gt 0><span class="layui-badge layui-bg-black">置顶</span></#if>-->
<#--                    <#if post.recommend><span class="layui-badge layui-bg-red">精帖</span></#if>-->

<#--                    <div class="fly-admin-box" data-id="${post.id}">-->

<#--                        <#if post.userId == profile.id>-->
<#--                            &lt;#&ndash;发布者删除&ndash;&gt;-->
<#--                            <span class="layui-btn layui-btn-xs jie-admin" type="del">删除</span>-->
<#--                        </#if>-->

<#--                        <@shiro.hasRole name="admin">-->
<#--                            &lt;#&ndash;管理员操作&ndash;&gt;-->
<#--                            <span class="layui-btn layui-btn-xs jie-admin" type="set" field="delete" rank="1">删除</span>-->

<#--                            <#if post.level == 0><span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="1">置顶</span></#if>-->
<#--                            <#if post.level gt 0><span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="0" style="background-color:#ccc;">取消置顶</span></#if>-->

<#--                            <#if !post.recommend><span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="1">加精</span></#if>-->
<#--                            <#if post.recommend><span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="0" style="background-color:#ccc;">取消加精</span></#if>-->
<#--                        </@shiro.hasRole>-->
<#--                    </div>-->
                    <span class="fly-list-nums">
            <a href="#comment"><i class="iconfont" title="回答">&#xe60c;</i> ${postData.articlesCount}</a>
            <i class="iconfont" title="人气">&#xe60b;</i> ${postData.articlesViews}
          </span>
                </div>
                <div class="detail-about">
                    <input id="postUserId" type="text" hidden value="${postData.userId}" />
                    <a class="fly-avatar" href="/user/${postData.articlesId}">
                       <img src="${postData.articlesCover}" alt="">
<#--                        <#if postData.articlesCover==null > <img src="http://localhost:8080/images/logo.png" alt=""></#if>-->
                    </a>
                    <div class="fly-detail-user">
                        <a href="/user/${postData.articlesId}" class="fly-link">
                            <cite>${postData.articlesTitle}</cite>
                        </a>
                        <span>${timeAgo(postData.articlesDate)}</span>
                    </div>

<#--                    <div class="detail-hits" id="LAY_jieAdmin" data-id="${articles.articlesId}">-->
<#--                        <#if profile.id == post.userId><span class="layui-btn layui-btn-xs jie-admin" type="edit"><a href="/post/edit?id=${post.id}">编辑此贴</a></span></#if>-->
<#--                    </div>-->
                </div>
                <div class="detail-body photos">
                    ${postData.articlesContent}
                </div>
            </div>

            <div class="fly-panel detail-box">
                <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
                    <legend>回 帖</legend>
                </fieldset>
                <@postsTwo articleId=articleId curr=curr size=2>
                    <ul class="jieda">
                       <#list articles.userList as comment>
                         <li data-id="${comment.commentId}" class="jieda-daan">
                           <a name="item-${comment.commentId}"></a>
                           <div class="detail-about detail-about-reply">
<#--                               <a class="fly-avatar" href="/user/${post.authorId}">-->
<#--                                   <img src="${comment.authorAvatar}" alt="${comment.authorName}">-->
<#--                               </a>-->
                               <div class="fly-detail-user">
                                   <a href="/user/${comment.articleId}" class="fly-link">
                                       <cite>${comment.userName}</cite>
                                   </a>

                                   <#if comment.userId == postData.userId>
                                   <span>(楼主)</span>
                                   </#if>
                               </div>

                               <div class="detail-hits">
                                   <span>${timeAgo(comment.commentDate)}</span>
                               </div>

                           </div>
                           <div class="detail-body jieda-body photos">
                               ${comment.commentContent}
                           </div>
                           <div class="jieda-reply">
                             <span class="jieda-zan zanok">
                               <i class="iconfont icon-zan"></i>
                               <em>${comment.likeCount}</em>
                             </span>
                             <span type="reply" class="reback">
                               <input type="text" id="toUser${comment.commentId}" name="${comment.userId}-${comment.commentId}" style="display: none;" value="@${comment.userName}"/>
                               <i class="iconfont icon-svgmoban53"></i>
                               回复
                             </span>
                               <div class="jieda-admin">
                                   <span type="del">删除</span>
                               </div>
                           </div>
                       </li>
                     </#list>
                    </ul>
                    <@paging articles></@paging>
                </@postsTwo>

                <div class="layui-form layui-form-pane" id="rebackdiv">
                    <form method="post">
                        <div class="layui-form-item layui-form-text">
                            <a name="comment"></a>
                            <div class="layui-input-block">
                                <textarea id="L_content" name="message" required lay-verify="required" placeholder="请输入内容"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <input type="hidden" name="jid" value="">
                            <button class="layui-btn" type="button" lay-filter="formSubmitBtn" lay-submit>提交回复</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <#include "../inc/right.ftl" />
    </div>
</div>
<script>
//layui.cache.page = 'jie';
    var rebackID=[];  //回复用户ID
    layui.use(['layer','form'], function() {
        var form=layui.form;
        var layer=layui.layer;

        $('.reback').on('click', function (){
            var tt=$(this).find("input");

            rebackID.push(parseInt(tt[0].name.split("-")));
            $("#rebackdiv").before("<span id='a"+tt[0].name+"' style='margin-right: 10px;'>"+tt[0].value+" <span type='del' style='color: red;cursor: pointer' onclick='delte("+tt[0].name+")'>×</span></span>");
        })

        form.on('submit(formSubmitBtn)', function(data){
            data.field.potsid=${postData.articlesId};
            data.field.touserid=rebackID[0];
            data.field.commentid=rebackID[1];
            data.field.postUserId=$("#postUserId").val();
            $.post("/user/comment", data.field, function(res){
                if(res.status == 0){
                    console.log(res);
                    layer.msg(res.msg, {
                        icon: 1,
                        time: 2000,
                    },function (){
                        location.href = res.action;
                    })
                }else{
                    console.log(res);
                    layer.msg(res.msg,{icon:5});
                }
            });
        });
    });

    function delte(id){
        $("#a"+id+"").remove();
        //使用过滤方法，过滤掉移除的id
        rebackID=rebackID.filter(function (value){
            return value != id;
        })
    }

</script>

</@layout>