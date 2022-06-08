<#include "../inc/layout.ftl" />

<@layout "登录">

<div class="layui-container fly-marginTop">
  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title">
        <li class="layui-this">登入</li>
        <li><a href="/reg">注册</a></li>
      </ul>
      <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div class="layui-form layui-form-pane">
            <form method="post">
              <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">账号</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_email" name="userip" required lay-verify="required" autocomplete="off" class="layui-input" value="10086">
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="userPassword" required lay-verify="required" autocomplete="off" class="layui-input" value="111111">
                </div>
              </div>
              <div class="layui-form-item">
                <button class="layui-btn" type="button" lay-filter="formSubmitBtn" lay-submit>立即登录</button>
                <span style="padding-left:20px;">
                  <a href="forget.html">忘记密码？</a>
                </span>
              </div>
              <div class="layui-form-item fly-form-app">
                <span>或者使用社交账号登入</span>
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

      form.on('submit(formSubmitBtn)', function(data){
        $.post("/loginDl", data.field, function(res){
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