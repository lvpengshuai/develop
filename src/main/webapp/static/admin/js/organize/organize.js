var OrganizeManager = (function () {

    var option = {
        url: appPath + '/admin/organizes',
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
        toolbar: '#organize_table_toolbars',
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
            field: 'name',
            title: '机构名称'
        }, {
            field: 'address',
            title: '机构地址',
        }, {
            field: 'telephone',
            title: '机构电话',
        }, {
            // field: 'ipStart',
            title: '机构IP范围',
            formatter: function (value, row) {
                var ipStart = row.ipStart;
                var ipEnd = row.ipEnd;
                var ipStarts = [];
                var ipEnds = [];
                var line = "";
                var line2 = "";
                if (ipStart != null && ipEnd != null){

                    ipStarts = ipStart.split(";");
                    ipEnds = ipEnd.split(";");
                    for (var i = 0; i < 2; i++) {
                        if (ipStarts[i] != "" && ipEnds[i] != "") {
                            line += ipStarts[i] + '-' + ipEnds[i] + '<br>';
                        }
                    }
                    for (var i = 0; i < ipStarts.length; i++) {
                        if (ipStarts[i] != "" && ipEnds[i] != "") {
                            if (ipStarts[i] == ipEnds[i]) {
                                line2 += ipStarts[i] + '\r\n';
                            } else {
                                line2 += ipStarts[i] + '-' + ipEnds[i] + '\r\n';
                            }
                        }
                    }
                }
                if (ipStarts.length > 2) {
                    line += "...";
                }
                var newline = "<div title='" + line2 + "'>" + line + "</div>";
                return newline;
            }
        }, {
            field: 'expiration',
            title: '有效时间',
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
        }, {
            title: '操作',
            formatter: function (value, row) {
                var online = $("#online").val();
                var status = row.status;
                var edit;
                /*if(status == "1"){*/
                if (online == "ON"){
                    edit =
                        '<a class="branchUser" href="javascript:void(0)" style="color: #757575;" title="绑定">' +
                        '<span class="glyphicon glyphicon-user"></span>' +
                        '</a>&nbsp;&nbsp;&nbsp;&nbsp;' +

                        '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                        '<span class="glyphicon glyphicon-edit"></span>' +
                        '</a>&nbsp;&nbsp;';
                }else {
                    edit =
                        '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                        '<span class="glyphicon glyphicon-edit"></span>' +
                        '</a>&nbsp;&nbsp;';
                }
                /* }else{
                 edit = '';
                 }*/
                return edit;
            },
            events: 'operateEvents'
        }]

    };

    function init() {
        $('#organize_table').bootstrapTable(option);
    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#organize_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#organize_table').bootstrapTable('getSelections').length);
        $('#btn_enable_organize').prop('disabled', !$('#organize_table').bootstrapTable('getSelections').length);
        $('#btn_disable_organize').prop('disabled', !$('#organize_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#remove').prop('disabled', true);
        $('#btn_enable_organize').prop('disabled', true);
        $('#btn_disable_organize').prop('disabled', true);
    }

    /*添加*/
    $('#btn-add').click(function () {
        window.location.href = appPath + '/admin/organize/add';
    });

    /* 删除开始 */
    $("#remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#remove-modal-btn-true').click(function () {
        $('#remove-modal').modal('hide');
        var ids = getIdSelections();
        $.ajax({
            url: appPath + '/admin/organizeDel/' + ids,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                $('#result-modal-body').text("成功删除" + result.total + "条数据。");
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#organize_table').bootstrapTable('refresh');
            }
        })
    });
    /* 删除结束 */

    /*更改账户状态开始*/
    /*启用账户*/
    $("#btn_enable_organize").click(function () {
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
    $("#btn_disable_organize").click(function () {
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
            url: appPath + '/admin/organize/' + ids + '/' + state,
            dataType: 'json',
            type: 'post',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                var status = "";
                if (result.status == '0') {
                    status = "启用";
                } else if (result.status == '1') {
                    status = "禁用";
                }
                $('#result-modal-body').text("成功" + status + result.total + "个账户");
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');
                cssSytle();
                $('#organize_table').bootstrapTable('refresh');
            }
        })
    }

    /*更改账户状态结束*/
