function clearInput() {
    $("input[ name='password' ] ").val("");
    $("input[ name='oldpassword' ] ").val("");
    $("input[ name='password_again' ] ").val("");

}
function subfuction(userName) {
    var password = document.getElementById("password").value;
    var oldpassword = document.getElementById("oldpassword").value;
    var password_again = document.getElementById("password_again").value;

    if (oldpassword == null || oldpassword == "") {
        alertify.alert("请输入当前密码");
        return;
    }
    if (password == null || password == "") {
        alertify.alert("请输入新密码");
        return;
    }
    if (password_again == null || password_again == "") {
        alertify.alert("请输入确认密码");
        return;
    }
    if(password!=password_again){
        alertify.alert("两次密码不一致");
        return;
    }
    var passwordReg = /^[a-zA-Z0-9]{6,20}$/;
    if(!passwordReg.test(password)){
        alertify.alert("密码长度6-20个字符或数字");
        return;
    }
    $.ajax({
        url: appPath + "/user/center/pwd",
        type: 'post',
        data: {
            userName: userName,
            password: password,
            oldpassword:oldpassword,
        },
        success: function (data) {
            var code = data.status;
            if (code ==1) {
                alertify.alert("修改失败");
            } else if (code == 0) {
                alertify.alert("修改成功");
                clearInput();
            }
        }

    });
}
//在线修改密码
function onlinesSubfuction(userName) {
    var password = document.getElementById("passwords").value;
    var oldpassword = document.getElementById("oldpasswords").value;
    var password_again = document.getElementById("password_agains").value;

    if (oldpassword == null || oldpassword == "") {
        alert("请输入当前密码");
        return;
    }
    if (password == null || password == "") {
        alert("请输入新密码");
        return;
    }
    if (password_again == null || password_again == "") {
        alert("请输入确认密码");
        return;
    }
    if(password!=password_again){
        alert("两次密码不一致");
        return;
    }
    var passwordReg = /^[a-zA-Z0-9]{6,20}$/;
    if(!passwordReg.test(password)){
        alert("密码长度6-20个字符或数字");
        return;
    }
    $.ajax({
        url: appPath + "/user/center/onlinepwd",
        type: 'post',
        data: {
            userName: userName,
            password: password,
            oldpassword:oldpassword,
        },
        success: function (data) {
            var code = data.status;
            if (code ==1) {
                alert("修改失败");
            } else if (code == 0) {
                alert("修改成功");
                clearInput();
            }
        }

    });
}
// 初始化加载 我得关注
function concernShow() {
    $.ajax({
        url :appPath + "/user/center/adminConcern",
        type:'post',
        data:{
            username:username,
        },
        success:function(data){
            var json = JSON.stringify(data[0]);
            var data2 = eval(json);
            var dad= "<div class='userright'>"
                +"<div class='userright-t'>"
                +"<dl>"
                +"<dt><img src='../static/client/img/usericon3.png'>已关注<b>"+data[1]+"</b>个关键词</dt>"
                +"<dd><img src='../static/client/img/usericon3.png'>本周新增关注<b>"+data[2]+"</b>个</dd>"
                +"<dd><img src='../static/client/img/usericon2.png'>新增收藏<b>"+data[3]+"</b>条</dd>"
                +"<dd><img src='../static/client/img/usericon1.png'>新增拼接阅读<b>"+data[4]+"</b>条</dd>"
                +"</dl>"
                +"</div>"
                +"<div class='userright-b'>"
                +"<div class='carebox' >";
            var dbd  ="";
            var dcd="</div></div></div>";
            for(var i in data2)
            {
                var da= "<a href='javascript:void(0)'><em onclick=window.open('/search?kw="+data2[i].name+"') title='"+data2[i].name+"'>"+data2[i].name.substring(0,6)+"</em><b onclick='concernDelete("+data2[i].id+")'>-</b>";
                dbd=dbd+da;
            }
            var ddd=dad+dbd+dcd;
            $("#mygz").html(ddd);
        }

    });

}
function concernShowPassword() {
    $.ajax({
        url :appPath + "/user/center/adminConcern",
        type:'post',
        data:{
            username:username,
        },
        success:function(data){
            var json = JSON.stringify(data[0]);
            var dad="<div class='userright-t'>"
                +"<dl>"
                +"<dt><img src='../static/client/img/usericon3.png'>已关注<b>"+data[1]+"</b>个关键词</dt>"
                +"<dd><img src='../static/client/img/usericon3.png'>本周新增关注<b>"+data[2]+"</b>个</dd>"
                +"<dd><img src='../static/client/img/usericon2.png'>新增收藏<b>"+data[3]+"</b>条</dd>"
                +"<dd><img src='../static/client/img/usericon1.png'>新增拼接阅读<b>"+data[4]+"</b>条</dd>"
                +"</dl>"
                +"</div>"

            $("#mymmt").html(dad);
            $("#mypjt").html(dad);

        }

    });

}
// 删除关注词
function concernDelete(id) {
    $.ajax({
        url: appPath +"/user/center/deleteConcern",
        type: 'post',
        data: {
            id: id,
            username:username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("删除成功");
                concernShow(username);
                // window.location.href = "../view/usercenter";
                // window.location.reload();
            } else {
                alertify.alert("删除失败");
            }
        }

    });

}
function cutstr(str, len) {
    var str_length = 0;
    var str_len = 0;
    str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            //中文字符的长度经编码之后大于4
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    //如果给定字符串小于指定长度，则返回源字符串；
    if (str_length < len) {
        return str;
    }
}
var PageCount=null;
var PageIndex=null;
// 加载我得收藏
function collectShow(foldername,PageIndex,currPage) {
    // foldername 收藏夹名称   PageIndex 当前页数   currPage 一次展示几条
    $.ajax({
        url: appPath +"/user/center/collectShow",
        type: 'post',
        data: {
            username:username,
            folderName:foldername,
            PageIndex:PageIndex,
            currPage:currPage,

        },
        success: function (data) {
            var json = JSON.stringify(data[0]);

            PageCount= Number(data[1]);
            PageIndex=Number(data[2]);
            var data2 = eval(json);
            var dad="  <dl style='height: 495px'>";
            var dbd="";
            var dcd=" </dl>";

            var pageOne="<div class='user_pre_next'>";
            var pageTree= "</div>";
            for(var i in data2)
            {
                var absss=cutstr(data2[i].abs,205);
                var da=  "<dd >"
                    +"<span type='button' onclick='collectDelete("+data2[i].id+")'>"
                    + "<em class='clickCollect' id="+data2[i].id+" >取消收藏</em></span>"
                    +"<h3><a href='../article/articleInfo?zid="+data2[i].tid+"&bookcode="+data2[i].bookcode+"' target='_blank'>"+data2[i].title+"</a></h3>"
                    +"<h4><b>"+data2[i].source+"</b></h4>"
                    +"<p><span title='"+data2[i].abs+"'>"+absss+"</span></p>"
                    +"</dd>";
                dbd=dbd+da;
            }
            var pgj=PageIndex-1;
            var pgjj=PageIndex+1;
            var pcj=PageCount-1;
            var pcjj=PageCount+1;

            var pa="<div class='pre_next'><span><a href='javascript:void(0);' onclick='setPageUser("+pgj+")' class='pre'>&lt; 上一页</a></span>";
            var q="";
            var w="";
            var e="";
            var r="";
            var qq="";
            var ww="";
            var ee="";
            var rr="";
            var qqq="";
            var www="";
            var eee="";
            var aaa="";
            var sss="";
            if(PageCount>1&&PageCount<=5){
                for(var i = 1;i < pcjj; i++) {
                    if(i==PageIndex){
                        q+="<a id='a_"+PageIndex+"' onclick='return false;' href='javascript:void(0);' class='cur'>"+PageIndex+"</a>";
                    }else {
                        q+="<a id='a_"+i+"' onclick='setPageUser("+i+");return false;' href='javascript:void(0);' >"+i+"</a>";
                    }
                }
            }
            if(PageCount>5){
                if(PageIndex<4){
                    for(var i = 1;i < pgj; i++) {
                        w+="<span><a id='a_"+i+"' onclick='setPageUser("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    e="<span><a id='a_"+PageIndex+"' onclick='return false;' href='javascript:void(0);'";
                    if(PageIndex<100){
                        r="class='cur'";
                    }
                    qq=">"+PageIndex+"</a></span>";
                }else{
                    ww="<span><a id='a_1' onclick='setPageUser(1);return false;' href='javascript:void(0);'>1</a></span>"
                        +"<span><a href='#' onclick='return false;' style='cursor: default;'>...</a></span>";
                    for(var i = pgj;i < PageIndex; i++) {
                        ee+="<span><a id='a_"+i+"' onclick='setPageUser("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    rr="<span><a id='a_"+PageIndex+"' onclick='return false;' href='javascript:void(0);'";
                    if(PageIndex<100){
                        qqq="class='cur'";
                    }
                    if(PageIndex>=100){
                        qqq= "class='cur'";
                    }
                    www=">"+PageIndex+"</a></span>";
                }
                var pcj2=PageCount-2;
                var pcjj2=PageCount+2;
                if(PageIndex>=pcj2 || pcj2<=0){
                    for(var i = pgjj;i < pcjj; i++) {
                        eee+="<span><a id='a_"+i+"' onclick='setPageUser("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                }else {
                    for(var i = pgjj;i < 5; i++) {
                        eee+="<span><a id='a_"+i+"' onclick='setPageUser("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    aaa="<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        +"<span><a id='a_"+PageCount+"' onclick='setPageUser("+PageCount+");return false;'href='javascript:void(0);'>"+PageCount+"</a></span>";
                }

            }
            if(PageCount==1){
                sss="<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz="<a href='#' class='next' onclick='setPageUser("+pgjj+")'>下一页 &gt;</a>"
                +"<span>共"+PageCount+"页</span>"
                +"<span>到第</span>"
                +"<input id='pageNum' name='' class='pre_txt' type='text'>"
                +"<span>页</span>"
                +" <input name='' class='pre_sub' value='确定' onclick='pageJump()' type='button'>"
                +"<input type='hidden' id='pageCount' value='"+PageCount+"'/>"
                +" </div>";
            if(data2.length!=0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#collectList").html(ddd);
            }else{
                $("#collectList").empty();
                // alertify.alert("暂无收藏！");
                $("#collectList").html("<span >暂无收藏</span>");
            }
        }

    });

}
// 下一页执行方法
function setPageUser(page) {
    if (page<=0){
        page=1;
    }else if(page>=PageCount){
        page=PageCount;
    }
    collectShow(folderName,page,showSize);
};
//收藏夹 点击方法
function collectShowByFolder(foldername) {
    // if(folderName!=""){
    //     collectShow("",'1',showSize);
    // }
    folderName=foldername;
    collectShow(foldername,'1',showSize);
}
// 跳转  分页
function pageJump(){
    var page = $("#pageNum").val();
    var pageCount = $("#pageCount").val();
    if(page!=""&&/^[0-9]*$/.test(page)){
        if(parseInt(pageCount)<parseInt(page)){
            page=$("#pageCount").val();
        }
        setPageUser(page);
    }else{
        alertify.alert("请输入正确的页码!");
    }
}
// 删除 我的收藏
function collectDelete(id) {
    $.ajax({
        url: appPath +"/user/center/collectDelete",
        type: 'post',
        data: {
            id: id,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("取消成功");
                collectShow(folderName,1,showSize);
            } else {
                alertify.alert("取消失败");
            }
        }

    });

}
// 加载 收藏夹
function collectFolder() {
    $.ajax({
        url: appPath +"/user/center/collectFolder",
        type: 'post',
        data: {
            username:username,
        },
        success: function (data) {
            var json = JSON.stringify(data);
            var data2 = eval(json);
            var dad="<h3><a href='##'  class='addfave' title='新建收藏夹'><img src='/static/client/img/favo_add.png'></a></h3>"
                +"<ul id='test'>";
            var dbd="";
            var ddd="</ul>";

            for(var i in data2)
            {
                var da= "<li>"
                    +"<a class='folName' style='padding-left:0px;float: left;' onclick="+"collectShowByFolder('"+data2[i].foldername+"')"+"><span title='"+data2[i].foldername+"'>"+data2[i].foldername.substring(0,7)+"</span></a>"
                    +"<a href='##' class='favedelete' onclick="+"folderD('"+data2[i].foldername+"')"+"><img src='/static/client/img/favo_icon3.png'>删除</a>"
                    +"<a href='##' class='changename'><img src='/static/client/img/favo_icon2.png'>重命名</a>"
                    +"<div class='favovhange'>"
                    +"<input  index='"+i+"' type='text'  class='changeinfo' value='"+data2[i].foldername+"' >"
                    +"<input  onclick='folderReName(\""+data2[i].foldername+"\","+i+")' type='button' value='保存' class='changeinfosave'>"
                    +"</div>"
                    +"</li>";
                dbd=dbd+da;
            }
            var div=dad+dbd+ddd;
            $("#folder").html(div);
        }

    });

}
//删除时 需要用的收藏夹名字
function folderD(name) {
    folderName=name;
}
//删除收藏文件夹
function folderDelete(){
    $.ajax({
        url: appPath +"/user/center/collectFolderDelete",
        type: 'post',
        data: {
            folderName:folderName,
            username:username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("删除成功");
                $(".layer").fadeOut();
                collectFolder();
            } else {
                alertify.alert("删除失败");
            }
        }

    });


}
//收藏夹重命名
function folderReName(oldFolder,i){
    var newFolder=$("input[index='"+i+"']").val();
    var pattern = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
    if(!pattern.test(newFolder)){
        alertify.alert("包含非法字符");
        return;
    }
    if(newFolder==oldFolder){
        return;
    }
    $.ajax({
        url: appPath +"/user/center/collectFolderReName",
        type: 'post',
        data: {
            folderName:newFolder,
            oldFolderName:oldFolder,
            username:username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("修改成功");
                $(".layer").fadeOut();
                collectFolder();
            } else if(code ==2){
                alertify.alert("该名称已经存在！！！");
            }else {
                alertify.alert("修改失败");
            }
        }

    });
}
// 收藏夹添加
function insertFolder(){
    var fName = document.getElementById("addfavetetx").value;
    var pattern = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
    if(!pattern.test(fName)){
        alertify.alert("包含非法字符");
        return;
    }
    if(fName==null||fName==""){
        alertify.alert("请输入收藏夹名称！！！");
        return;
    }
    $.ajax({
        url: appPath +"/user/center/insertFolder",
        type: 'post',
        data: {
            folderName:fName,
            userName:username,
        },
        success: function (data) {
            var code = data.state;
            if (code == 0) {
                alertify.alert("添加成功");
                $(".layer").fadeOut();
                collectFolder();
            } else if(code ==2){
                alertify.alert("该名称已经存在！！");
            }else  if(code ==3){
                alertify.alert("已经超过最大收藏夹数量！！！！");
            }else {
                alertify.alert("添加失败");
            }
        }

    });
}
//加载我得拼接
var PageCountSplice=null;
var PageIndexSplice=null;
function spliceShow(PageIndexSplice,currPageSplice) {
    $.ajax({
        url: appPath +"/user/center/spliceShow",
        type: 'post',
        data: {
            username:username,
            PageIndex:PageIndexSplice,
            currPage:currPageSplice,
        },
        success: function (data) {
            var json = JSON.stringify(data[0]);
            var data2 = eval(json);
            PageCountSplice= Number(data[1]);
            PageIndexSplice=Number(data[2]);
            var dad="<ul>";
            var dbd="";
            var dcd="</ul>";

            var pageOne="<div class='user_pre_next'>";
            var pageTree= "</div>";
            for(var i in data2)
            {
                var ii= Number(i)+1;
                //格式化日期
                var t1=new Date(data2[i].gmtCreate).Format("MM-dd hh:mm");
                var zzid=data2[i].zid;
                var da= "<li>"
                    +"<p><span title='"+data2[i].title+"'>"+ii+"."+data2[i].title+"</span></p><span>"+t1+"</span><a class='splicedelete' onclick='deleteS("+data2[i].id+")'>删除记录</a><a target='_blank' href='/article/articleInfo?zid="+data2[i].zid+"&bookcode="+data2[i].bookcode+"' >继续阅读</a>"
                    +"</li>";
                dbd=dbd+da;
            }

            var pgj=PageIndexSplice-1;
            var pgjj=PageIndexSplice+1;
            var pcj=PageCountSplice-1;
            var pcjj=PageCountSplice+1;

            var pa="<div class='pre_next'><span><a href='javascript:void(0);' onclick='setPageUserSplice("+pgj+")' class='pre'>&lt; 上一页</a></span>";
            var q="";
            var w="";
            var e="";
            var r="";
            var qq="";
            var ww="";
            var ee="";
            var rr="";
            var qqq="";
            var www="";
            var eee="";
            var aaa="";
            var sss="";
            if(PageCountSplice>1&&PageCountSplice<=5){
                for(var i = 1;i < pcjj; i++) {
                    if(i==PageIndexSplice){
                        q+="<a id='a_"+PageIndexSplice+"' onclick='return false;' href='javascript:void(0);' class='cur'>"+PageIndexSplice+"</a>";
                    }else {
                        q+="<a id='a_"+i+"' onclick='setPageUserSplice("+i+");return false;' href='javascript:void(0);' >"+i+"</a>";
                    }
                }
            }
            if(PageCountSplice>5){
                if(PageIndexSplice<4){
                    for(var i = 1;i < pgj; i++) {
                        w+="<span><a id='a_"+i+"' onclick='setPageUserSplice("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    e="<span><a id='a_"+PageIndexSplice+"' onclick='return false;' href='javascript:void(0);'";
                    if(PageIndexSplice<100){
                        r="class='cur'";
                    }
                    qq=">"+PageIndexSplice+"</a></span>";
                }else{
                    ww="<span><a id='a_1' onclick='setPageUserSplice(1);return false;' href='javascript:void(0);'>1</a></span>"
                        +"<span><a href='#' onclick='return false;' style='cursor: default;'>...</a></span>";
                    for(var i = pgj;i < PageIndexSplice; i++) {
                        ee+="<span><a id='a_"+i+"' onclick='setPageUserSplice("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    rr="<span><a id='a_"+PageIndexSplice+"' onclick='return false;' href='javascript:void(0);'";
                    if(PageIndexSplice<100){
                        qqq="class='cur'";
                    }
                    if(PageIndexSplice>=100){
                        qqq= "class='cur'";
                    }
                    www=">"+PageIndexSplice+"</a></span>";
                }
                var pcj2=PageCountSplice-2;
                var pcjj2=PageCountSplice+2;
                if(PageIndexSplice>=pcj2 || pcj2<=0){
                    for(var i = pgjj;i < pcjj; i++) {
                        eee+="<span><a id='a_"+i+"' onclick='setPageUserSplice("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                }else {
                    for(var i = pgjj;i < 5; i++) {
                        eee+="<span><a id='a_"+i+"' onclick='setPageUserSplice("+i+");return false;' href='javascript:void(0);'>"+i+"</a></span>";
                    }
                    aaa="<span><a href='javascript:void(0);' onclick='return false;' style='cursor: default;'>...</a></span>"
                        +"<span><a id='a_"+PageCountSplice+"' onclick='setPageUserSplice("+PageCountSplice+");return false;'href='javascript:void(0);'>"+PageCountSplice+"</a></span>";
                }

            }
            if(PageCountSplice==1){
                sss="<span><a id='a_1' onclick='return false;' href='javascript:void(0);' class='cur'>1</a></span>";
            }
            var zzz="<a href='#' class='next' onclick='setPageUserSplice("+pgjj+")'>下一页 &gt;</a>"
                +"<span>共"+PageCountSplice+"页</span>"
                +"<span>到第</span>"
                +"<input id='pageNumSplice' name='' class='pre_txt' type='text'>"
                +"<span>页</span>"
                +" <input name='' class='pre_sub' value='确定' onclick='pageJumpSplice()' type='button'>"
                +"<input type='hidden' id='PageCountSplice' value='"+PageCountSplice+"'/>"
                +" </div>";
            if(data2.length!=0) {
                var ddd = dad + dbd + dcd + pageOne + pa + q + w + e + r + qq + ww + ee + rr + qqq + www + eee + aaa + sss + zzz + pageTree;
                $("#splice").html(ddd);
            }else{
                $("#splice").empty();
                // alertify.alert("暂无收藏！");
                $("#splice").html("<span >暂无拼接</span>");
            }
        }

    });

}
function deleteS(id) {
    spliceDeleteId=id;
}
// 下一页执行方法
function setPageUserSplice(page) {
    if (page<=0){
        page=1;
    }else if(page>=PageCountSplice){
        page=PageCountSplice;
    }
    spliceShow(page,showSizeSplice);
};
// 跳转  分页
function pageJumpSplice(){
    var page = $("#pageNumSplice").val();
    var pageCount = $("#PageCountSplice").val();
    if(page!=""&&/^[0-9]*$/.test(page)){
        if(parseInt(pageCount)<parseInt(page)){
            page=$("#PageCountSplice").val();
        }
        setPageUserSplice(page);
    }else{
        alertify.alert("请输入正确的页码!");
    }
}
//格式化日期方法
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
//删除 拼接阅读
function deleteSplice() {

    $.ajax({
        url: appPath +"/user/center/deleteSplice",
        type: 'post',
        data: {
            id:spliceDeleteId,
            username:username,
        },
        success: function (data) {
            var code = data.code;
            if (code == 0) {
                alertify.alert("删除成功");
                spliceShow("1",showSizeSplice);
                $(".layer").fadeOut();
            } else {
                alertify.alert("删除失败");
            }
        }

    });

}
