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
        <title>Document</title>
        <script src="${path}/bootstrap/js/jquery.min.js"></script>
        <!-- 引入 ECharts 文件 -->
        <script src="${path}/js/echarts.js"></script>
        <script type="text/javascript">

            $(function(){

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户统计图',  //标题
                        //show:false  //是否隐藏标题
                        subtext:"纯属虚构", //副标题
                        sublink:"${path}/main/main.jsp"
                    },
                    tooltip: {},  //鼠标的提示
                    legend: {
                        data:['小男孩','小姑娘']  //选项卡
                    },
                    xAxis: {  //横坐标
                        data: ["1月","2月","3月","4月","5月","6月"]
                    },
                    yAxis: {},  //纵坐标   自适应
                    series: [{   //数据列表
                        name: '小男孩',  //对应选项卡名称
                        type: 'bar',  //展示的图标类型    bar:柱状图   line:折线图    pie：饼图
                        data: [50, 200, 360, 100, 100, 200]  //数据
                    },{   //数据列表
                        name: '小姑娘',  //对应选项卡名称
                        type: 'bar',  //展示的图标类型    bar:柱状图   line:折线图    pie：饼图
                        data: [5, 20, 36, 100, 10, 20]  //数据
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });



        </script>
    </head>
    <body>
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="main" style="width: 600px;height:400px;"></div>

    </body>
</html>