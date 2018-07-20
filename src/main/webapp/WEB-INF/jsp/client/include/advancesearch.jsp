<%--
  Created by IntelliJ IDEA.
  User: epro
  Date: 2017/8/23
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layer layer-highsearch">
    <div class="layerbg"></div>
    <form id="advsearch" action="${pageContext.request.contextPath}/search">
        <input type="hidden" value="true" name="ifadv">
        <div class="layerbox">
            <span class="close"><img src="${ctx}/static/client/img/close.png" alt=""></span>
            <div class="searchchose">
                <label><input checked class="searchchoseclick01" type="radio" name="tp" value="nj" id="RadioGroup1_0"> 搜年鉴</label>
                <label><input class="searchchoseclick02" type="radio" name="tp" value="wz" id="RadioGroup1_1">搜章节</label>
            </div>
            <div class="searchshow01">
                <div class="highsearch">
                    <ul style="margin-left: 120px;">
                        <li>
                            <span>年鉴年份：</span>
                            <span>从</span>
                            <div class="selectbox">
                                <select name="st" id="st" size="1">
                                    <option value="">不限</option>
                                    <c:forEach var="sub" varStatus="i" begin="0" end="50">
                                        <option value="${2017-i.index}">${2017-i.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <span>到</span>
                            <div class="selectbox">
                                <select name="nt" id="nt" size="1">
                                    <option value="">不限</option>
                                    <c:forEach var="sub" varStatus="i" begin="0" end="50">
                                        <option value="${2017-i.index}">${2017-i.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </li>
                        <li>
                            <%-- <div class="selectbox">
                                 <select name="ath" size="1">
                                     <option>主编</option>
                                     &lt;%&ndash;<option>作者</option>&ndash;%&gt;
                                 </select>
                             </div>--%>
                            <span>主编：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <input name="advath" id="advath" type="text" class="highsearchtext">
                            <div class="selectbox">
                                <select name="athacc" id="athacc" size="1">
                                    <option value="vague">模糊</option>
                                    <option value="accurate">精确</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <span>年鉴名称：</span>
                            <input name="njname" id="njname" type="text" class="highsearchtext w200" placeholder="年鉴名称 / ISBN">
                            <div class="selectbox">
                                <select name="titleacc" id="titleacc" size="1">
                                    <option value="vague">模糊</option>
                                    <option value="accurate">精确</option>
                                </select>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="searchshow02">
                <div class="highsearch">
                    <h5>请输入关键词</h5>
                    <ul>
                        <li>
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
                                    <option value="and">与</option>
                                    <option value="or">或</option>
                                    <option value="not">非</option>
                                </select>
                            </div>
                            <a href="#"><img src="${ctx}/static/client/img/highsearch_1.png">删除</a>
                            <a href="#" class="addclick01"><img src="${ctx}/static/client/img/highsearch_2.png">添加</a>
                        </li>
                        <li class="addli01">
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
                            <input name="advwd" type="text" class="highsearchtext" value="">
                            <div class="selectbox">
                                <select name="adcimpact" size="1">
                                    <option value="and">与</option>
                                    <option value="or">或</option>
                                    <option value="not">非</option>
                                </select>
                            </div>
                            <a href="#" class="remove01"><img src="${ctx}/static/client/img/highsearch_1.png">删除</a>
                            <a href="#" class="addclick02"><img src="${ctx}/static/client/img/highsearch_2.png">添加</a>
                        </li>
                        <li class="addli02">
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
                            <input name="advwd" type="text" class="highsearchtext" value="">
                            <div class="selectbox">
                                <select  name="adcimpact" size="1">
                                    <option value="and">与</option>
                                    <option value="or">或</option>
                                    <option value="not">非</option>
                                </select>
                            </div>
                            <a href="#" class="remove02"><img src="${ctx}/static/client/img/highsearch_1.png">删除</a>
                            <a href="#"><img src="${ctx}/static/client/img/highsearch_2.png">添加</a>
                        </li>
                    </ul>

                    <h5>请选择来源年鉴</h5>
                    <ul style="margin-left: 120px;">
                        <li>
                            <span>年鉴年份：</span>
                            <span>从</span>
                            <div class="selectbox">
                                <select name="st" size="1">
                                    <option value="">不限</option>
                                    <c:forEach var="sub" varStatus="i" begin="0" end="50">
                                        <option value="${2017-i.index}">${2017-i.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <span>到</span>
                            <div class="selectbox">
                                <select name="nt" size="1">
                                    <option value="">不限</option>
                                    <c:forEach var="sub" varStatus="i" begin="0" end="50">
                                        <option value="${2017-i.index}">${2017-i.index}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </li>
                        <li>
                            <%-- <div class="selectbox">
                                 <select name="ath" size="1">
                                     <option>主编</option>
                                     &lt;%&ndash;<option>作者</option>&ndash;%&gt;
                                 </select>
                             </div>--%>
                            <span>主编：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <input name="advath"  type="text" class="highsearchtext">
                            <div class="selectbox">
                                <select name="athacc" size="1">
                                    <option value="vague">模糊</option>
                                    <option value="accurate">精确</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <span>年鉴名称：</span>
                            <input name="njname" type="text" class="highsearchtext w200" placeholder="年鉴名称 / ISBN">
                            <div class="selectbox">
                                <select name="titleacc" size="1">
                                    <option value="vague">模糊</option>
                                    <option value="accurate">精确</option>
                                </select>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="btns"><a style="background:#a0a0a0" href="javascript:void(0);" id="emptys"  >重置</a><a style="cursor: pointer" onclick="advsubmint()">搜索</a></div>
        </div>
    </form>
</div>
<script>
    function advsubmint() {
        $("#advsearch").submit()
    }
    $(function () {
        $("#emptys").click(function () {
            $("#nt").val("");
            $("#st").val("");
            $("#athacc").val("vague");
            $("#titleacc").val("vague");
            $("#advath").val("");
            $("#njname").val("");
        })
    })
    $(function () {
        //高级检索
        $(".addli01").hide();
        $(".addli02").hide();
        $(".remove02").click(function () {
            $(".addli02").hide();
        })
        $(".remove01").click(function () {
            $(".addli01").hide();
        })
    })
    //高级检索
    $(".searchchoseclick01").click(function(){
        $(".searchshow01").show();
        $(".searchshow02").hide();
    });
    $(".searchchoseclick02").click(function(){
        $(".searchshow02").show();
        $(".searchshow01").hide();
    });

    $(".addclick01").click(function(){
        $(".addli01").show();
    });
    $(".addclick02").click(function(){
        $(".addli02").show();
    });
    $(".addclick03").click(function(){
        $(".addli03").show();
    });
    $(".addclick04").click(function(){
        $(".addli04").show();
    });
    $(".addclick05").click(function(){
        $(".addli05").show();
    });
</script>