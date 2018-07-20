<%@ page import="com.trs.core.util.IPUtil" %><%--
  Created by IntelliJ IDEA.
  User: zly
  Date: 2017-3-3
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 脚部开始 -->
<footer class="site-footer">
    <div class="site-footer-legal">© 2017 <a href="${pageContext.request.contextPath}/admin/home">社会科学出版社</a></div>
    <div class="site-footer-legal" style="margin-left: 30px;">服务器：<%= IPUtil.getServerIp() %></div>
    <div class="site-footer-right">Crafted with by TRS.<span> Version：5.0.1${webapp.build.version}</span>
    </div>
</footer>
<%-- 脚部结束 --%>