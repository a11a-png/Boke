<#--分页-->
<#macro paging pageData>

<div style="text-align: center">
    <div id="laypage-main">

    </div>
    <script>
        layui.use('laypage', function(){
            var laypage = layui.laypage;

            //执行一个laypage实例
            laypage.render({
                elem: 'laypage-main'
                ,count: ${pageData.total}
                ,curr: ${pageData.current}
                ,limit: ${pageData.page}
                ,jump: function(obj, first){
                   //首次不执行
                   if(!first){
                       location.href = "?curr=" + obj.curr;
                   }
                }
            });
        });
    </script>
</div>
</#macro>

<#macro plisting post>
    <li>
        <a href="user/home.html" class="fly-avatar">
            <img src="${post.articlesCover}" alt="">
        </a>
        <h2>
            <a class="layui-badge">${post.sortName}</a>
            <a href="/category/jie/${post.articlesId}">${post.articlesTitle}</a>
        </h2>
        <div class="fly-list-info">
            <a href="user/home.html" link>
                <cite>${post.userName}</cite>
                <!--
                <i class="iconfont icon-renzheng" title="认证信息：XXX"></i>
                <i class="layui-badge fly-badge-vip">VIP3</i>
                -->
            </a>
            <span>${timeAgo(post.articlesDate)}</span>

            <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i>${post.likeCount}</span>
            <span class="layui-badge fly-badge-accept layui-hide-xs">浏览量 ${post.articlesViews}</span>
            <span class="fly-list-nums">
              <i class="iconfont icon-pinglun1" title="回答"></i> ${post.articlesCount}
              <a href=""></a>
            </span>
        </div>
        <div class="fly-list-badge">
            <!--<span class="layui-badge layui-bg-red">精帖</span>-->
        </div>
    </li>
</#macro>

<#--用户中心的左侧-->
<#macro centerLeft level>

<ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
    <li class="layui-nav-item">
        <a href="/user/home">
            <i class="layui-icon">&#xe609;</i>
            我的主页
        </a>
    </li>
    <li class="layui-nav-item layui-this">
        <a href="/user/index">
            <i class="layui-icon">&#xe612;</i>
            用户中心
        </a>
    </li>
<#--    <li class="layui-nav-item">-->
<#--        <a href="/user/set">-->
<#--            <i class="layui-icon">&#xe620;</i>-->
<#--            基本设置-->
<#--        </a>-->
<#--    </li>-->
    <li class="layui-nav-item">
        <a href="/user/mess">
            <i class="layui-icon">&#xe611;</i>
            我的消息
        </a>
    </li>
</ul>
</#macro>