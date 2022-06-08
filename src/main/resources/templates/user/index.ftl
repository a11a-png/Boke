<#include "../inc/layout.ftl" />

<@layout "用户中心">
  <div class="layui-container fly-marginTop fly-user-main">
    <@centerLeft level=1></@centerLeft>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>


    <div class="fly-panel fly-panel-user" pad20>
      <div class="layui-tab">
        <ul class="layui-tab-title">
          <li class="layui-this">我发的帖</li>
          <li >我收藏的帖</li>
        </ul>
        <div class="layui-tab-content" style="padding: 20px 0;">
          <div class="layui-tab-item layui-show">
            <form class="layui-form">
              <@postsThree userid=userid curr=curr size=2>
                <ul class="mine-view jie-row" id="fabu">
                  <#list articles.userList as post>
                  <#-- 博文列表 -->
                    <li>
                      <a class="jie-title" href="/post/${post.articlesId}" target="_blank">${post.articlesTitle}</a>
                      <i>${timeAgo(post.articlesDate)}</i>
                      <a class="mine-edit" href="/post/edit?id=${post.articlesId}">编辑</a>
                      <em>${post.articlesViews}阅/${post.articlesCount}答</em>
                    </li>
                  </#list>
                  <#-- 分页 -->
                  <div id="page"></div>
                </ul>
              </@postsThree>
            </form>
          </div>
          <div class="layui-tab-item">
            <form class="layui-form">
              <@postsFour userid=userid curr=curr size=2>
                <ul class="mine-view jie-row" id="collection">
                  <#list collection.userList as post>
                  <#-- 博文列表 -->
                    <li>
                      <a class="jie-title" href="/post/${post.articlesId}" target="_blank">${post.articlesTitle}</a>
                      <i>${timeAgo(post.articlesDate)}</i>
                      <a class="mine-edit" href="/post/edit?id=${post.articlesId}">编辑</a>
                      <em>${post.articlesViews}阅/${post.articlesCount}答</em>
                    </li>
                  </#list>
                  <#-- 分页 -->
                  <@paging collection></@paging>
                </ul>
              </@postsFour>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
    layui.cache.page = 'user';

    layui.use(['laytpl', 'flow', 'util','element','laypage'], function() {
      var $ = layui.jquery;
      var laytpl = layui.laytpl;
      var flow = layui.flow;
      var util = layui.util;
      var element = layui.element;
      var laypage = layui.laypage;

      //执行一个laypage实例
      laypage.render({
        elem: 'page'
        ,count: ${articles.total}
        ,curr: ${articles.current}
        ,limit: ${articles.page}
        ,jump: function(obj, first){
          //首次不执行
          if(!first){
            location.href = "?curr=" + obj.curr;
          }
        }
      });
    });

  </script>
</@layout>