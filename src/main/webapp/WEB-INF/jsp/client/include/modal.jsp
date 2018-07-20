<%--
  Created by IntelliJ IDEA.
  User: 门喜朋
  Date: 2017/10/19
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <head>
    <style type="text/css">
        .pop-box {
            /*弹出窗口后，弹出的DIV采用此CSS，保证置于最上层
             z-index控制Z轴的坐标，数值越大，离用户越近
            */
            z-index: 9999999; /*这个数值要足够大，才能够显示在最上层*/
            margin-bottom: 3px;
            display: none;
            position: absolute;
            /*background: gray;*/
            /*border: solid1px #6e8bde;*/
        }

        #bg {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            /*弹出窗口后，添加遮罩层，采用此CSS，将该层置于弹出DIV和页面层之间
             z-index控制Z轴的坐标，数值越大，离用户越近 rgba(72, 74, 68, 0.46)
            */
            z-index: 1001;
            background-color:#666;
            -moz-opacity: 0.7;
            opacity: .70;
            filter: alpha(opacity = 70);
        }

    </style>

    <script type="text/javascript">

        function popupDiv(div_id) {
            // 获取传入的DIV
            var $div_obj = $("#" + div_id);
            // 计算机屏幕高度
            var windowWidth = $(window).width();
            // 计算机屏幕长度
            var windowHeight = $(window).height();
            // 取得传入DIV的高度
            var popupHeight = $div_obj.height();
            // 取得传入DIV的长度
            var popupWidth = $div_obj.width();

            // // 添加并显示遮罩层
            $("<div id='bg'></div>").width(windowWidth * 1)
                .height(windowHeight * 0.99).click(function() {
                //hideDiv(div_id);
            }).appendTo("body").fadeIn(200);

            // 显示弹出的DIV
            $div_obj.css({
                "position" : "absloute"
            }).animate({
                left : windowWidth / 2 - popupWidth / 2,
                top : windowHeight / 2 - popupHeight / 2,
                opacity : "show"
            }, "slow");

        }
        /*隐藏弹出DIV*/
        function hideDiv(div_id) {
            $("#bg").remove();
            $("#" + div_id).animate({
                left : 0,
                top : 0,
                opacity : "hide"
            }, "slow");
        }
    </script>

</head>



<div id='pop-div'  class="pop-box">



    <!--<div id='buttonPanel' style="text-align: right"-->
    <!--style="text-align:right">-->
    <!--<input type="button" value="Close" id="btn1"-->
    <!--onclick="hideDiv('pop-div');" />-->
    <!--</div>-->
    <img src="${ctx}/static/client/img/login.gif">
    <p style="margin-left: -50px;z-index: 9999999;color: black;opacity:0.8;">拼命加载中，请稍后...</p>
</div>



</div>