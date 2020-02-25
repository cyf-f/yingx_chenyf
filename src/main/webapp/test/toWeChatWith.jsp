<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>聊天室</title>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        /*初始化GoEasy*/
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-df0a45499f274b2bae29ae50a6a12dc9", //替换为您的应用appkey
        });

        $(function(){

            var inputMsg;

            //接收 订阅消息
            //GoEasy-OTP可以对appkey进行有效保护,详情请参考​ ​
            //如果需要使用HTTPS/WSS，请在连接初始化GoEasy对象的时候传入参数forceTLS，并设置为true。
            goEasy.subscribe({
                channel: "myWeChatChannel", //替换为您自己的channel
                onMessage: function (message) {
                    //获取发送内容
                    var content=message.content;

                    //判断接收到的内容是不是自己发的
                    if(inputMsg==content){
                        //是   不处理
                    }else{
                        //渲染发送页面
                        var msgDiv=("<div style=';width:auto;height:30px;'>" +
                            "<div style='float:left;background-color: #bbaadd;border-radius: 12px'>"+content+"</div>" +
                            "</div>");

                        //将内容追加到消息框
                        $("#showMsg").append(msgDiv);
                    }
                }
            });

            //点击发送按钮触发事件
            $("#sendMsg").click(function(){

                //获取文本内容
                var content=$("#content").val();

                //将发送的内容传给变量
                inputMsg=content;

                //发布消息
                goEasy.publish({
                    channel: "myWeChatChannel", //替换为您自己的channel
                    message: content, //替换为您想要发送的消息内容
                    onSuccess:function(){

                        //清空输入框
                        $("#content").val("");

                        /*设置内容样式*/
                        var msgDiv=("<div style=';width:auto;height:30px;'> " +
                            "<div style='float:right;background-color:#a6e1ec;border-radius:10px'>"+content+"</div>" +
                            "</div>");

                        //在我的页面展示我发的消息   靠右展示  展示在消息去
                        $("#showMsg").append(msgDiv);
                    },
                    onFailed: function (error) { //消息发送失败
                        alert("消息发送失败，错误编码："+error.code+" 错误信息："+error.content);
                    }
                });

            });
        })
    </script>
</head>
<body>
<div align="center">
    <h1>184 聊天室</h1>
    <div style="width: 600px;height: 600px;border: 3px #ccaadd solid">
        <%--内容展示区域--%>
        <div id="showMsg" style="width: 594px;height: 500px;border: 3px #aaccdd solid"></div>
        <%--内容输入区域--%>
        <div style="width: 594px;height: 88px;border: 3px #ddccaa solid">
            <%--输入文本框--%>
            <input  id="content" style="width: 500px;height: 80px;" type="area"/>
            <%--发送按钮--%>
            <button id="sendMsg" style="width: 70px;height: 80px;background-color: greenyellow">发送</button>
        </div>
    </div>
</div>
</body>
</html>