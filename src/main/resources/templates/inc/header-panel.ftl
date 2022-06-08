<div class="fly-panel fly-column">
    <div class="layui-container">
        <ul class="layui-clear">
            <li class="${(0 == itemID)?string("layui-hide-xs layui-this","")}"><a href="/">首页</a></li>
            <#list menus as item>
                <li class="${(item.menuId == itemID)?string("layui-hide-xs layui-this","")}"><a href="/category/${item.menuId}">${item.menuName}</a></li>
            </#list>

            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li>

            <!-- 用户登入后显示 -->
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/user/index">我发表的贴</a></li>
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/user/index#collection">我收藏的贴</a></li>
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/UploadES">同步ES</a></li>
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/logOut">退出</a></li>
            <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="/post/edit">发表新帖</a></li>
        </ul>

        <div class="fly-column-right layui-hide-xs">
            <form action="" class="layui-form">
                <input type="text" class="layui-input-inline" name="title">
                <span class="fly-search" lay-filter="formSubmitBtn" lay-submit>
                    <i class="layui-icon"></i>
                </span>
            </form>
        </div>
    </div>
</div>
<script>
    layui.use(['layer','form'],function (){
        var layer=layui.layer;
        var form=layui.form;

        form.on('submit(formSubmitBtn)',function (data){
            $.get("/SearchEs/"+data.field.title,function (data){
                location.href="/searchIndex/"+data.data;
            })
        })

    })

</script>
