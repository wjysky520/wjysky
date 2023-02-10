window.CHAT={
    var socket = null;
// 初始化socket
    init:function(){
// 判断当前的浏览器是否支持WebSocket
        if(window.WebSocket){
            // 检验当前的webSocket是否存在,以及连接的状态,如已经连接,直接返回
            if(CHAT.socket!=null&&CHAT.socket!=undefined&&CHAT.socket.readyState==WebSocket.OPEN){
                return false;
            }else{// 实例化 , 第二个ws是我们可以自定义的, 根据后端的路由来
                CHAT.socket=new WebSocket("ws://192.168.43.10:9999/ws");
                // 初始化WebSocket原生的方法
                CHAT.socket.onopen=CHAT.myopen();
                CHAT.socket.onmessage=CHAT.mymessage();
                CHAT.socket.onerror=CHAT.myerror();
                CHAT.socket.onclose=CHAT.myclose();

            }
        }else{
            alert("当前设备不支持WebSocket");
        }
    }
// 发送聊天消息
    chat:function(msg){
        // 如果的当前的WebSocket是连接的状态,直接发送 否则从新连接
        if(CHAT.socket.readyState==WebSocket.OPEN&&CHAT.socket!=null&&CHAT.socket!=undefined){
            socket.send(msg);
        }else{
            // 重新连接
            CHAT.init();
            // 延迟一会,从新发送
            setTimeout(1000);
            CHAT.send(msg);
        }
    }
// 当连接建立完成后对调
    myopen:function(){
        // 拉取连接建立之前的未签收的消息记录
        // 发送心跳包
    }
    mymessage:function(msg){
        // 因为服务端可以主动的推送消息,我们提前定义和后端统一msg的类型, 如,拉取好友信息的消息,或 聊天的消息
        if(msg==聊天内容){
            // 发送请求签收消息,改变请求的状态
            // 将消息缓存到本地
            // 将msg 转换成消息对象, 植入html进行渲染
        }else if(msg==拉取好友列表){
            // 发送请求更新好友列表
        }

    }
    myerror:function(){
        console.log("连接出现异常...");
    }
    myclose:function(){
        console.log("连接关闭...");
    }
    keepalive: function() {
        // 构建对象
        var dataContent = new app.DataContent(app.KEEPALIVE, null, null);
        // 发送心跳
        CHAT.chat(JSON.stringify(dataContent));

        // 定时执行函数, 其他操作
        // 拉取未读消息
        // 拉取好友信息
    }

}