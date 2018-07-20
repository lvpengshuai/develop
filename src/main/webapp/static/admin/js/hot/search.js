var RoleManager = (function () {
    var option = {
        url: appPath + '/admin/hot/searchs',
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
        toolbar: '#role_table_toolbars',
        iconsPrefix: 'glyphicon',
        icons: {
            refresh: 'md-refresh',
            columns: 'md-view-list-alt',
        },
        queryParams: function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, //页面大小
                currPage: params.offset, //页码
                search: params.search,
                 sort: params.sort,
                 order: params.order,
                state: $('#state').val(),
            };
            return temp;
        },
        onLoadSuccess: function () {
            cssSytle();
        },
        columns: [{
            checkbox: true,
            align: 'center'
        }, {
            field: 'id',
            class: 'hidden'
        }, {
            field: 'title',
            title: '书名'
        }, {
            field: 'hottype',
            title: '状态',
            formatter: function (value, row) {
                var state = row.hottype;
                var s;
                if (state == '0') {
                    s = "<span class='label label-success'>主书</span>";
                } else if (state == '1') {
                    s = "<span class='label label-primary'>次书</span>";
                } else if (state == '2') {
                    s = "<span class='label label-danger'>无</span>";
                }
                return s;
            }
        }
        ]
    };

    function init() {
        $('#role_table').bootstrapTable(option);
    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#role_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#role_table').bootstrapTable('getSelections').length);
        $('#btn-un-publish').prop('disabled', !$('#role_table').bootstrapTable('getSelections').length);
        $('#btn-publish').prop('disabled', !$('#role_table').bootstrapTable('getSelections').length);
    });

    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
        $('#btn-publish').prop('disabled', true);
        $('#btn-un-publish').prop('disabled', true);

    }

    /* 启用开始 */
    $("#btn-publish").click(function () {
        $(".modal-title").text("主书");
        $(".modal-body").text("您确定要设置主书吗？");
        $("#publish-modal-btn-true").html("设置");
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });

    $("#publish-modal-btn-true").click(function () {
        var state = "0";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/hot/update/" + getIdSelections()+"/"+state,
            success: function () {
                $('#role_table').bootstrapTable('refresh');
                $('#publish-modal').modal('hide');
                cssSytle();
            }
        });
    });
    /* 启用角色结束 */

    /* 禁用角色开始 */
    $("#btn-un-publish").click(function () {
        var useds = getUsedSelections();
        $(".modal-title").text("次书");
        $(".modal-body").text("您确定要设置次书吗？");
        for (var i = 0; i < useds.length; i++) {
            if (useds[i] == 0) {
                $(".modal-body").html("<p style='color: green'>选中的角色正在被使用，您确定要禁用吗?</p><p style='font-size: 12px;color: #db2d20'><b>(谨慎禁用活动中的角色)</b></p>");
                break;
            }
        }
        $("#un-publish-modal-btn-true").html("设置");
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#un-publish-modal-btn-true").click(function () {
        var state = "1";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/hot/update/" + getIdSelections()+"/"+state,
            data: {
                'state': state
            },
            success: function () {
                $('#role_table').bootstrapTable('refresh');
                $('#un-publish-modal').modal('hide');
                cssSytle();
            }
        });
    });
    /* 禁用角色结束 */

    /* 删除角色开始 */
    $("#remove").click(function () {
        var useds = getUsedSelections();
        $(".modal-title").text("取消显示");
        $(".modal-body").text("您确定要设置为不显示吗？");
        for (var i = 0; i < useds.length; i++) {
            if (useds[i] == 0) {
                $(".modal-body").html("<p style='color: green'>选中的角色正在被使用，您确定要禁用吗?</p><p style='font-size: 12px;color: #db2d20'><b>(谨慎禁用活动中的角色)</b></p>");
                break;
            }
        }
        $("#un-publish-modal-btn-true").html("设置");
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#un-publish-modal-btn-true").click(function () {
        var state = "2";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/hot/update/" + getIdSelections()+"/"+state,
            data: {
                'state': state
            },
            success: function () {
                $('#role_table').bootstrapTable('refresh');
                $('#un-publish-modal').modal('hide');
                cssSytle();
            }
        });
    });
    /* 删除角色结束 */


    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#role_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

    /* 获取使用状态 */
    function getUsedSelections() {
        return $.map($('#role_table').bootstrapTable('getSelections'), function (row) {
            return row.used;
        });
    }
    return {
        init: init,
    }

})
();
RoleManager.init();
