var addRoleManager = (function () {
    var flag = 0;
    var status = $("#status2").val();
    var attirbute = $("#attribute-menu-txt").val();
    if (status == null || status == '') {
        if ($("#pageSize").text() == '禁用') {
            flag = 1;
        }
        // 给flag赋值
        $("#status2").val(flag);
    }

    if (attirbute == null || attirbute == '') {
        $("#attribute-menu-txt").val("1");
    }


    /**
     * 初始化js
     */
    function init() {

        // 配置表单验证基本验证
        $.extend($.validator.messages, {
            required: '<span style="font-size: 12px;float:left;color:red; height: 36px;line-height: 36px;margin-left: 5px;"><em>* 必填</em></span>',
            minlength: "50",
        });
        // 验证角色名称（* 仅由汉字组成（3 ~ 10 个字符））
        $.validator.addMethod("roleName", function (value, element) {
            var roleNameReg = /^[\u4e00-\u9fa5]{3,10}$/;
            $("#realNameTip").html('');
            return this.optional(element) || (roleNameReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;height: 36px;line-height: 36px;margin-left: 5px;'><em>* 仅由汉字组成（3 ~ 10 个字符）</em></span>");

        // 验证角色描述（* 由字母汉字组成（5 ~ 50 个字符））
        $.validator.addMethod("remark", function (value, element) {
            var remarkReg = /^[A-Za-z,，、\-\u4e00-\u9fa5]{5,50}$/;
            return this.optional(element) || (remarkReg.test(value));
        }, "<span  style='font-size: 12px;float: left;color:red;margin-top: 5px;margin-left: 5px'><em>* 由字母汉字组成（5 ~ 50 个字符）</em></span>");

    }

    /* 表单验证 */
    subForm();

    /* 页面下拉菜单与赋值 */
    select();

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


    /**
     * 表单提交并验证
     */
    function subForm() {
        // 当表单进行提交时开始验证
        $("#addRole").validate({
            submitHandler: function () {
                var url = "";
                var val = $("#id").val();
                if (val == 'add') {
                    val = '0';
                    url = appPath + "/admin/role/" + val;
                } else {
                    url = appPath + "/admin/role/" + val;
                }

                var options = {
                    // beforeSubmit: checkForm,
                    success: showResponse,
                    url: url,
                    type: "post",
                    dataType: "json",
                    clearForm: false,
                    resetForm: false,
                    timeout: 120000
                };
                $('#addRole').ajaxSubmit(options);
            }
        });
        /* 回调函数 */
        function showResponse(result) {
            if (result.code == 1) {
                alert("添加成功");
                window.location.href = appPath + "/admin/role";
            } else if (result.code == 0) {
                $("#roleName").focus();
                $("#realNameTip").html("<em>* 该名称已被使用</em>");
                return false;
            } else if (result.code == -2) {
                alert("请选择权限");
                return false;
            }
        }

        /* 点击事件触发表单提交 */
        $("#save").click(function () {
            $("#addRole").submit();
        });
    }

    /**
     * 页面下拉菜单与赋值
     */
    function select() {
        /*状态下拉框*/
        $(".state li a").click(function () {
            var $this_val = $(this).html();
            $(".page-size2").text($this_val);
            if ($this_val == '启用') {
                flag = '0';
            } else if ($this_val == '禁用') {
                flag = '1';
            } else {
                flag = '0';
            }
            $("#status2").val(flag);
        });

        /* 角色属性下拉框 */
        $(".attribute-value li a").click(function () {
            var $this_val = $(this).html();
            $(".attribute_value").text($this_val);
            if ($this_val == '后台角色') {
                flag = '1';
                $("#test-div").show();
                $("#attribute-menu-div").hide();

            } else if ($this_val == '前台角色') {
                flag = '2';
                $("#test-div").hide();
                $("#attribute-menu-div").show();
            } else {
                flag = '1';
            }
            $("input[type='checkbox']").removeAttr('checked');
            $("#attribute-menu-txt").val(flag);
        });
    }

    /**
     * 返回函数
     */
    return {
        init: init,
        CheckAll: CheckAll,
        reloadpage: reloadpage
    }

})();

addRoleManager.init();