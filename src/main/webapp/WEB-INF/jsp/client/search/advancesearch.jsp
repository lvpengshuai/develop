<%--
  Created by IntelliJ IDEA.
  User: epro
  Date: 2017/8/23
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="advsearch" action="${pageContext.request.contextPath}/search">
    <div class="selectbox">
        <select name="advposition" size="1">
            <option value="title">标题</option>
            <option value="TextContent">正文</option>
        </select>
    </div>
    <div class="selectbox">
        <select name="advaccurate" size="1">
            <option value="vague">模糊</option>
            <option value="accurate">精确</option>
        </select>
    </div>
    <input id="advwd" name="advwd" type="text" class="highsearchtext" value="">
    <div class="selectbox">
        <select  id="adcimpact" name="adcimpact" size="1">
            <option value="ADN">与</option>
            <option value="OR">或</option>
            <option value="NOT">非</option>
        </select>
    </div>
    <div class="selectbox">
        <select name="advposition" size="1">
            <option value="title">标题</option>
            <option value="TextContent">正文</option>
        </select>
    </div>
    <div class="selectbox">
        <select name="advaccurate" size="1">
            <option value="vague">模糊</option>
            <option value="accurate">精确</option>
        </select>
    </div>
    <input id="advwd" name="advwd" type="text" class="highsearchtext" value="">
    <div class="selectbox">
        <select  id="adcimpact" name="adcimpact" size="1">
            <option value="ADN">与</option>
            <option value="OR">或</option>
            <option value="NOT">非</option>
        </select>
    </div>
    <div class="selectbox">
        <select name="advposition" size="1">
            <option value="title">标题</option>
            <option value="TextContent">正文</option>
        </select>
    </div>
    <div class="selectbox">
        <select name="advaccurate" size="1">
            <option value="vague">模糊</option>
            <option value="accurate">精确</option>
        </select>
    </div>
    <input id="advwd" name="advwd" type="text" class="highsearchtext" value="">
    <div class="selectbox">
        <select  id="adcimpact" name="adcimpact" size="1">
            <option value="ADN">与</option>
            <option value="OR">或</option>
            <option value="NOT">非</option>
        </select>
    </div>
<input type="submit" value="test">
</form>
