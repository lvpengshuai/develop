<%--
  User: zly
  Date: 2017-2-27
  Time: 15:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/jsp/admin/include/meta.jsp" %>

    <title>社科年鉴服务平台</title>
    <!-- Stylesheets -->
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/css/bootstrap-extend.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/assets/css/site.min.css">
    <!-- Plugins -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/animsition/animsition.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/asscrollable/asScrollable.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/switchery/switchery.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/intro-js/introjs.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/slidepanel/slidePanel.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery-mmenu.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/flag-icon-css/flag-icon.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/vendor/bootstrap-sweetalert/sweet-alert.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/glyphicons/glyphicons.css">
    <!-- Fonts -->
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/weather-icons/weather-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/web-icons/web-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/open-iconic/open-iconic.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/brand-icons/brand-icons.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/remark/global/fonts/material-design/material-design.min.css">
    <!--[if lt IE 9]>
    <script src="${ctx}/static/remark/vendor/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!--[if lt IE 10]>
    <script src="${ctx}/static/remark/vendor/media-match/media.match.min.js"></script>
    <script src="${ctx}/static/remark/vendor/respond/respond.min.js"></script>
    <![endif]-->
    <!-- Scripts -->
    <script src="${ctx}/static/remark/global/vendor/modernizr/modernizr.min.js"></script>
    <script src="${ctx}/static/remark/global/vendor/breakpoints/breakpoints.min.js"></script>
    <script>
        Breakpoints();
    </script>
    <style type="text/css">
        <!--
        .hybg01 {
            background-repeat: no-repeat;
            background-position: right top;
        }
        .hybox01 {

        }
        .hybt01 {
            font-size: 26px;
            font-weight: bold;
            color: #CC6600;
        }
        .hybt02 {
            font-size: 15px;
            font-weight: bold;
            color: #465468;
        }
        .hybt03 {
            font-size: 12px;
            color: #465468;
        }
        -->
    </style>
</head>
<body class="site-navbar-small dashboard site-menubar-unfold hybg01" leftmargin="2" topmargin="0" marginwidth="0" marginheight="0" >
<!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->
<%-- 右侧菜单导航开始 --%>
<%@include file="/WEB-INF/jsp/admin/include/nav.jsp"%>
<%-- 右侧菜单导航结束 --%>
<!-- 主界面开始 -->
<div class="page animsition">
    <div class="page-content container-fluid">
        <div class="row" data-plugin="matchHeight" data-by-row="true">
            <table width="100" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="100">&nbsp;</td>
                </tr>
            </table>
            <table width="708" height="348" border="0" align="center" cellpadding="0" cellspacing="4">
                <tr>
                    <td height="250" valign="top" class="hybox01">
                        <table width="658" height="155" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="25" height="70" align="center">&nbsp;</td>
                                <td width="633" align="left" class="hybt01"> 欢迎进入中国社会科学年鉴数据库</td>
                            </tr>
                        </table>
                        <table width="640" height="21" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="30" height="21" align="center"><br></td>

                                <td width="440" align="left" class="hybt02"><span class="hybt03"><span id="times">
				</span>
			<script language="JavaScript">
				function setTime()
                {
                    tmpDate = new Date();
                    date = tmpDate.getDate();
                    month= tmpDate.getMonth() + 1 ;
                    year= tmpDate.getFullYear();
                    var timestr = "";

                    timestr +=year;
                    timestr +="年";
                    timestr +=month;
                    timestr +="月";
                    timestr +=date;
                    timestr +="日 ";

                    myArray=new Array(6);
                    myArray[0]="星期日 "
                    myArray[1]="星期一 "
                    myArray[2]="星期二 "
                    myArray[3]="星期三 "
                    myArray[4]="星期四 "
                    myArray[5]="星期五 "
                    myArray[6]="星期六 "
                    weekday=tmpDate.getDay();
                    if (weekday==0 | weekday==6)
                    {
                        timestr +=myArray[weekday]
                    }
                    else
                    {timestr +=myArray[weekday]
                    };
                    hour = tmpDate.getHours();
                    minute = tmpDate.getMinutes();
                    second = tmpDate.getSeconds();
                    timestr +=hour;
                    timestr +="时";
                    timestr +=minute;
                    timestr +="分";
                    timestr +=second;
                    timestr +="秒 ";
                    document.getElementById("times").innerText = timestr;
                }

                setInterval("setTime()",1000);
				</script>
                                <td width="30" align="center"><br></td>
                                <td width="44" align="left" class="hybt02"><br></td>
                                <td width="30" align="center"><br></td>
                                <td width="53" align="left" class="hybt02"><br></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<!-- 主界面结束 -->
