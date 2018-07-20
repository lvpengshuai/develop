var MemberManager = (function () {

    var option = {
        url: appPath + '/admin/members',
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
        toolbar: '#member_table_toolbars',
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
            field: 'username',
            title: '账户',
            formatter: function (value, row) {
                return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.username + '</a>';
            },
            events: 'openStandard'
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
            field: 'roleId',
            title: '会员属性',
            formatter: function (value, row) {
                var roleId = row.roleId;
                var s;
                if (roleId != '0') {
                    s = "<span class='label label-dark'>授权用户</span>";
                } else if (roleId == '0') {
                    s = "<span class='label label-warning' style='background-color: #D3AA9C'>注册用户</span>";
                }
                return s;
            }
        }, {
            field: 'status',
            title: '状态',
            formatter: function (value, row) {
                var status = row.status;
                var s;
                if (status == '0') {
                    s = "<span class='label label-success'>启用</span>";
                } else if (status == '1') {
                    s = "<span class='label label-warning'>禁用</span>";
                }
                return s;
            }
        },
            {
                title: '操作',
                formatter: function (value, row) {
                    var status = row.status;
                    var edit;
                    var resetpwd;
                    // if (status == "0") {
                        edit = '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                         '<span class="glyphicon glyphicon-edit"></span>' +
                         '</a>&nbsp;&nbsp;';
                        resetpwd = '<a class="resetpwd" href="javascript:void(0)" style="color: #757575;" title="重置密码">' +
                            '<span class="glyphicon glyphicon-cog"></span>' +
                            '</a>';
                    // } else {
                    //     edit = '';
                    //     resetpwd = '';
                    // }
                    return edit+resetpwd ;
                },
                events: 'operateEvents'
            }
        ]

    };

    function init() {
        $('#member_table').bootstrapTable(option);
    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#member_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#member_table').bootstrapTable('getSelections').length);
        $('#btn_enable_user').prop('disabled', !$('#member_table').bootstrapTable('getSelections').length);
        $('#btn_disable_user').prop('disabled', !$('#member_table').bootstrapTable('getSelections').length);
        $('#btn_enable_user_attribute').prop('disabled', !$('#member_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
        $('#btn_enable_user').prop('disabled', true);
        $('#btn_disable_user').prop('disabled', true);
        $('#btn_enable_user_attribute').prop('disabled', true);
    }

    /*添加用户*/
    $('#btn-add').click(function () {
        window.location.href = appPath + '/admin/member/add';
    });

    /* 删除角色开始 */
    $("#remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#remove-modal-btn-true').click(function () {
        $('#remove-modal').modal('hide');
        var ids = getIdSelections();
        $.ajax({
            url: appPath + '/admin/member/' + ids,
            dataType: 'json',
            type: 'delete',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                if ((result.total - result.deltotal) == 0) {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。");
                }
                else if ((result.total - result.deltotal) > 0 && result.used == 1) {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。其中" + (result.total - result.deltotal) + "个用户已启用，无法删除");
                }
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#member_table').bootstrapTable('refresh');
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
            url: appPath + '/admin/member/' + ids + '/' + state,
            dataType: 'json',
            type: 'post',
            success: function (result) {
                cssSytle();
                $('#member_table').bootstrapTable('refresh');
            }
        })
    }

    /*更改账户状态结束*/

    /* 用户资料 */
    window.openStandard = {
        'click .open': function (e, value, row) {
            var id = row.id;
            $("#member-info-show").html("");
            $.ajax({
                dataType: "json",
                type: "post",
                url: appPath + "/admin/member/" + id,
                success: function (resultMap) {
                    // 弹出modal
                    $('#member-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                    var object = $("#member-info-show").append(
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">账号：</label>' +
                        '<input type="text" class="form-control bookName" id="username" name="username" value="' + resultMap.member.username + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">学历：</label>' +
                        '<input type="text" class="form-control" name="education" value="' + resultMap.member.education + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">专业：</label>' +
                        '<input type="text" class="form-control" name="major" value="' + resultMap.member.major + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">邮箱：</label>' +
                        '<input type="text" class="form-control bookName" name="standardId" value="' + resultMap.member.email + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">单位：</label>' +
                        '<input type="text" class="form-control" name="company" value="' + resultMap.member.organization + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<div style="margin-left: 60px">' +
                        '<label style="float: left;height: 36px;line-height: 36px">住址：</label>' +
                        ' <input type="text" class="form-control" name="address" value="' + resultMap.member.address + '" style="width: 80%;background-color: white" readonly/>' +
                        '</div><br>' +
                        '<span>会员状态：</span>');
                    if (resultMap.member.status == 1) {
                        object = object.append("<span class='label label-warning'>禁用</span>");
                    } else if (resultMap.member.status == 0) {
                        object = object.append("<span class='label label-success'>启用</span>");
                    }
                    object = object.append('<span style="margin-left: 40px">会员属性：</span>');
                    if (resultMap.member.roleId != 0) {
                        object = object.append("<span class='label label-dark'>授权用户</span>");
                    } else {
                        object = object.append("<span class='label label-warning' style='background-color: #D3AA9C'>注册用户</span>");
                    }
                    object={};
                    //object.append('</div><br>');
                }
            });
        },
    };

    /*影响操作*/
    window.operateEvents = {
        /*修改管理员*/
        'click .edit': function (e, value, row) {
            var id = row.id;

            window.location.href = appPath + '/admin/member/' + id;
        },
        /*重置密码*/
        'click .resetpwd': function (e, value, row) {
            $("#newpassword-error").attr("float", 'left');
            /*配置相同的值*/
            $.extend($.validator.messages, {
                required: '<span style="font-size: 12px;color:red; "><em>* 必填</em></span>',
                equalTo: "<span  style='font-size: 12px;color:red; '><em>* 两次输入的密码不一致</em></span>",
                minlength: "50",
            });
            /*验证密码*/
            $.validator.addMethod("newpassword", function (value, element) {
                var newpasswordReg = /^[a-zA-Z0-9]{6,20}$/;
                return this.optional(element) || (newpasswordReg.test(value));
            }, "<span  style='font-size: 12px;color:red;'><em>* 由字符或数字组成 (6 ~ 20)</em></span>");
            var id = row.id;
            $("#oldpassword").hide();
            $('#resetpassword_modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#id').val(id);

        },
    };

    /* 修改密码开始 */
    $("#resetpassword_modal_btn_true").click(function () {
        $("#resetpwd_form").submit();
    });
    $("#resetpwd_form").validate({
        submitHandler: function () {
            var url = appPath + "/admin/member/resetpassword";
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
        } else {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text("重置密码失败！");
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
        }
    }

    /* 修改密码结束 */

    /*状态下拉框*/
    $(".status-name li a").click(function () {
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
        $('#member_table').bootstrapTable("refresh");
        cssSytle();
    });

    /* 修改用户权限开始 */
    $("#btn_enable_user_attribute").click(function () {
        $("#role-name").text('');
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/member/role",
            success: function (list) {
                var objectRoleName = $("#role-name");
                objectRoleName.append('<li><a href="javascript:void(0)">注册用户</a></li>');
                for (var i = 0; i < list.length; i++) {
                    objectRoleName.append(' <li><a id="' + list[i].id + '" title="' + list[i].remark + '" href="javascript:void(0)">' + list[i].name + '</a></li>');
                }

                /* 选择用户权限 */
                $("#role-name li a").click(function () {
                    var $this_val = $(this).html();
                    $("#role-name-attribute").text($this_val);
                    var attr = $(this).attr("id");
                    var title = $(this).attr("title");
                    var roleId = '';
                    if ($this_val == '请选择') {
                        roleId = '';
                    } else if ($this_val == '注册用户') {
                        $("#remark").text("个人用户首次注册后,拥有得默认权限.具有拼接,收藏,关注权限.");
                        roleId = '0';
                    } else {
                        roleId = attr;
                        $("#remark").text(title);
                    }
                    $('#roleId').val(roleId);

                });
                /* 选择用户权限 */
            }
        });
        $('#role-attribute-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $("#role-name-attribute").text("请选择");
        $("#remark").text("");
    });
    $("#role-attribute-modal-btn-true").click(function () {
        $('#role-attribute-modal').modal('hide');
        // var ids = getIdSelections();
        var attribute = $("#roleId").val();
        $.ajax({
            dataType: "json",
            type: "post",
            url: appPath + "/admin/member/" + getIdSelections(),
            data: {
                'attribute': attribute,
                _method: 'put'
            },
            success: function (result) {
                if (result.code == '1') {
                    $('#member_table').bootstrapTable("refresh");
                    cssSytle();
                }
            }
        });
    });
    /* 修改用户权限结束 */

    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#member_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }


    return {
        init: init,
    }
})();

MemberManager.init();
