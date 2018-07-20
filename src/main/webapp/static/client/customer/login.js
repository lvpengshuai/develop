var loginManager = (function () {

    function init() {
        // 配置表单验证基本验证
        $.extend($.validator.messages, {
            required: '<span class="valid_message" style="font-size: 12px;width: 50px">必填</span>',
            equalTo: '<span class="valid_message" style="font-size: 12px;">两次输入的密码不一致</span>',
            minlength: "50",
        });

        /*验证账户名称（支持数字、字母、下划线，下划线不能开头的组合4-20个字符）*/
        $.validator.addMethod("userName", function (value, element) {
            var roleNameReg = /^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]{4,20}$/;
            $("#userNameValue").html('');
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span class='valid_message'>支持字母数字下划线,下划线不能开头和结尾，数字不能开头，4-20字符</span>");

        /*验证账户名称（支持数字、字母、下划线，下划线不能开头的组合4-20个字符）*/
        $.validator.addMethod("userName-user", function (value, element) {
            var roleNameReg = /^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]{3,20}$/;
            $("#userNameValue").html('');
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span class='valid_message' style='width: 60px'>格式非法</span>");

        /* 验证密码 */
        $.validator.addMethod("password", function (value, element) {
            var roleNameReg =  /^[a-zA-Z0-9_.\[\]]{6,20}$/;
            //$("#realNameTip").html('');
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span class='valid_message'>数字、字母、下划线（6-20个字符）</span>");

        /* 验证密码 */
        $.validator.addMethod("password-pwd", function (value, element) {
            var roleNameReg =  /^[a-zA-Z0-9_.\[\]]{6,20}$/;
            //$("#realNameTip").html('');
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span class='valid_message' style='width: 60px'>格式非法</span>");
    }
    $(document).keydown(function () {
        if(event.keyCode==13) {
            $("#userNameValue").text("");
            $("#code").text("");
            $("#subForm").submit();
        }
    });
    //form 表单验证
    $('#subForm').validate({
        showErrors : function(errorMap, errorList) {
            // 遍历错误列表
            for(var obj in errorMap) {
                // 自定义错误提示效果
                $('#' + obj).text('');
            }
            // 此处注意，一定要调用默认方法，这样保证提示消息的默认效果
            this.defaultShowErrors();
        },
        submitHandler: function () {
            var url = appPath + "/login";
            var options = {
                success: showResponse,
                url: url,
                dataType: "json",
                type: "post",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $("#subForm").ajaxSubmit(options);
        }
    });


    //alertify.alert("提交成功");
    function showResponse(result) {
        if(result.code == 0){
            $("#userNameValue").text(result.msg);
            $("#checkCode").val("");
            reloadcode();
            return false;
        }else if(result.code == -2){
            $("#code").text(result.msg);
            $("#checkCode").val("");
            reloadcode();
            return false;
        }else if(result.code == -3) {
            $("#userNameValue").text(result.msg);
            $("#checkCode").val("");
            reloadcode();
            return false;
        }else if(result.code == 1){
            if(result.returnUrl == "0"){
                // $(".header").append("<iframe id='ifr' src='"+appPath + '/login.jsp?userName='+result.userName+'&password='+result.password+"' style='display:none;'></iframe>");
                layer.closeAll();
                var url=document.location.href;
                layer.open({
                    type: 1 //Page层类型
                    ,
                    area: ['400px', '210px']
                    ,
                    title: '登录成功'
                    ,
                    shade: 0.4 //遮罩透明度
                    ,
                    anim: -1 //0-6的动画形式，-1不开启
                    ,
                    content: '<div style="padding:40px; text-align:center;"><a href="'+url+'"><p style="margin-bottom: 20px"><img src="' + appPath + '/static/client/images/update-sucess.png"/></p><p class="update-sucess" style="font-size: 12px">登录成功</p></a></div>'
                });
                layer.closeAll();
                window.location.href = url;
            }else {
                //window.location.href = appPath + '/login.jsp?userName='+result.userName+"&password="+result.password;
                window.location.href = appPath+"/index";
            }
        }else if(result.code == -1){
            $("#userNameValue").text(result.msg);
            $("#checkCode").val("");
            reloadcode();
            return false;
        }

    }
    $("#submit").click(function () {
        if($('#check-1').is(':checked')) {
            $("#topic").val("0");
        }else {
            $("#topic").val("1");
        }
        $("#subForm").submit();
    });

    /* 验证码获得焦点 */
    $("#checkCode").focus(function () {
        $("#code").text("");
    });

    /* 验证码获得焦点 */
    $("#username").focus(function () {
        $("#userNameValue").text("");
    });


    /*忘记密码*/
    function forgetpwd() {
        var username = $("#username").val();
        var url = appPath + "/retrievepwd/forgot";
        window.open(url);
    }

    function reloadcode(){
            $('#imageCode').attr('src',appPath+'/imagecode?createTypeFlag='+Math.floor(Math.random()*100));
    }


    //172.16.8.247/sso/authCode.zhtml
    return {
        init:init,
        forgetpwd:forgetpwd,
        reloadcode:reloadcode,

    }


})();

// loginManager.init();

var imgRN=null;
function loginSUB() {
    // LoginManager.login();
    var userName = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var inputIm = document.getElementById("checkCode").value;
    var flag = document.getElementById("flag").value;
    var code =document.getElementById("checkCode").value;
    //获取当前页面url
    // var urlParm=GetQueryString("url");
    var str=location.href; //取得整个地址栏
    var urlParm=str.split("SKT=")[1]; //各个参数放到数组里

    if (userName == null || userName == "") {
        hideDiv('pop-div');
        alertify.alert("请输入账号");
        loginManager.reloadcode();
        return;
    }
    if (password == null || password == "") {
        hideDiv('pop-div');
        alertify.alert("请输入密码");
        loginManager.reloadcode();
        return;
    }
    // if (inputIm == null || inputIm == "") {
    //     hideDiv('pop-div');
    //     alertify.alert("请输入验证码");
    //     loginManager.reloadcode();
    //     return;
    // }
    // if (imgRN == 0) {
    //     hideDiv('pop-div');
    //     alertify.alert("验证码错误");
    //     loginManager.reloadcode();
    //     return;
    // }
    popupDiv('pop-div');
    var iiiPP=$("#iiiPP").val();
    $.ajax({
        url: appPath +"/login",

        type: 'post',
        data: {
            userName: userName,
            password: password,
            code:inputIm,
            url:urlParm,
            flag:flag,
            iiiPP:iiiPP,
            c:code
        },
        success: function (data) {
            var code = data.code;
            var reUrl=decodeURI(data.url);
            var userName=data.userName
            var code = data.code;
            if (code == 3){
                alertify.alert(data.msg);
                loginManager.reloadcode();
            }
            if (code == 5){
                alertify.alert(data.msg);
                loginManager.reloadcode();
            }
            if (code == 1) {

                // 哪退出 返回那
                if(reUrl.indexOf("searchresult")!=-1){
                    window.history.back();
                    return;
                }
                if(reUrl==null||reUrl=="null"||reUrl=="/"||reUrl==""){
                    window.location.href = "index";
                }else{
                    window.location.href = ""+reUrl+"";
                }

                // 直接返回页面model的类型
                // window.location.href ="/user/center/adminConcern";

                // 访问用户中心跳转
                // window.location.href ="/user/center";
            } else {
                hideDiv('pop-div');
                alertify.alert(data.msg);
                loginManager.reloadcode();
            }
        }

    });
}
// /*
// * 在线登录方式
// * */
// function OnlineloginSUB() {
//     // LoginManager.login();
//     var userName = document.getElementById("username").value;
//     var password = document.getElementById("password").value;
//     var inputIm = document.getElementById("checkCode").value;
//     //获取当前页面url
//    var urlParm=GetQueryString("url");
//
//     if (userName == null || userName == "") {
//         alertify.alert("请输入账号");
//         return;
//     }
//     if (password == null || password == "") {
//         alertify.alert("请输入密码");
//         return;
//     }
//     if (inputIm == null || inputIm == "") {
//         alertify.alert("请输入验证码");
//         return;
//     }
//     if (imgRN == 0) {
//         alertify.alert("验证码错误");
//         return;
//     }
//     $.ajax({
//         url:"/OnlineLogin",
//         type: 'post',
//         data: {
//             userName: userName,
//             password: password,
//             code:inputIm,
//             //url:urlParm,
//         },
//         success: function (data) {
//             var code = data.code;
//             if (code == 1) {
//
//                 // 哪退出 返回那
//                 // if(reUrl.indexOf("searchresult")!=-1){
//                 //     window.history.back();
//                 //     return;
//                 // }
//                 // if(reUrl!=""){
//                 //     window.location.href = ""+reUrl+"";
//                 // }else{
//                 //     window.location.href = "index";
//                 // }
//
//                 // 直接返回页面model的类型
//                 // window.location.href ="/user/center/adminConcern";
//
//                 // 访问用户中心跳转
//                 window.location.href ="/user/center";
//             } else {
//                 alertify.alert(code);
//             }
//         }
//
//     });
// }