var orgid = "";
    /*影响操作*/
    window.operateEvents = {
        /*修改*/
        'click .edit': function (e, value, row) {
            var id = row.id;
            window.location.href = appPath + '/admin/organize/' + id;
        },
        /*branchUser*/
        'click .branchUser': function (e, value, row) {
            orgid = row.id;
            orgMember(orgid);
            $('#member_modal').modal({backdrop: 'static', keyboard: false}).modal('show');

        },
    };
    function reloadpage() {
        var parEl;
        for (k = 0; k < CheckBoxNum; k++) {
            if (n[k].checked) {
                parEl = document.getElementById(n[k].id.substring(0, n[k].id.indexOf("_")));
                if (parEl != null) {
                    parEl.checked = true;
                }
                for (l = 0; l < CheckBoxNum; l++) {
                    if (n[l].id.substring(0, n[l].id.indexOf("_")) == n[k].id.substring(0, n[k].id.indexOf("_")) && n[l].id != n[k].id.substring(0, n[k].id.indexOf("_")) && n[l].type == "checkbox") {
                        if (n[l].checked == false) {
                            if (parEl != null) {
                                parEl.indeterminate = -1;
                            }
                        } else {
                        }
                    }
                }
            }
        }
    }

    $("#member").click(function () {
        $('#member_modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });


    function orgMember(orgid) {
        $("#admin_table").bootstrapTable('destroy');
        $("#member_table").bootstrapTable('destroy');
        $('#admin_table').bootstrapTable({
            url: appPath + '/admin/membersOrg',
            dataType: 'json',
            cache: true,
            // clickToSelect:true,
            search: true,
            showRefresh: true,
            striped: true,
            pagination: false,
            sidePagination: 'server',
            //pageSize:9999,
            iconsPrefix: 'glyphicon',
            icons: {
                refresh: 'md-refresh',
                columns: 'md-view-list-alt',
            },
            // data: data,
            // clickToSelect:true,
            // cache: true,
            // striped: true,
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                   // pageSize: params.limit, //页面大小
                    currPage: params.offset, //页码
                    search: params.search,
                    sort: params.sort,
                    order: params.order,
                    state: '0',
                    orgId: orgid,
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
                title: '真实姓名'
            }]
        });
        /*初始化俩个表格*/
        $('#member_table').bootstrapTable({
            url: appPath + '/admin/members',
            dataType: 'json',
            cache: true,
            // clickToSelect:true,
            search: true,
            showRefresh: true,
            striped: true,
            pagination: true,
            sidePagination: 'server',
            pageSize: 10,
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
                    state: '0',
                    orgId: orgid,
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
                title: '真实姓名'
            }]
        });

    }


    $('#btn2right').click(function () {
        var selectContent = $('#member_table').bootstrapTable('getSelections');
        //左侧ID
        var emberId = $.map(selectContent, function (row) {
            return row.id;
        });
        //右侧id
        var admind = $('#admin_table').bootstrapTable('getData');
        // 标识是否存在
        var emad=0;
        for (var i in emberId) {
            var emid = emberId[i];
            for(var ii = 0; ii < admind.length; ii++){
                var adid=admind[ii].id;
                if(adid==emid){
                    //存在
                    emad=1;
                }
            }
            if(emad==0){
                $('#admin_table').bootstrapTable("append", selectContent[i]);
            }else{
                emad=0;
            }
        }

    });


    $('#btn2left').click(function () {
        var selects = $('#admin_table').bootstrapTable('getSelections');
        var id = $.map(selects, function (row) {
            return row.id;
        });
        $('#admin_table').bootstrapTable('remove', {
            field: 'id',
            values: id
        });
    });
    $('#admin_table').on('dbl-click-row.bs.table', function (e, row) {
        $('#admin_table').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    });

    $('#admin_table').bootstrapTable("append", adminList);

    /*确认提交按钮*/
    $('#member-btn-true').click(function () {
        var d = $('#admin_table').bootstrapTable('getData');
        if (d.length == 0) {
            alert("请选择用户");
            return false;
        } else {
            var ids = [];
            var name = [];
            for (var i = 0; i < d.length; i++) {
                var u = d[i].username;
                var id = d[i].id;

                ids.push(id);
                name.push(u);
                /*   $('#memberId').val(ids);
                   $('#member').val(name);
                   $('#member_modal').modal('hide');*/
                $.ajax({
                    url: appPath + '/admin/organize/checkmember',
                    data: {"username": u, "id": id, "orgId": orgid},
                    dataType: "json",
                    type: 'get',
                    success: function (data) {
                        if (data.code == 1) {
                            $('#admin_table').bootstrapTable('remove', {
                                field: 'id',
                                values: [data.memberId]
                            });
                            ids.splice($.inArray(data.memberId, ids), 1);
                            name.splice($.inArray(data.memberName, name), 1);
                            alert(data.msg);
                        }
                        $('#memberId').val(ids);
                        $('#member').val(name);
                        $('#member_modal').modal('hide');
                    }
                });
            }
        }
    });

    /*点击删除机构下得用户*/
    $('#member-delete').click(function () {
        var d = $('#admin_table').bootstrapTable('getData');
        var selects = $('#admin_table').bootstrapTable('getSelections');
        var id = $.map(selects, function (row) {
            return row.id;
        });
        if (d.length == 0) {
            alert("请选择用户");
            return false;
        } else {
            for (var i = 0; i < id.length; i++) {
                var iid=id[i];
                $.ajax({
                    url: appPath + '/admin/organize/deleteOrg',
                    data: {
                        "id": iid
                    },
                    dataType: "json",
                    type: 'get',
                    success: function (data) {
                        if (data.state == 0) {
                            $('#member_modal').modal('hide');
                        }else {
                            alert("删除失败！！");
                        }

                    }
                });
            }
        }
    });

    /*状态下拉框*/
    $(".menu li a").click(function () {
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
        $('#organize_table').bootstrapTable("refresh");
    });

    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#organize_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }


    return {
        init: init,
    }
})();

OrganizeManager.init();