var addOrganizeManager = (function () {

    function init() {
        var result = false;
        var errorIp = '非法的IP地址:';
        /*配置相同的值*/
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;">*必填</span>',
            // equalTo: "<span  style='font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;'>两次输入的密码不一致</span>",
            minlength: "50",
        });
        /*验证ip*/
        $.validator.addMethod("ips", function (value, element) {
            var spt = value.split("\n");
            var ipReg = /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\-(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/;
            var ipReg1 = /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/;
            for(var i = 0;i < spt.length;i++){
                console.log(spt[i]);
                if (spt[i].indexOf("-") != -1){
                    if(spt[i] != "" && spt[i] != null){
                        if(ipReg.test(spt[i])){
                            result = true;
                            $("#errorIps").html('<span id="errorIps"></span>');
                            //alert("=====>"+result);
                        } else{
                            result = false;
                            $("#errorIps").html('<span id="errorIps" style="font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;">非法的地址IP:'+spt[i]+'</span>');
                            errorIp =  spt[i];
                            //alert(">>>>>>"+result);
                            return;
                        }
                    }
                }else {
                    if(spt[i] != "" && spt[i] != null){
                        if(ipReg1.test(spt[i])){
                            result = true;
                            $("#errorIps").html('<span id="errorIps"></span>');
                            //alert("=====>"+result);
                        } else{
                            result = false;
                            $("#errorIps").html('<span id="errorIps" style="font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;">非法的地址IP:'+spt[i]+'</span>');
                            errorIp =  spt[i];
                            //alert(">>>>>>"+result);
                            return;
                        }
                    }
                }
            }
            return this.optional(element) || result;
        }, "");
        $.validator.addMethod("maxOnline", function (value, element) {
            var maxOnlineReg = /^[1-9]\d*$/;
            return this.optional(element) || (maxOnlineReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>非法的数值</span>");
        // /*验证电话*/
        //$.validator.addMethod("telephone", function (value, element) {
        //    var telephoneReg = /^[1-9]\d*$/;
        //    return this.optional(element) || (telephoneReg.test(value));
        //}, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'>请输入正确的电话号码</span>");

    }

    $("#expiration").datetimepicker({
        format: 'yyyy-mm-dd',
        // format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        language: 'zh-CN',
        todayHighlight: 1,
        startView: 2,
        forceParse: 1,
        // minView: "hour",
        minView: "month"
    });

    $(document).ready(function () {
        var MaxInputs = 8; //maximum input boxes allowed
        var InputsWrapper = $("#InputsWrapper"); //Input boxes wrapper ID
        var InputsWrapperOne = $("#InputsWrapperOne"); //Input boxes wrapper ID
        var AddButton = $("#AddMoreFileBox"); //Add button ID
        var AddButtonOne = $("#AddMoreFileBoxOne"); //Add button ID
        var x = InputsWrapper.length; //initlal text box count
        var FieldCount = 1; //to keep track of text box added
        $(AddButton).click(function (e) //on add input button click
        {
            if (x <= MaxInputs) //max input box allowed
            {
                FieldCount++; //text box added increment
                //add input box
                $(InputsWrapper).append('<div class="form-group height-div" style="margin-bottom: 10px">' +
                    '<input type="text" class="form-control ip" placeholder="请填写机构IP范围开始" name="ipStart" required/>' +
                    '<i style="float:left;line-height: 36px" aria-hidden="true">—</i>' +
                    '<input type="text" class="form-control ip" placeholder="请填写机构IP范围结束" name="ipEnd" required/>' +
                    '<a href="javascript:void(0);" style="text-decoration: none;line-height: 36px;margin-left: 10px;color: #4caf50" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="glyphicon glyphicon-minus removeclass"></a>' +
                    '</div>');
                x++; //text box increment
            }
            return false;
        });
        $(AddButtonOne).click(function (e) //on add input button click
        {
            if (x <= MaxInputs) //max input box allowed
            {
                FieldCount++; //text box added increment
                //add input box
                $(InputsWrapperOne).append('<div class="form-group height-div" style="margin-bottom: 10px">' +
                    '<input type="text" class="form-control ip" placeholder="请填写机构IP" name="ipStartOne" required/>' +
                    '<a href="javascript:void(0);" style="text-decoration: none;line-height: 36px;margin-left: 10px;color: #4caf50" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="glyphicon glyphicon-minus removeclass"></a>' +
                    '</div>');
                x++; //text box increment
            }
            return false;
        });
        $("body").on("click", ".removeclass", function (e) { //user click on remove text
            // if (x > 1) {
            $(this).parent('div').remove(); //remove text box
            x--; //decrement textbox
            // }
            return false;
        })
    });

    $("#addOrganize").validate({
        submitHandler: function () {
            var url = appPath + "/admin/organize";
            var options = {
                success: showResponse,
                url: url,
                dataType: "json",
                type: "post",
                clearForm: false,
                resetForm: false,
                timeout: 120000
            };
            $('#addOrganize').ajaxSubmit(options);
        }
    });
    function showResponse(result) {
        if (result.state == 0) {
            window.location.href = appPath + "/admin/organize";
        } else if (result.state == 1) {
            alert("添加失败");
        } else if (result.state == 2) {
            $("#name").focus();
            $("#nameTip").html("<em>* 该机构已经被注册</em>");
        }else if (result.state == 3){
            alert(result.msg);
        }
    }

    $("#save").click(function () {
        $("#addOrganize").submit();
    });

    /* 初始化权限列表 */
    var n = document.getElementsByTagName("INPUT");
    var CheckBoxNum = n.length;
    var SubNodeCheckSome, SameNodeCheckSome, tf, SearchNodeName, SearchParentNodeName, SameNodeNum, SubNodeCheckedNum,
        SameNodeCheckedNum, SubNodeNum, SubNodeCheckedTF;

    /**
     * 查找父节点
     * @param SubNodeName
     * @returns {*}
     * @constructor
     */
    function FindParentNode(SubNodeName) {
        tf = false;
        SearchNodeName = SubNodeName;
        t = SubNodeName.lastIndexOf("_");
        if (t != -1) SearchNodeName = SubNodeName.substring(0, t);
        if (document.getElementById(SubNodeName).checked) tf = true;
        return SearchNodeName;
    }

    /**
     * 检查子节点
     * @param NodeName
     * @returns {*}
     * @constructor
     */
    function CheckSubNode(NodeName) {
        SubNodeCheckedTF = false;
        SubNodeNum = 0;
        SameNodeNum = 0;
        SubNodeCheckedNum = 0;
        SameNodeCheckedNum = 0;
        SubNodeCheckSome = 0;
        SameNodeCheckSome = 0;
        ParentNodeName = FindParentNode(NodeName);
        SearchParentNodeName = NodeName;
        d = NodeName.lastIndexOf("_");
        if (d != -1) SearchParentNodeName = SearchParentNodeName.substring(0, d);
        for (i = 0; i < CheckBoxNum; i++) {
            if (n[i].id.length == NodeName.length && ParentNodeName == FindParentNode(n[i].id)) {
                SameNodeNum += 1;
                if (n[i].checked) SameNodeCheckedNum += 1;
                if (n[i].indeterminate) SameNodeCheckSome += 1;
            }
            if (n[i].id.substring(0, NodeName.length) == NodeName && n[i].id != NodeName && n[i].type == "checkbox") {
                SubNodeNum += 1;
                if (n[i].checked) SubNodeCheckedNum += 1;
                if (n[i].indeterminate) SubNodeCheckSome += 1;
            }
        }

        if ((SameNodeNum == 1 || SameNodeCheckedNum == 0) && (SubNodeCheckedNum == 0) && !document.getElementById(NodeName).checked)
            SubNodeCheckedTF = true;
        if ((SameNodeNum >= 0 && SameNodeCheckedNum < SameNodeNum) || SameNodeCheckSome > 0 || SubNodeCheckSome > 0)
            document.getElementById(SearchParentNodeName).indeterminate = true;
        if ((SameNodeCheckedNum == SameNodeNum || SameNodeCheckedNum == 0) && SubNodeCheckSome == 0 && SameNodeCheckSome == 0)
            document.getElementById(SearchParentNodeName).indeterminate = false;
        return SearchParentNodeName;
    }

    /**
     * 点击选中权限
     * @param BoxName
     * @constructor
     */
    function CheckAll(BoxName) {
        SearchNodeName = BoxName;
        SearchParentNodeName = BoxName;
        SubNodeLength = BoxName.split("_").length;
        for (i = 0; i < CheckBoxNum; i++) {
            if (n[i].id.substring(0, BoxName.length) == BoxName && n[i].id != BoxName && n[i].type == "checkbox") {
                n[i].indeterminate = false;
                n[i].checked = document.getElementById(BoxName).checked ? true : false;
            }
        }
        for (j = 1; j < SubNodeLength; j++) {
            document.getElementById(CheckSubNode(SearchParentNodeName)).checked = SubNodeCheckedTF ? false : true;
        }
    }

    /**
     * 选中已经具备的权限
     */
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
        orgMember($("#orgId").val());
        $('#member_modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });

    function orgMember(orgid) {
        $("#admin_table").bootstrapTable('destroy');
        $("#member_table").bootstrapTable('destroy');
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
                    state: '0'
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
        $('#admin_table').bootstrapTable({
            url: appPath + '/admin/membersOrg',
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
    $('#member_table').on('dbl-click-row.bs.table', function (e, row) {
        $.ajax({
            url: appPath + '/admin/organize/checkmember',
            data: {"username": row.username, "id": row.id, "orgId": $("#orgId").val()},
            dataType: "json",
            type: 'get',
            success: function (data) {
                if (data.code == 1) {
                    $('#admin_table').bootstrapTable('remove', {
                        field: 'id',
                        values: [data.memberId]
                    });
                    alert(data.msg);
                } else {
                    $('#admin_table').bootstrapTable("append", row);
                }
            }
        });
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
                    data: {"username": u, "id": id, "orgId": $("#orgId").val()},
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
    return {
        init: init,
        CheckAll: CheckAll,
        reloadpage: reloadpage
    }

})();

addOrganizeManager.init();