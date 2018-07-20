var LogManager = (function () {

    var targetList = ['系统管理', '知识体系管理', '知识库管理', '电子书库管理', '期刊库管理', '标准库管理', '用户管理', '会员管理', '机构管理', '日志管理', '资源访问'];
    var operList = ['新增', '删除', '修改', '查看', '登录', '登出', '发布', '撤销发布', '上传', '下载', '启用', '禁用', '审核', '同步'];

    var option = {
        url: appPath + '/admin/error-records',
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
        toolbar: '#log_table_toolbars',
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
                targetType: $('#targetType').val(),
                operType: $('#operType').val(),
                start: $('#start').val(),
                end: $('#end').val(),
            };
            return temp;
        },
        columns: [{
            checkbox: true,
            align: 'center'
        }, {
            field: 'targetType',
            title: '操作对象',
            formatter: function (value, row) {
                var targetType = row.targetType;
                // var t = "<span class='label label-success'>" + targetList[targetType - 1] + "</span>";
                return targetList[targetType - 1];
            }
        }, {
            field: 'operType',
            title: '操作类型',
            formatter: function (value, row) {
                var operType = row.operType;
                // var o = "<span class='label label-success'>" + operList[operType - 1] + "</span>";
                return operList[operType - 1];
            }
        }, {
            field: 'operUsername',
            title: '操作者'
        }, {
            field: 'description',
            title: '操作描述'
        }, {
            field: 'ip',
            title: '操作者IP',
        }, {
            field: 'gmtCreate',
            title: '操作时间',
            sortable: true,
            formatter: function (value) {
                var da = new Date(value);
                var year = da.getFullYear();
                var month = da.getMonth() + 1;
                var date = da.getDate();
                var hour = da.getHours();
                var min = da.getMinutes();
                var sec = da.getSeconds();
                return year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;
            }
        }]

    };

    function init() {
        $('#log_table').bootstrapTable(option);
    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#log_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#log_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
    }

    /* 删除资源 */
    $("#remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#remove-modal-btn-true').click(function () {
            $('#remove-modal').modal('hide');
            var ids = getIdSelections();
            $.ajax({
                url: appPath + '/admin/error-record/' + ids,
                dataType: 'json',
                type: 'delete',
                success: function (result) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    if (result.code == '0') {
                        $('#result-modal-body').text("共选择" + result.total + "条记录，" +
                            "成功删除" + result.total + "条记录");
                    }
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    cssSytle();
                    $('#log_table').bootstrapTable('refresh');
                }
            })
        });
    });

    /*条件过滤*/
    $(function () {
        var tar_list_a = '';
        for (var i = 0; i < targetList.length; i++) {
            tar_list_a += '<li><a href="javascript:void(0)">' + targetList[i] + '</a></li>';
        }
        $('#t').append(tar_list_a);

        var oper_list_a = '';
        for (var j = 0; j < operList.length; j++) {
            oper_list_a += '<li><a href="javascript:void(0)">' + operList[j] + '</a></li>';
        }
        $('#o').append(oper_list_a);

        /*操作对象*/
        $("#t li a").click(function () {
            var $this_val = $(this).html();
            $("#target").text($this_val);
            var targetType = targetList.indexOf($this_val) + 1;
            if (targetType == '0') {
                targetType = '';
            }
            $('#targetType').val(targetType);
            $('#log_table').bootstrapTable("refresh");
        });
        /*操作类型*/
        $("#o li a").click(function () {
            var $this_val = $(this).html();
            $("#oper").text($this_val);
            var operType = operList.indexOf($this_val) + 1;
            if (operType == '0') {
                operType = '';
            }
            $('#operType').val(operType);
            $('#log_table').bootstrapTable("refresh");
        });
    });


    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#log_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }


    return {
        init: init,
    }
})();

LogManager.init();