<!-- 脚部开始 -->
<%@include file="/WEB-INF/jsp/admin/include/footer.jsp"%>
<%-- 脚部结束 --%>
<!-- Core  -->

<script src="${ctx}/static/remark/global/vendor/jquery/jquery.js"></script>
<script src="${ctx}/static/remark/global/vendor/jquery-cookie/jquery.cookie.js"></script>
<script src="${ctx}/static/remark/global/vendor/bootstrap/bootstrap.js"></script>
<script src="${ctx}/static/remark/global/vendor/animsition/animsition.js"></script>
<script src="${ctx}/static/remark/global/vendor/asscroll/jquery-asScroll.js"></script>
<script src="${ctx}/static/remark/global/vendor/mousewheel/jquery.mousewheel.js"></script>
<script src="${ctx}/static/remark/global/vendor/asscrollable/jquery.asScrollable.all.js"></script>
<script src="${ctx}/static/remark/global/vendor/ashoverscroll/jquery-asHoverScroll.js"></script>
<script src="${ctx}/static/remark/global/vendor/waves/waves.js"></script>
<!-- Plugins -->
<script src="${ctx}/static/remark/global/vendor/jquery-mmenu/jquery.mmenu.min.all.js"></script>
<script src="${ctx}/static/remark/global/vendor/switchery/switchery.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/intro-js/intro.js"></script>
<script src="${ctx}/static/remark/global/vendor/screenfull/screenfull.js"></script>
<script src="${ctx}/static/remark/global/vendor/slidepanel/jquery-slidePanel.js"></script>
<script src="${ctx}/static/remark/global/vendor/jvectormap/jquery-jvectormap.min.js"></script>
<script src="${ctx}/static/remark/global/vendor/jvectormap/maps/jquery-jvectormap-world-mill-en.js"></script>
<script src="${ctx}/static/remark/global/vendor/matchheight/jquery.matchHeight-min.js"></script>
<script src="${ctx}/static/remark/global/vendor/peity/jquery.peity.min.js"></script>
<!-- Scripts -->
<script src="${ctx}/static/remark/global/js/core.js"></script>
<script src="${ctx}/static/remark/assets/js/site.js"></script>
<script src="${ctx}/static/remark/assets/js/sections/menu.js"></script>
<script src="${ctx}/static/remark/assets/js/sections/menubar.js"></script>
<script src="${ctx}/static/remark/assets/js/sections/gridmenu.js"></script>
<script src="${ctx}/static/remark/assets/js/sections/sidebar.js"></script>
<script src="${ctx}/static/remark/global/js/configs/config-colors.js"></script>
<script src="${ctx}/static/remark/assets/js/configs/config-tour.js"></script>
<script src="${ctx}/static/remark/global/js/components/asscrollable.js"></script>
<script src="${ctx}/static/remark/global/js/components/animsition.js"></script>
<script src="${ctx}/static/remark/global/js/components/slidepanel.js"></script>
<script src="${ctx}/static/remark/global/js/components/switchery.js"></script>
<script src="${ctx}/static/remark/global/js/components/tabs.js"></script>
<script src="${ctx}/static/remark/global/js/components/matchheight.js"></script>
<script src="${ctx}/static/remark/global/js/components/jvectormap.js"></script>
<script src="${ctx}/static/remark/global/js/components/peity.js"></script>
<script>
    (function (document, window, $) {
        'use strict';
        var Site = window.Site;
        $(document).ready(function () {
            Site.run();
        });
    })(document, window, jQuery);
</script>
</body>
</html>



