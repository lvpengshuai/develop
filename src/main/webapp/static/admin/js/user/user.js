var UserManager = (function () {

    var option={
        url:appPath+'/admin/admin-users',
        dataType:'json',
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
        columns: [{
            checkbox: true,
            align: 'center'
        }, {
            field: 'id',
            class: 'hidden'
        }, {
            field: 'username',
            title: '账户'
        }, {
            field: 'realname',
            title: '真实姓名',
        }, {
            field: 'email',
            title: '邮箱',
        }, {
            field: 'telephone',
            title: '联系电话',
        }, {
            field: 'address',
            title: '地址',
        }, {
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
                return year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;
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
                return year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;
            }
        }, {
            field: 'status',
            title: '状态',
            formatter:function(value,row){
                var status = row.status;
                var s;
                if(status == '0'){
                    s = "<span class='label label-success'>启用</span>";
                } else if (status == '1') {
                    s = "<span class='label label-warning'>禁用</span>";
                }
                return s;
            }
        }, {
            title:'操作',
            formatter:function(value,row){
                var status = row.status;
                var edit;
                var resetpwd;
                if(status == "1"){
                    edit = '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                        '<span class="glyphicon glyphicon-edit"></span>' +
                        '</a>&nbsp;&nbsp;';
                    resetpwd = '<a class="resetpwd" href="javascript:void(0)" style="color: #757575;" title="重置密码">' +
                        '<span class="glyphicon glyphicon-cog"></span>' +
                        '</a>';
                }else{
                    edit = '';
                    resetpwd = '';
                }
                return  edit+resetpwd;
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
        $('#btn_enable_user').prop('disabled', !$('#user_table').bootstrapTable('getSelections').length);
        $('#btn_disable_user').prop('disabled', !$('#user_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
        $('#btn_enable_user').prop('disabled', true);
        $('#btn_disable_user').prop('disabled', true);
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
        $.ajax({
            url: appPath + '/admin/admin-user/' + ids,
            dataType: 'json',
            type: 'post',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                if ((result.total - result.deltotal) == 0) {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。");
                } else if ((result.total - result.deltotal) > 0 && result.admin == 1) {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。其中含有系统管理员，无法删除");
                }else if((result.total - result.deltotal) > 0 && result.used == 1){
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。其中"+(result.total - result.deltotal)+"个用户已启用，无法删除");
                }
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#user_table').bootstrapTable('refresh');
            }
        })
    });
    /* 删除角色结束 */

    /*更改账户状态开始*/
    /*启用账户*/
    $("#btn_enable_user").click(function () {
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#publish-modal div h4').text("启用");
        $('#publish-modal .modal-body').text("您确定要启用吗？");
        $('#publish-modal-btn-true').text("启用");
    });
    $('#publish-modal-btn-true').click(function () {
        $('#publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '0');
    });
    /*禁用账户*/
    $("#btn_disable_user").click(function () {
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#un-publish-modal div h4').text("禁用");
        $('#un-publish-modal .modal-body').text("您确定要禁用吗？");
        $('#un-publish-modal-btn-true').text("禁用");
    });
    $('#un-publish-modal-btn-true').click(function () {
        $('#un-publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '1');
    });
    function changeState(ids, state) {
        $.ajax({
            url: appPath + '/admin/admin-user/' + ids + '/' + state,
            dataType: 'json',
            type: 'post',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                var status = "";
                if(result.status == '0'){
                    status = "启用";
                }else if(result.status == '1'){
                    status = "禁用";
                }
                if ((result.total - result.chgtotal) == 0) {
                    $('#result-modal-body').text("共选择" + result.total + "个账户，" +
                        status + result.chgtotal + "个账户。");
                } else if ((result.total - result.chgtotal) > 0 && result.admin == 1) {
                    $('#result-modal-body').text("共选择" + result.total + "个账户，" +
                        status + result.chgtotal + "个账户。其中含有系统管理员，无法修改");
                }
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#user_table').bootstrapTable('refresh');
            }
        })
    }
    /*更改账户状态结束*/

    /*影响操作*/
    window.operateEvents = {
        /*修改管理员*/
        'click .edit': function (e, value, row) {
            var id = row.id;
            window.location.href = appPath + '/admin/admin-user/' + id;
        },
        /*重置密码*/
        'click .resetpwd': function (e, value, row) {
            /*配置相同的值*/
            $.extend($.validator.messages, {
                required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;">*必填</span>',
                equalTo: "<span  style='font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;'>两次输入的密码不一致</span>",
                minlength:"50",
            });
            /*验证原始密码*/
            $.validator.addMethod("oldpassword", function (value, element) {
                var oldpasswordReg=/^.*[^\s]+.*$/;
                return this.optional(element) || (oldpasswordReg.test(value));
            }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>原始密码不能为空</span>");
            /*验证密码*/
            $.validator.addMethod("newpassword", function (value, element) {
                var newpasswordReg=/^[a-zA-Z0-9]{6,20}$/;
                return this.optional(element) || (newpasswordReg.test(value));
            }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>密码长度6-20个字符或数字</span>");
            var id = row.id;
            $('#resetpassword_modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#id').val(id);
        },
    };
    /*重置密码开始*/
    $("#resetpwd_form").validate({
        submitHandler: function () {
            var url = appPath + "/admin/admin-user/resetpassword";
            var options = {
                success: showResponse,
                url: url,
                dataType: "json",
                type: "post",
                clearForm: true,
                resetForm: true,
                timeout: 120000
            };
            $('#resetpwd_form').ajaxSubmit(options);
        }
    });
    function showResponse(result) {
        $('#resetpassword_modal').modal('hide');

        if (result.code == '0') {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("密码重置成功！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }else if(result.code == "-1"){
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("您输入的旧密码与原始密码不相同！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }else{
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("密码重置失败！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }
    }

    $("#resetpassword_modal_btn_true").click(function () {
        $("#resetpwd_form").submit();
    });
    /*重置密码结束*/

    /*状态下拉框*/
    $("#userstate li a").click(function () {
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
        $('#user_table').bootstrapTable("refresh");
    });

    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#user_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }


    return {
        init: init,
    }
})();

UserManager.init();