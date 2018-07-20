
var index=0;//代表目前第几个dd是空的
$(function () {
    /*ajax保存拼接信息*/
    $(".pinjietit a:eq(1)").on({
        click:function () {
            var bookcde = $("#bookcode").val();
            var dds=$(".pinjielist dl dd");
            var title="";
            var zid="";
            var bookcode="";
            for(var i=0; i<index; i++){
                if(i==index-1){
                    zid+=$(dds[i]).attr("zid");
                    bookcode+=$(dds[i]).attr("bookcode");
                    title+=$(dds[i]).find("h3").find("a").text().substring(1);
                }else{
                    zid+=$(dds[i]).attr("zid")+";";
                    bookcode+=$(dds[i]).attr("bookcode")+";";
                    title+=$(dds[i]).find("h3").find("a").text().substring(1)+";";
                }
            }
            if(zid==""||bookcode==""){
                return;
            }
            var iiiPP=$("#iiiPP").val();
            $.ajax({
                url: appPath + "/organize/authorization?name=拼接&dateTime="+Math.random()+"&iiiPP="+iiiPP,
                type: "get",
                dataType: "json",
                async: true,
                success:function (data) {
                    if (data.status == 0){
                        var tempwindow=window.open();
                            $.ajax({
                                url:appPath+"/user/center/mySpliceAdd",
                                type:'post',
                                data:{'username':username, 'zid':zid,'bookcode':bookcode, 'title':title},
                                success:function (data) {
                                    var code = data.code;
                                    if (code == 0) {
                                        // tempwindow.location="/article/articleInfo?zid="+zid+"&bookcode="+bookcode;
                                        window.open("/article/articleInfo?zid="+zid+"&bookcode="+bookcode);
                                    }else{
                                        alertify.alert("拼接失败！！");
                                    }
                                }
                            });

                    }else if(data.status == 2){
                        alertify.alert("权限不足");
                    }else if(data.status == 3){
                        alertify.alert("请先登陆,再进行拼接！！");
                    }
                }
            })

        }
    });
    /*清空拼接*/
    $(".pinjietit a:eq(0)").on({
        click:function () {
            var dds=$(".pinjielist dl dd");
            dds.attr("zid","");
            dds.html('<span><img src="/static/client/img/pinjie_add.jpg" alt=""></span>');
            index=0;
            cleanCookie();
        }
    });
    /*添加拼接*/
    $(".click_pj").on({
       click:function () {
           // if(username==''||username.length==0){
           //     alertify.alert("未登录，请先登录");
           //     return;
           // }
           if(index==4){
               return;
           }
           var fontText=$(this).parent("dd").find("font").html();
           $(this).parent("dd").find("font").replaceWith(fontText);
           var title=$(this).next("h3").find("b").text();
           var content=$(this).parent("dd").find("p").text();
           content=content.substring(0,40)+"...";
           var zid=$(this).next("h3").find("b").attr("zid");
           var bookcode=$(this).next("h3").find("b").attr("bookcode");
           var dds=$(".pinjielist dl dd");
           for(var i=0; i<dds.length;i++){
               if($(dds[i]).attr("zid")==zid){
                   return;
               }
           }
           /*onclick 左右空格不可以删除 ，服务器cookie值不可编译*/
           var str='<i><img onclick = " removePJ('+index+'); " src="/static/client/img/pinjie_delete.png" alt=""></i>';
           str+='<h3><a href="javascript:void(0)"><em>'+(index+1)+'</em>'+title+'</a></h3>';
           str+='<p>'+content+'</p>';
           if(index>=0 && index<=3){
               var dd=$(".pinjielist dl dd:eq("+index+")");
               dd.attr("zid",zid);
               dd.attr("bookcode",bookcode);
               dd.html(str);
               index++;
           }
           setCookie(zid,str,index,bookcode);
       }
    });
});
/*点击X删除单个拼接*/
function removePJ(param) {
    for(var i=param; i<index; i++){
        var dd=$(".pinjielist dl dd:eq("+i+")");
        if(i==3){
            dd.attr("zid","");
            dd.html('<span><img src="/static/client/img/pinjie_add.jpg" alt=""></span>');
        }else{
            var nextdd=$(".pinjielist dl dd:eq("+(i+1)+")");
            dd.attr("zid",nextdd.attr("zid"));
            dd.html(nextdd.html());
            dd.find("img").attr("onclick","removePJ("+i+")");
            dd.find("em").html(i+1);
        }
    }
    delCookie(param+1)
    index--;
}
/*保存Cookie信息*/
function setCookie(zid,str,index,bookcode) {
    var date=new Date();
    date.setTime(date.getTime()+60*60*1000*24*3);
    var expires = "expires=" + date.toUTCString();
    document.cookie="k"+index+"="+escape(zid+"******"+bookcode+"******"+str)+";"+expires;
}
/*获取Cookie信息*/
function getCookie() {
    var cook=document.cookie;
    var cookies=new Array();
    if (cook.length>0){
        for (var i=1;i<5;i++){
            var key="k"+i;
            var c_start=cook.indexOf(key+"=");
            if (c_start!=-1){
                c_start=c_start+key.length+1;
                var c_end=cook.indexOf(";",c_start);
                if (c_end==-1) {
                    c_end=document.cookie.length;
                }
                cookies.push(unescape(document.cookie.substring(c_start,c_end)));
            }
        }
    }
    return cookies;
}
/*将Cookie获得的数据放到元素中*/
function setElement(data) {
    //index=data.length;
    var j=0;
    for (var i=0;i<data.length;i++){
        if (data[i]!="null" && data[i]!="undefined" ){
            var datas=data[i].split("******");
            $(".pinjielist dl dd").eq(i).attr("zid",datas[0]);
            $(".pinjielist dl dd").eq(i).attr("bookcode",datas[1]);
            $(".pinjielist dl dd").eq(i).html(datas[2]);
            $(".pinjielist dl dd").eq(i).find("em").text(i+1);
            $(".pinjielist dl dd").eq(i).find("img").attr("onclick","removePJ("+i+");")
            j++;
        }
    }
    index=j;

}
/*删除Cookie*/
function delCookie(indexCookie) {
    var getCook=getCookie();
    var aaa=indexCookie;
    for (var i=0;i<4-aaa;i++){
        document.cookie="k"+indexCookie+"="+escape(getCook[(indexCookie)]);
        indexCookie++;
    }
    document.cookie="k4="+escape("null");
    //index--;
}
/*清空Cookie*/
function cleanCookie() {
    for (var i=1;i<5;i++){
        document.cookie="k"+i+"="+escape("null");
        }
}
$(function () {
    setElement(getCookie());
})

