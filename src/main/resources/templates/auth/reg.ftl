<#include "../inc/layout.ftl" />

<@layout "注册">
<div class="layui-container fly-marginTop">
    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user">
            <ul class="layui-tab-title">
                <li><a href="/login">登入</a></li>
                <li class="layui-this">注册</li>
            </ul>
            <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
                <div class="layui-tab-item layui-show">
                    <div class="layui-form layui-form-pane">
                        <form method="post" action="/creatCustomer">
                            <div class="layui-form-item">
                                <label for="L_username" class="layui-form-label">账号</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="L_username" name="userip" required lay-verify="required" autocomplete="off" class="layui-input" value="10086">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_pass" class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="L_pass" name="userPassword" required lay-verify="required" autocomplete="off" class="layui-input" value="111111">
                                </div>
                                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_repass" class="layui-form-label">确认密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="L_repass" name="repass" required lay-verify="required" autocomplete="off" class="layui-input" value="111111">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_vercode" class="layui-form-label">人类验证</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="L_vercode" name="vercode" required lay-verify="required" placeholder="请回答后面的问题" autocomplete="off" class="layui-input">
                                </div>
                                <div class="">
                                    <img id="capthca" src="/getYzm?time="+new Date().getTime()>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button class="layui-btn" type="button" lay-filter="formSubmitBtn" lay-submit>立即注册</button>
                            </div>
                            <div class="layui-form-item fly-form-app">
                                <span>或者直接使用社交账号快捷注册</span>
                                <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
                                <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    //layui.cache.page = 'user';
    layui.use(["form","layer"],function (){
        var form=layui.form;
        var layer=layui.layer;
        $("#capthca").click(function () {
            console.log(123);
            this.src = "/getYzm?time="+new Date().getTime();
        });

        form.on('submit(formSubmitBtn)', function(data){
            $.post("/creatCustomer", data.field, function(res){
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
    })

</script>
</@layout>