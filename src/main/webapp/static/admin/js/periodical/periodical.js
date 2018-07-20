var PeriodicalManager = (function () {

    function init() {
        $('#periodical_table').bootstrapTable('destroy');
        $('#periodical_table').bootstrapTable({
            url: appPath + '/admin/journals',
            dataType: 'json',
            cache: false,
            search: true,
            showRefresh: true,
            escape: true,
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
                columns: 'md-view-list-alt',
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
                title: '序号',
                class: 'hidden'

            }, {
                field: 'chName',
                title: '期刊名',
                formatter: function (value, row) {
                    if (row.chName == null || row.chName == "") {
                        return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.enName + '</a>';
                    } else {
                        return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.chName + '</a>';
                    }
                },
                events: 'openPeriodical'
            }, {
                field: 'lanmu',
                title: '栏目'
            },
                {
                    field: 'gmtCreate',
                    title: '创建时间 ',
                    sortable: true,
                    formatter: function (row) {
                        if (null != row) {
                            return new Date(row).pattern("yyyy-MM-dd");
                        }
                    }
                },

                {
                    field: 'pubDate',
                    title: '发布时间',
                    sortable: true,
                    formatter: function (row) {
                        if (null != row) {
                            return new Date(row).pattern("yyyy-MM-dd");
                        }
                    }

                },
                {
                    field: 'phase',
                    title: '期'
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
                    field: '',
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
    /* 按钮状态当最少有一个复选框被选择后按钮才可用 */
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
        var url = appPath + "/admin/journal/import";
        var options = {
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
        var filename = $('#tmp-file-input').val();
        var type = filename.substring(filename.lastIndexOf(".") + 1).toLocaleLowerCase();
        // 检测上传文件是否为空
        if ($("#file_input").val() == null || $("#file_input").val() == '') {
            $("#file-up-load-value").text("上传文件不能为空");
            return false;
        }
        if (type != 'zip') {
            $("#file-up-load-value").text("只能上传zip文件");
            return false;
        }

        $("#up-load").submit();
        //uploadFile();
        $('#import-modal-btn-true').attr('disabled', true);
        $("#import-modal-btn-false").attr('disabled', true);
        $("#close-modal").text("");
        reMainTime();
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
                        // 执行解析程序 弹出加载图片
                        $('#wait-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                        // 弹出动态图片
                    } else {
                        i = setTimeout(reMainTime(), 1000);
                    }
                }
            });
        }
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
            url: appPath + "/admin/journal/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
            },
            success: function (status) {
                $('#publish-modal').modal('hide');
                if (status.count != 0) {
                    var value = result.total - result.count;
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text(status.count + "条资源不存在!其余发布成功");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    $('#wait-modal').modal('hide');
                    $('#periodical_table').bootstrapTable('refresh');
                    cssSytle();
                    return false;
                } else if (status.code == 20000) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text("服务器异常，请稍后再试");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    $('#wait-modal').modal('hide');
                    $('#periodical_table').bootstrapTable('refresh');
                    cssSytle();
                    return false;
                }
                $('#periodical_table').bootstrapTable('refresh');
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
            url: appPath + "/admin/journal/" + getIdSelections(),
            data: {
                'state': state,
                _method: 'put'
            },
            success: function (status) {
                $('#un-publish-modal').modal('hide');
                if (status.count != 0) {
                    //var value =  result.total - result.count;
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text(status.count + "条资源不存在!其余撤销发布成功");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    //$('#wait-modal').modal('hide');
                    $('#periodical_table').bootstrapTable('refresh');
                    cssSytle();
                    return false;
                } else if (status.code == 20000) {
                    $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    $('#result-modal-title').html("提示");
                    $('#result-modal-body').text("服务器异常，请稍后再试");
                    $('#result-modal-btn-false').html("确定");
                    $('#result-modal-btn-true').prop('style', 'display:none');
                    //$('#wait-modal').modal('hide');
                    $('#periodical_table').bootstrapTable('refresh');
                    cssSytle();
                    return false;
                }
                $('#periodical_table').bootstrapTable('refresh');
                cssSytle();
            }
        });
    });
    /* 撤销发布资源结束 */

    /* 删除资源开始 */
    $("#btn-remove").click(function () {
        // alert(getIdSelections());
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });

    $("#remove-modal-btn-true").click(function () {
        $.ajax({
            dataType: "json",
            type: "delete",
            url: appPath + "/admin/journal/" + getIdSelections(),
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

    window.operateEvents = {
        /*修改期刊*/
        'click .edit': function (e, value, row) {
            var id = row.id;

            window.location.href = appPath + '/admin/journal/' + id;
        },
    };
    window.openPeriodical = {
        'click .open': function (e, value, row) {
            var id = row.id;

            window.open(appPath + '/journal/' + id + "?isback=0");
        },
    };
    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#periodical_table').bootstrapTable('getSelections'), function (row) {
            // alert(row.id);
            return row.id;
        });

    }

    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#btn-publish').prop('disabled', true);
        $('#btn-remove').prop('disabled', true);
        $('#btn-un-publish').prop('disabled', true);
    }


    $(".state li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = ''
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

    function getFileSize(obj) {
        photoExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if (photoExt != '.zip') {
            $("#file-up-load-value").text("文件格式不正確支持zip格式");
            return false;
        }
        $("#file-up-load-value").text("");
        var fileSize = 0;
        var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
        if (isIE && !obj.files) {
            var filePath = obj.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            var file = fileSystem.GetFile(filePath);
            fileSize = file.Size;
        } else {
            fileSize = obj.files[0].size;
        }
        /*fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
         if(fileSize>=10){
         alert("照片最大尺寸为10KB，请重新上传!");
         return false;
         }*/
    }


    return {
        init: init,
        getIdSelections: getIdSelections,
        getFileSize: getFileSize
    }
})();

PeriodicalManager.init();