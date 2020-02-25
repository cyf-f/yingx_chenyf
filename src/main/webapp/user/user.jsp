<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>
    $(function(){
        //初始化表单属性
        $("#uTable").jqGrid({
            url : "${path}/user/queryByPage",  //分页查询   page  rows  total recoreds  page  rows
            editurl:"${path}/user/edit",
            datatype : "json",
            rowNum : 2,  //每页展示是条数
            rowList : [ 2,5,10, 20, 30 ],
            pager : '#uPager',
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords:true,  //是否展示数据总条数
            colNames : [ 'ID', '用户名', '手机号', '头像', '签名','状态', '微信','注册时间','学分'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'username',editable:true,width : 90},
                {name : 'phone',editable:true,width : 100},
                {name : 'picImg',editable:true,width : 80,align : "center",edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='height:100px;width:100px' />";
                    }
                },
                {name : 'sign',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,align : "center",
                    //cellvalue：具体的值，options：操作的属性，rowObject：行对象一行的数据
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue=="1"){ //正常状态
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-success'>冻结</button>";
                        }else{  //冻结状态
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-danger'>激活</button>";
                        }
                    }
                },
                {name : 'wechat',editable:true,width : 80,align : "center"},
                {name : 'createDate',width : 80,align : "center"},
                {name : 'score',width : 150,sortable : false}
            ]
        });

        //处理曾删改查操作   工具栏
        $("#uTable").jqGrid('navGrid', '#uPager',
            {edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},
            {}, //执行修改之后的额外操作
            {
                closeAfterAdd:true, //关闭添加的对话框
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        fileElementId: "picImg",    //需要上传的文件域的ID，即<input type="file">的ID。
                        url: "${path}/user/uploadUserPicImg", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'text',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        //async : true,   //是否是异步
                        data:{id:data.responseText},  //responseText: "74141b4c-f337-4ab2-ada2-c1b07364adee"
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新页面
                            $("#uTable").trigger("reloadGrid");
                            //$("#uTable").trigger("reloadGrid", { fromServer: true, page: 1 });
                        }
                    });  http://q5um2in1x.bkt.clouddn.com/1582076953948-1581991049337-%E5%8A%A8%E7%94%BB.mp4
                        //必须要有返回值
                        return "aaa";
                }
            }, //执行添加之后的额外操作
            {} //执行删除之后的额外操作
        );
        //点及发送验证码
        $("#sendPhoneCode").click(function(){

            //1.做验证  手机号验证  获取输入框手机号
            var phone = $("#phoneNum").val();
            //正则验证手机号
            var aa=/^1[34578]\d{9}$/;
            //判断手机号是否合法
            if(aa.test(phone)){
                // 将手机号传过去
                //2.发送异步请求调用方法发送验证码
                $.post("${path}/user/getPhone?phone=" + phone, function (data) {
                    alert(data.message);
                }, "json");
            }else{
                alert("请正确填写手机号");
            }
        });
    });

    //点击修改状态按钮
    function updateStatus(id,status){

        //id 修改状态   异步的修改
        if(status==1){
            //正常状态  改为冻结 0
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                dataType:"text",
                data:{"id":id,"status":"0","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#uTable").trigger("reloadGrid");
                }
            });
        }else{
            //冻结状态  改为正常 1
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                dataType:"text",
                data:{"id":id,"status":"1","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#uTable").trigger("reloadGrid");
                }
            });
        }
    }

    //删除
    function deletePhoto(id){
        alert(id);
    }

</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <center><h2>用户管理</h2></center>
    </div>

        <%--发送验证码--%>
        <div class="input-group" style="float:right;width:30%">
            <input id="phoneNum" type="text" class="form-control" placeholder="请输入手机号" />
            <span class="input-group-addon"><a href="javascript:void(0)" id="sendPhoneCode">发送验证码</a></span>
        </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">用户信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="uTable" />

    <%--工具栏--%>
    <div id="uPager" />

</div>