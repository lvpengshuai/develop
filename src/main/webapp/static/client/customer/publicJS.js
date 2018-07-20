
// 导航栏  选中事件
function set_class(li,cur,text){
    var name=li;
    text=jQuery.trim(text);
    jQuery(name).each(function(){
        var li_text=jQuery.trim(jQuery(this).text()+"");
        //alert(li_text);
        if(li_text==text){
            jQuery(this).addClass(cur);
        }
    });
}

// 获取url参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

