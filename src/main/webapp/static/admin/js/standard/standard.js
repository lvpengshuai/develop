var StandardManager = (function () {
    function init() {
        $('#periodical_table').bootstrapTable({
            url: appPath + '/admin/standards',
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
            toolbar: '#periodical_table_toolbars',
            iconsPrefix: 'icon',
            icons: {
                refresh: 'md-refresh',
                toggle: 'md-receipt',
                columns: 'md-view-list-alt'
            },
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit, //页面大小
                    offset: params.offset, //页码
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
                field: 'chName',
                title: '中文名称',
                formatter: function (value, row) {
                    return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.chName + '</a>';
                },
                events: 'openStandard'
            }, {
                field: 'standardId',
                title: '标准编号'
            },
                {
                    field: 'pubDate',
                    title: '发布时间',
                    sortable: true,
                    formatter: function (row) {
                        return new Date(row).pattern("yyyy-MM-dd");
                    }
                },
                {
                    field: 'gmtCreate',
                    title: '创建时间',
                    sortable: true,
                    formatter: function (row) {
                        return new Date(row).pattern("yyyy-MM-dd");
                    }
                },
                {
                    field: 'state',
                    title: '状态',
                    formatter: function (value, row) {
                        var state = row.state;
                        var s;
                        if (state == '0') {
                            s = "<span class='label label-success'>已发布</span>";
                        } else if (state == '1') {
                            s = "<span class='label label-warning'>未发布</span>";
                        }
                        return s;
                    }

                },
                {
                    title: '操作',
                    formatter: function (value, row, index) {
                        var state = row.state;
                        var d;
                        if (state == "1") {
                            d = '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                                '<span class="glyphicon glyphicon-edit"></span>' +
                                '</a>';
                            // d = '<span class="glyphicon glyphicon-edit"></span>';
                        } else {
                            d = '';
                        }
                        return d;
                    },
                    events: 'operateEvents'
                }
            ],
        });
    }

    /* 修改table语言为中文 */
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
    /* 删除按钮状态当最少有一个复选框被选择后删除按钮才可用 */
    $('#periodical_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#btn-remove').prop('disabled', !$('#periodical_table').bootstrapTable('getSelections').length);

        $('#btn-publish').prop('disabled', !$('#periodical_table').bootstrapTable('getSelections').length);

        $('#btn-un-publish').prop('disabled', !$('#periodical_table').bootstrapTable('getSelections').length);
    });

    /* 导入资源开始 */
    $("#btn-import").click(function () {
        $('#import-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#wait-modal').modal('hide');
        $("#uploadScroll").hide();
        $('#import-modal-btn-true').prop('disabled', true);
        /*$('#file_input').val("");
         $('#tmp-file-input').val("");*/
        $("#up-load")[0].reset();
        $("#file-up-load-value").text("");
        $("#import-modal-btn-false").attr("disabled", false);
    });
    $('#file_input').change(function () {
        if (!$('#file_input').val()) {
            $('#import-modal-btn-true').prop('disabled', true);
            $("#file-up-load-value").text("上传文件不能为空");
        } else {
            $('#import-modal-btn-true').prop('disabled', false);
            $("#file-up-load-value").text("");
        }
    });
    function checkForm() {
        // alert("验证成功");
    }

    function showResponse(status) {
        $('#import-modal').modal('hide');
        if (status.code == '-2') {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("上传失败，请检查文件格式");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#wait-modal').modal('hide');
            return false;
        } else if (status.code == -3) {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("上传失败，请检查文件内容");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#wait-modal').modal('hide');
            return false;
        } else if (status.code == -4) {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("上传失败，文件不能为空");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#wait-modal').modal('hide');
            return false;
        } else if (status.code == 10000) {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("导入" + status.count + "条数据");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#wait-modal').modal('hide');
            $('#periodical_table').bootstrapTable('refresh');
            return false;
        }
    }

    $(function () {
        var url = appPath + "/admin/standard/import";
        var options = {
            beforeSubmit: checkForm,
            success: showResponse,
            dataType: "json",
            url: url,
            type: "post",
            clearForm: true,
            resetForm: true,
            timeout: 600000
        }

        $("#up-load").submit(function () {
            $("#up-load").ajaxSubmit(options);
            return false;
        });
    });
    $('#import-modal-btn-true').click(function () {

        // 检测上传文件是否为空
        if ($("#file_input").val() == null || $("#file_input").val() == '') {
            $("#file-up-load-value").text("上传文件不能为空");
            return false;
        }

        $("#up-load").submit();
        $('#import-modal-btn-true').attr('disabled', true);
        $("#import-modal-btn-false").attr('disabled', true);
        $("#close-modal").text("");
        var i;

        function reMainTime() {
            $.ajax({
                url: appPath + "/admin/progress",
                type: "post",
                dataType: "json",
                success: function (result) {
                    $("#uploadScroll").show();
                    $("#scroll").css("width", result.status);
                    $("#baifenbi").text(result.status);
                    if (result.status == "100%") {
                        clearTimeout(i);
                        // todo
                        // 执行解析程序 弹出加载图片
                        $('#wait-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                        // 弹出动态图片
                    } else {
                        i = setTimeout(reMainTime(), 1000);
                    }
                }
            });
        }

        reMainTime();
    });

    /* 导入资源结束 */

    /* 发布资源开始 */
    $("#btn-publish").click(function () {
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#publish-modal-btn-true").click(function () {
        var state = "0";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/standard/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
            },
            success: function () {
                $('#periodical_table').bootstrapTable('refresh');
                $('#publish-modal').modal('hide');
                cssSytle();
            }
        });
    });
    /* 发布资源结束 */

    /* 撤销发布资源开始 */
    $("#btn-un-publish").click(function () {
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#un-publish-modal-btn-true").click(function () {
        var state = "1";
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/standard/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
            },
            success: function () {
                $('#periodical_table').bootstrapTable('refresh');
                $('#un-publish-modal').modal('hide');
                cssSytle();
            }
        });
    });
    /* 撤销发布资源结束 */

    /* 删除资源开始 */
    $("#btn-remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });

    $("#remove-modal-btn-true").click(function () {
        $.ajax({
            dataType: "json",
            type: "delete",
            url: appPath + "/admin/standard/" + getIdSelections(),
            success: function (status) {
                if (status.code == 1) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').html("<div style='color: green;font-weight: 600'>共选中" + status.total + "条数据   " + "成功删除" + status.count + "条数据</div>[注意：发布的资源不能被删除]");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    $('#periodical_table').bootstrapTable('refresh');
                    $('#periodical_table').bootstrapTable('refresh');
                    $('#remove-modal').modal('hide');
                } else if (status.code == 20000) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text("服务器异常，请稍后再试");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    $('#periodical_table').bootstrapTable('refresh');
                    $('#periodical_table').bootstrapTable('refresh');
                    $('#remove-modal').modal('hide');
                }
                cssSytle();
            }
        });
    });

    /* 删除资源结束 */

    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#btn-publish').prop('disabled', true);
        $('#btn-remove').prop('disabled', true);
        $('#btn-un-publish').prop('disabled', true);
    }

    window.openStandard = {
        'click .open': function (e, value, row) {
            var id = row.id;

            window.open(appPath + '/standard/' + id + "?isback=0");
        },
    };

    window.operateEvents = {
        /*修改期刊*/
        'click .edit': function (e, value, row) {
            var id = row.id;

            window.location.href = appPath + '/admin/standard/' + id;
        },
    };
    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#periodical_table').bootstrapTable('getSelections'), function (row) {
            // alert(row.id);
            return row.id;
        });

    }


    /* 页面下拉菜单（全部，未发布，发布）点击事件 */
    $(".state li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '全部') {
            state = '';
        } else if ($this_val == '已发布') {
            state = '0';
        } else if ($this_val == '未发布') {
            state = '1';
        }
        $('#state').val(state);
        $('#periodical_table').bootstrapTable("refresh");
        cssSytle();
        // refresh();
    });

    /* 格式化日期 */
    Date.prototype.pattern = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        var week = {
            "0": "/u65e5",
            "1": "/u4e00",
            "2": "/u4e8c",
            "3": "/u4e09",
            "4": "/u56db",
            "5": "/u4e94",
            "6": "/u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }


    return {
        init: init,

    }
})();

StandardManager.init();