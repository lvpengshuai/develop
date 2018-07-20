$(document).ready(function() {
   //  alert("666");
   //  var maxbookcode = $("#maxbookcode").val();
   // showHotWord(maxbookcode);
   // showHotPerson(maxbookcode);
});
window.onload=function () {
    var maxbookcode = $("#maxbookcode").val();
    showHotWord(maxbookcode);
    showHotPerson(maxbookcode);
};
function mySpliceAdd() {
    // if(username==null||username==""){
    //     alertify.alert("登录后才能进行拼接阅读.");
    //     return;
    // }
    var iiiPP=$("#iiiPP").val();
    //获取zid
    var id = document.getElementsByName('titleID');
    //zid值
    var value = [];
    var zid="";
    var bookcode = "";
    // title值
    var title="";
    for(var i = 0; i < id.length; i++){
        if(id[i].checked){
            value.push(id[i].value);
            var vl=id[i].value.split("|");
            title=title+vl[1]+"+";
            zid=zid+vl[0]+";";
            bookcode=bookcode+vl[2]+";";
        }
    }
    zid=zid.substring(0,zid.length-1);
    title=title.substring(0,title.length-1);
    bookcode=bookcode.substring(0,bookcode.length-1);
    if(value.length>5){
        alertify.alert("只能拼接5条目录！！！");
        return;
    }
    if(value.length==0){
        alertify.alert("请选择拼接阅读得目录！！！");
        return;
    }
    $.ajax({
        url: appPath + "/organize/authorization",
        acynsc:false,
        type: "get",
        data: {
            "name": "拼接",
            dateTime: Math.random(),//随机函数------------------解决ie下兼容问题
            iiiPP:iiiPP,
        },
        dataType: "json",
        success:function (data) {
            if (data.status == 0){
                    $.ajax({
                        url: appPath +"/user/center/mySpliceAdd",
                        acynsc:false,
                        type: 'post',
                        data: {
                            username: username,
                            zid: zid,
                            title:title,
                            bookcode:bookcode,
                        },
                        success: function (data) {
                            var code = data.code;
                            if (code == 0) {
                                window.open("/article/articleInfo?zid="+zid+"&bookcode="+bookcode,"_blank");
                            } else {
                                alertify.alert("拼接失败");
                            }
                        }

                    });

            }else if (data.status == 2){
                alertify.alert("权限不足");
            } if(data.status == 3){
                alertify.confirm("请先登陆,再进行拼接！！", function (e) {
                    if (e) {
                        var id = document.getElementsByName('titleID');
                        //zid值
                        if (id.length > 0) {
                            var value = [];
                            var zid = "";
                            var bookcode = "";
                            // title值
                            var title = "";
                            for (var i = 0; i < id.length; i++) {
                                if (id[i].checked) {
                                    value.push(id[i].value);
                                    var vl = id[i].value.split("|");
                                    title = title + vl[1] + "+";
                                    zid = zid + vl[0] + ";";
                                    bookcode = bookcode + vl[2] + ";";
                                }
                            }
                            zid = zid.substring(0, zid.length - 1);
                            title = title.substring(0, title.length - 1);
                            bookcode = bookcode.substring(0, bookcode.length - 1);
                            if (zid == "" || bookcode == "") {
                                window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
                            } else {
                                window.open(encodeURI("/login?SKT=/article/articleInfo?zid=" + zid + "&bookcode=" + bookcode), "_blank");
                            }
                        } else {
                            window.location.href = encodeURI("/login?SKT=" + window.location.pathname + window.location.search);
                        }
                    }
                });
            }
        }
    })
}
function gethotevent(page,year,bookAbbreviate) {
    $(".activitylist").HoverTreeScroll(1000);
    //$("#booklist"+year).css({"color":"blue","font-size":"20px"});
    $.ajax({
        url: appPath +"/book/hotevents",
        type: 'post',
        data: {
            year: year,
            pageIndex: page,
            bookAbbreviate:bookAbbreviate
        },
        success: function (data) {
            $("#hotevents").html(data);
        }
    });
}

