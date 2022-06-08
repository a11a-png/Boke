if (typeof (tio) == "undefined") {
    tio = {};
}
tio.ws = {};
tio.ws = function ($, layim) {

    this.heartbeatTimeout = 5000; // 心跳超时时间，单位：毫秒
    this.heartbeatSendInterval = this.heartbeatTimeout / 2;   //心跳发送间隔

     var self = this;  //tio对象

    //连接
    this.connect = function () {
        var url = "ws://localhost:9527?userId="+self.userId; //tio连接端口
        var socket = new WebSocket(url); //连接WebSocket

        self.socket = socket;

        socket.onopen = function () {
            console.log("tio ws 启动~");
            self.lastInteractionTime(new Date().getTime());

            //建立心跳
            self.ping();
        };

        socket.onclose = function () {
            console.log("tio ws 关闭~");
            //尝试重连
            self.reconn();
        }

        socket.onmessage = function (res) {
            console.log("接收到消息！！")
            var msgBody=eval('('+res.data+')');
            if (msgBody.emit === 'chatMessage'){
                //将接收到的消息输出到聊天窗口中
                layim.getMessage(msgBody.data);
            }
        }
    };

    this.openChatWindow=function (){
        //获取个人信息
        $.ajax({
            url:"/chat/getMineAndGroupData",
            async: false,  //设置为同步
            success:function (res){
                self.group=res.data.group;
                self.mine=res.data.mine;
                self.userId=self.mine.id;
            }
        })

        //获取缓存
        var cache = layui.layim.cache();
        //更改缓存
        cache.mine=self.mine;

        //打开窗口
        layim.chat(self.group);
        layim.setChatMin(); //收缩聊天面板
    }

    this.sendChatMessage = function (res) {
        self.socket.send(JSON.stringify({
            type: 'chatMessage',
            data: res
        }));
    }


    this.initHistoryMess = function () {
        localStorage.clear();
        $.ajax({
            url: '/chat/getGroupHistoryMsg',
            success: function (res) {
                var data = res.data;
                console.log(res.data);
                if(data.length < 1) {
                    return;
                }

                //设置每一条消息
                for (var i in data){
                    layim.getMessage(data[i]);
                }
            }
        });
    }


    //-----------重试机制---------------
    this.lastInteractionTime = function () {
        // debugger;
        if (arguments.length == 1) {
            this.lastInteractionTimeValue = arguments[0]
        }
        return this.lastInteractionTimeValue  //上次消息交互间隔
    }

    this.ping = function () {
        console.log("------------->准备心跳中~");


        //建立一个定时器，定时心跳
        self.pingIntervalId = setInterval(function () {
            var iv = new Date().getTime() - self.lastInteractionTime(); // 已经多久没发消息了

            //debugger;

            // 单位：秒
            // 心跳发送间隔2.5秒+消息发送间隔 >= 心跳超时时间
            if ((self.heartbeatSendInterval + iv) >= self.heartbeatTimeout) {
                //则重新连接Webscoket
                self.socket.send(JSON.stringify({
                    type: 'pingMessage'
                    ,data: 'ping'
                }))
                console.log("------------->心跳中~")
            }
            //每隔2.5秒进行判断, 就是为了防止长时间
        }, self.heartbeatSendInterval)
    };

    this.reconn = function () {
        // 先删除心跳定时器
        clearInterval(self.pingIntervalId);

        // 然后尝试重连
        self.connect();
    };
};