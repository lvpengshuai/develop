/*
 * @author LiangYong
 * @create 2017/11/21-15:26
 */
var UserManager = (function () {

    var option = {
        url: appPath + '/admin/get/topicEntry',
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
            width: '200px'
        }, {
            field: 'zidTitle',
            title: '目录',
            width: '250px'
        }, {
            field: 'title',
            title: '标题',
            width: '250px'

        }, {
            field: 'exdata',
            title: '内容',
            formatter: function (object) {
                var thisOnject = JSON.parse(object);
                var str = "";
                var i = 1;
                $.each(thisOnject, function (name, value) {
                    if (name == "编号" || name == "负责人" || name == "职称" || name == "机构" || name == "资助金额" || name == "单位" || name == "成果形式" || name == "成果名称" || name == "评级" || name == "立项时间" || name == "立项资助" || name == "备注") {
                        var newValue = value == "" ? "无" : value;
                        str += "<div class='col-md-5'>" + name + "：" + newValue + "</div>";
                        if (i % 2 == 0) {
                            str += "<br>";
                        }
                        i++;
                    }
                })
                return str;
            }
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
        var bookCodes = getBookCodeSelections();
        $.ajax({
            url: appPath + '/admin/del/topicEntry/' + ids + '/' + bookCodes,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                if (result) {
                    $('#result-modal-body').text("成功删除");
                } else {
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


    /*影响操作*/
    window.operateEvents = {
        /*修改管理员*/
        'click .edit': function (e, value, row) {
            var id = row.id;
            var bookcode = row.bookcode
            window.location.href = appPath + '/admin/set/topicEntry/' + id + "/" + bookcode;
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

    /*状态下拉框*/
    $("#userstate li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '立项课题') {
            state = "立项";
        } else if ($this_val == '结项课题') {
            state = "结项";
        }
        $('#state').val(state);
        $('#user_table').bootstrapTable("refresh");
    });
    return {
        init: init,
    }
})();

UserManager.init();
