<#macro layout title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="欢迎关注公众号：MarkerHub">
    <meta name="description" content="更多开源项目可以关注公众号：MarkerHub">
    <link rel="stylesheet" href="/layui/css/layui.css">
    <link rel="stylesheet" href="/css/global.css">

    <script src="/layui/layui.js"></script>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/im.js"></script>
    <script src="/js/chat.js"></script>

</head>
<body>

    <#include "../inc/common.ftl" />
    <#include "../inc/header.ftl" />

    <#nested >

<#include "../inc/footer.ftl" />

<script>
    <#--var username=-->
    <#--//layui.cache.page = '';-->
    <#--layui.cache.user = {-->
    <#--    username: '${customer.userip!=null? "${customer.userip}" : "游客"}',-->
    <#--    uid: '${customer.userid}'!=null? '${customer.userid}':-1,-->
    <#--    avatar: '${customer.userPhoto!"/images/avatar/00.jpg"}',-->
    <#--    experience: 83-->
    <#--};-->

    // layui.config({
    //     version: "3.0.0"
    //     //,base: '/res/mods/' //这里实际使用时，建议改成绝对路径
    // }).extend({
    //     fly: 'index'
    // }).use('fly');
</script>

<script>
    layui.use("layer",function (){
        var layer=layui.layer;
        message();
        function message(){
            var userid=$("#userid").val();
            var elemUser = $('.fly-nav-user');
            if(userid!=null){
                $.post('/message/nums/',{_: new Date().getTime()},function (res){
                    console.log(res);
                    if(res.status === 0 && res.count > 0){
                        var msg = $('<a class="fly-nav-msg" href="javascript:;">'+ res.count +'</a>');
                        elemUser.append(msg);
                        msg.on('click', function(){
                            location.href = "/user/mess";
                        });
                        layer.tips('你有 '+ res.count +' 条未读消息', msg, {
                            tips: 3
                            ,tipsMore: true
                            ,fixed: true
                        });
                        msg.on('mouseenter', function(){
                            layer.closeAll('tips');
                        })
                    }
                })
            }
        }
    })

    //及时推送消息
    //弹出消息
    function showTips(count) {
        var msg = $('<a class="fly-nav-msg" href="javascript:;">'+ count +'</a>');
        var elemUser = $('.fly-nav-user');
        elemUser.append(msg);
        msg.on('click', function(){
            location.href = "/user/mess";
        });
        layer.tips('你有 '+ count +' 条未读消息', msg, {
            tips: 3
            ,tipsMore: true
            ,fixed: true
        });
        msg.on('mouseenter', function(){
            layer.closeAll('tips');
        })
    }

    //连接websocket消息通道
    $(function () {
        var userid=$("#userid").val();
        var elemUser = $('.fly-nav-user');
        if(userid!=null){
            //连接websocket通道
            var socket = new SockJS("/websocket")
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                //推送消息 "/user/" +userid + "/messCount"消息通道
                stompClient.subscribe("/user/" + userid + "/messCount", function (res) {
                    console.log(res);
                    // 弹窗
                    showTips(res.body);
                })
            });
        }
    });
</script>

</body>
</html>

</#macro>