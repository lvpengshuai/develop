/*
 * @author LiYu
 * @create 2017/11/27-17:26
 */

var UserManager = (function () {

    var option = {
        url: appPath + '/admin/getAllMemorabiliayEntry',
        dataType: 'json',
        cache: true,
        search: true,
        showRefresh: true,
        striped: true,
        checkboxHeader: true,
        pagination: true,
        sidePagination: 'server',
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        toolbar: '#user_table_toolbars',
        iconsPrefix: 'glyphicon',
        icons: {
            refresh: 'md-refresh',
            columns: 'md-view-list-alt',
        },
        queryParams: function (params) {
            var nameEntry = $("#nameEntry").val();
            var yearEntry = $("#yearEntry").val();
            var state = $('#state').val();
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, //页面大小
                currPage: params.offset, //页码
                search: params.search,
                sort: params.sort,
                order: params.order,
                nameEntry: nameEntry == "" ? undefined : nameEntry,
                yearEntry: yearEntry == "" ? undefined : yearEntry,
                state: state == "" ? undefined : state,
            };
            return temp;
        },
        columns: [{
            checkbox: true,
            align: 'center'
        }, {
            field: 'id',
            class: 'hidden'
        }, {
            field: 'bookcode',
            class: 'hidden'
        }, {
            field: 'bookname',
            title: '年鉴',
            width:'150px'
        },  {
            field: 'title',
            title: '标题',
            width: '150px',
        },{
            field: 'exdate',
            title: '时间',
            width: '110px',
            formatter: function (exdate) {
               var newExdate = new Date(exdate).Format("yyyy-MM-dd")
                return   newExdate;
            },
        }, {
            field: 'zidTitle',
            title: '来自',
        },{
            field: 'htmlContent',
            title: '事记描述',
        }, {
            title: '操作',
            formatter: function () {
                var edit;
                edit = '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                    '<span class="glyphicon glyphicon-edit"></span>' +
                    '</a>&nbsp;&nbsp;';
                return edit;
            },
            events: 'operateEvents'
        }]

    };

    function init() {
        $('#user_table').bootstrapTable(option);

    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#user_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#user_table').bootstrapTable('getSelections').length);
    });

    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
    }

    /*添加角色*/
    $('#btn-add').click(function () {
        window.location.href = appPath + '/admin/admin-user/add';
    });

    /* 删除角色开始 */
    $("#remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#remove-modal-btn-true').click(function () {
        $('#remove-modal').modal('hide');
        var ids = getIdSelections();
        var bookCodes=getBookCodeSelections();
        $.ajax({
            url: appPath + '/admin/deleteMemorabiliayEntry/' + ids+'/'+ bookCodes,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                if (result) {
                    $('#result-modal-body').text("成功删除");
                } else{
                    $('#result-modal-body').text("删除失败");
                }
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#user_table').bootstrapTable('refresh');
            }
        })
    });
    /* 删除角色结束 */

    /*日期格式化*/
    Date.prototype.Format = function (fmt) {
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

    /*影响操作*/
    window.operateEvents = {
        /*修改管理员*/
        'click .edit': function (e, value, row) {
            var id = row.id;
            var bookcode = row.bookcode
            window.location.href = appPath + '/admin/setMemorabiliayEntry/' + id + "/" + bookcode;
        },
    };

    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#user_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    function getBookCodeSelections() {
        return $.map($('#user_table').bootstrapTable('getSelections'), function (row) {
            return row.bookcode;
        });
    }


    return {
        init: init,
    }
})();

UserManager.init();

