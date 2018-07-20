var RoleManager = (function () {
    var option = {
        url: appPath + '/admin/hot/words',
        dataType: 'json',
        cache: true,
        //search: true,
        showRefresh: true,
        striped: true,
        checkboxHeader: true,
        pagination: true,
        sidePagination: 'server',
        //pageSize: 20,
        //pageList: [20, 50, 100, 200],
        toolbar: '#role_table_toolbars',
        iconsPrefix: 'glyphicon',
        icons: {
            refresh: 'md-refresh',
            columns: 'md-view-list-alt',
        },
        queryParams: function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                //pageSize: params.limit, //页面大小
                //currPage: params.offset, //页码
                //search: params.search,
                // sort: params.sort,
                // order: params.order,
                //state: $('#state').val(),
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
            field: 'word',
            title: '热搜词'
        },{
            field: 'gmtCreate',
            title: '创建时间',
            sortable: true,
            formatter: function (value) {
                var da = new Date(value);
                var year = da.getFullYear();
                var month = da.getMonth() + 1;
                var date = da.getDate();
                var hour = da.getHours();
                var min = da.getMinutes();
                var sec = da.getSeconds();
                return year + "-" + month + "-" + date/* + " " + hour + ":" + min + ":" + sec*/;
            }
        }, {
            field: 'gmtModified',
            title: '修改时间',
            sortable: true,
            formatter: function (value) {
                var da = new Date(value);
                var year = da.getFullYear();
                var month = da.getMonth() + 1;
                var date = da.getDate();
                var hour = da.getHours();
                var min = da.getMinutes();
                var sec = da.getSeconds();
                return year + "-" + month + "-" + date/* + " " + hour + ":" + min + ":" + sec*/;
            }
        },  {
            field: 'status',
            title: '状态',
            formatter: function (value, row) {
                var state = row.status;
                var s;
                if (state == '0') {
                    s = "<span class='label label-success'>启用</span>";
                } else if (state == '1') {
                    s = "<span class='label label-warning'>禁用</span>";
                }
                return s;
            }
        }, {
            title: '操作',
            formatter: function (value, row) {
                var state = row.status;
                var id = row.id;
                var d;
                var remove;
                var pwd;
                if (state == "1") {

                    d = '<a class="edit" href="javascript:void(0)" id="btn-update" style="color: #757575;" title="修改">' +
                        '<span class="glyphicon glyphicon-edit"></span>' +
                        '</a>&nbsp;&nbsp;';
                    remove = '<a class="remove" href="javascript:void(0)" style="color: #757575;" title="删除">' +
                        '<span class="glyphicon glyphicon-remove"></span>' +
                        '</a>';
                } else {
                    d = '';
                    remove = '';
                }
                return d + remove;
            },
            events: 'operateEvents'
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
    /*添加角色*/
    $('#btn-add').click(function () {
        window.location.href = appPath + '/admin/hot/word/add';
    });
    /* 启用开始 */
    $("#btn-publish").click(function () {
        $(".modal-title").text("启用");
        $(".modal-body").text("您确定要启用吗");
        $("#publish-modal-btn-true").html("启用");
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });

    $("#publish-modal-btn-true").click(function () {
        var state = "0";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/role/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
            },
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
        $(".modal-title").text("禁用");
        $(".modal-body").text("您确定要禁用吗?");
        for (var i = 0; i < useds.length; i++) {
            if (useds[i] == 0) {
                $(".modal-body").html("<p style='color: green'>选中的角色正在被使用，您确定要禁用吗?</p><p style='font-size: 12px;color: #db2d20'><b>(谨慎禁用活动中的角色)</b></p>");
                break;
            }
        }
        $("#un-publish-modal-btn-true").html("禁用");
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#un-publish-modal-btn-true").click(function () {
        var state = "1";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/role/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
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
    $('#remove-modal-btn-true').click(function () {
        $("#remove-modal").modal("hide");
        var id = $("#id").val();
        $.ajax({
            url: appPath + '/admin/role/' + id,
            dataType: 'json',
            type: 'delete',
            success: function (result) {
                if (result.code <= 0) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text("操作有误，请稍后再试");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    return false;
                }
                cssSytle();
                $('#role_table').bootstrapTable('refresh');
            }
        })
    });
    /* 删除角色结束 */

    /*影响操作*/
    window.operateEvents = {
        /*修改角色*/
        'click .edit': function (e, value, row) {
            var id = row.id;

            window.location.href = appPath + '/admin/role/' + id;
        },
        'click .remove': function (e, value, row) {
            var id = row.id;
            var status = row.status;
            $(".modal-title").text("删除");
            $(".text-center").text("此操作将不可逆,请谨慎操作!您确定要删除吗");
            $("#remove-modal-btn-true").attr("disabled", false);
            if (0 == status) {
                $(".text-center").text("角色正在使用不能删除");
                $("#remove-modal-btn-true").attr("disabled", true);
            }
            $("#id").val(id);
            $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        },
    };

    /*状态下拉框*/
    $(".state li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '全部') {
            state = '';
        } else if ($this_val == '启用') {
            state = '0';
        } else if ($this_val == '禁用') {
            state = '1';
        }
        $('#state').val(state);
        $('#role_table').bootstrapTable("refresh");
        cssSytle();
    });


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