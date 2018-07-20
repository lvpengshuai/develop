var BookManager = (function () {

    var option = {
        url: appPath + '/admin/books',
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
        toolbar: '#book_table_toolbars',
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
            field: 'bookName',
            title: '图书名称',
            formatter: function (value, row) {
                return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.bookName + '</a>';
            },
            events: 'openBook'
        }, {
            field: 'bookAuthor',
            title: '图书作者'
        }, {
            field: 'isbnFull',
            title: 'ISBN'
        }, {
            field: 'clc',
            title: '中图分类'
        }, {
            field: 'pubDate',
            title: '出版时间',
            sortable: true
        }, {
            field: 'gmtCreate',
            title: '添加时间',
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
            field: 'price',
            title: '定价',
        }, {
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
        }, {
            title: '操作',
            formatter: function (value, row) {
                var state = row.state;
                var d;
                if (state == "1") {
                    d = '<a class="edit" href="javascript:void(0)" style="color: #757575;" title="修改">' +
                        '<span class="glyphicon glyphicon-edit"></span>' +
                        '</a>';
                } else {
                    d = '';
                }
                return  d;
            },
            events: 'operateEvents'
        }]

    };

    function init() {
        $('#book_table').bootstrapTable(option);
    }

    /*修改table语言为中文*/
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

    /*至少有一个复选框被选择后操作按钮才可用*/
    $('#book_table').on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
        $('#un-publish').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
        $('#publish').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#publish').prop('disabled', true);
        $('#remove').prop('disabled', true);
        $('#un-publish').prop('disabled', true);
    }

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
    function showResponse(result) {

        $('#wait-modal').modal('hide');
        $('#import-modal').modal('hide');
        if (result.state == '0') {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text(result.msg);
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#book_table').bootstrapTable('refresh');
        } else {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text(result.msg);
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#book_table').bootstrapTable('refresh');
        }
    }

    $(function () {
        var url = appPath + "/admin/book/import";
        var options = {
            success: showResponse,
            dataType: "json",
            url: url,
            type: "post",
            clearForm: true,
            resetForm: true,
            timeout: 600000
        };
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

    /*指定目录导入*/
    $("#path-btn-import").click(function () {
        $('#path-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#path-import-modal-btn-true').prop('disabled', true);
    });
    function btnstate() {
        if (!$('#path-file_input').val().trim()) {
            $('#path-import-modal-btn-true').prop('disabled', true);
        } else {
            $('#path-import-modal-btn-true').prop('disabled', false);
        }
    }

    function checkFormByPath() {
        $('#path-modal').modal('hide');
        $('#wait-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    }

    function showResponseByPath(result) {
        $('#wait-modal').modal('hide');

        if (result.state == '0') {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text(result.msg);
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#book_table').bootstrapTable('refresh');

        } else {
            $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
            $('#result-modal-title').html("提示");
            $('#result-modal-body').text(result.msg);
            $('#result-modal-btn-false').html("确定");
            $('#result-modal-btn-true').prop('style', 'display:none');
            $('#book_table').bootstrapTable('refresh');
        }
    }

    $(function () {
        var url = appPath + "/admin/book/import";
        var options = {
            beforeSubmit: checkFormByPath,
            success: showResponseByPath,
            dataType: "json",
            url: url,
            type: "post",
            data: {'path': $("#path-file_input").val()},
            clearForm: true,
            resetForm: true,
            timeout: 60000
        };
        $("#path-up-load").submit(function () {
            $("#path-up-load").ajaxSubmit(options);
            return false;
        });
    });
    $('#path-import-modal-btn-true').click(function () {
        $("#path-up-load").submit();
    });
    /*指定目录导入*/

    /* 发布资源开始 */
    $("#publish").click(function () {
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');

    });
    $('#publish-modal-btn-true').click(function () {
        $('#publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '0');
    });
    /* 发布资源结束 */

    /* 撤销发布资源开始 */
    $("#un-publish").click(function () {
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#un-publish-modal-btn-true').click(function () {
        $('#un-publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '1');
    });
    /* 撤销发布资源结束 */

    /*更改图书状态*/
    function changeState(ids, state) {
        $.ajax({
            url: appPath + '/admin/book/' + ids + '/' + state,
            dataType: 'json',
            type: 'post',
            success: function (result) {
                cssSytle();
                $('#book_table').bootstrapTable('refresh');
            }
        })
    }

    /* 删除资源开始 */
    $("#remove").click(function () {
        $('#remove-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#remove-modal-btn-true').click(function () {
        $('#remove-modal').modal('hide');
        var ids = getIdSelections();
        $.ajax({
            url: appPath + '/admin/book/' + ids,
            dataType: 'json',
            type: 'delete',
            success: function (result) {
                $('#result-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
                $('#result-modal-title').html("提示");
                if (result.deltotal == 0) {
                    $('#result-modal-body').text("其中" + (result.total - result.deltotal) + "条数据已发布，无法删除。");
                } else if ((result.total - result.deltotal) == 0) {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。");
                } else {
                    $('#result-modal-body').text("共选择" + result.total + "条数据，" +
                        "成功删除" + result.deltotal + "条数据。其中" + (result.total - result.deltotal) + "条数据已发布，无法删除。");

                }
                $('#result-modal-btn-false').html("确定");
                $('#result-modal-btn-true').prop('style', 'display:none');

                cssSytle();
                $('#book_table').bootstrapTable('refresh');
            }
        })
    });
    /* 删除资源结束 */

    /*影响操作*/
    window.operateEvents = {
        /*修改图书*/
        'click .edit': function (e, value, row) {
            var id = row.id;

            window.location.href = appPath + '/admin/book/' + id;
        },
    };
    window.openBook = {
        'click .open': function (e, value, row) {
            var id = row.id;

            window.open(appPath + '/book/' + id + "?isback=0");
        },
    };
    /*状态下拉框*/
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
        $('#book_table').bootstrapTable("refresh");
    });

    /*获取多选框ID*/
    function getIdSelections() {
        return $.map($('#book_table').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

    /*参考文献处理*/
    $("#reference-documentation-import").click(function () {
        $('#reference-documentation-modal').modal({backdrop: 'static', keyboard: false}).modal('show');

        $('#documentation_table').bootstrapTable({
            url:appPath + '/admin/documentation',
            dataType:'json',
            cache:true,
            search:true,
            showRefresh:true,
            striped:true,
            checkboxHeader:true,
            pagination:true,
            sidePagination:'server',
            pageSize:10,
            pageList:[20, 50, 100, 200],
            iconsPrefix:'glyphicon',
            icons:{
                    refresh: 'md-refresh',
                    columns:'md-view-list-alt',
                },
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize: params.limit, //页面大小
                    currPage: params.offset, //页码
                    search: params.search,
                };
                return temp;
            }
            ,
            columns: [{
                checkbox: false,
                align: 'center'
            }, {
                field: 'id',
                class: 'hidden'
            }, {
                field: 'content',
                title: '参考文献',
                formatter:function(value,row){
                    var content = row.content;
                    var d;
                    if(content.length>60){
                       d='<span title='+content+'>'+content.substring(1,60)+"..."+'</span>';
                    }else{
                        d=content;
                    }
                    return  d;
                }
            }, {
                field: 'type',
                title: '类型',
                formatter:function(value,row){
                    var type = row.type;
                    var d;
                    if(type == null){
                        d = '无';
                    }else{
                        d=type;
                    }
                    return  d;
                },
                editable: {
                    type: 'select',
                    title: '类型',
                    source: [{ value: "无", text: "无" }, { value: "电子书", text: "电子书" }, { value: "期刊", text: "期刊" }, { value: "标准", text: "标准" }]
                }

            },
                {
                field: 'isbn',
                title: 'ISBN',
                formatter:function(value,row){
                    var isbn = row.isbn;
                    var d;
                    if(isbn == null){
                        d = '无';
                    }else{
                        d=isbn;
                    }
                    return  d;
                },
                editable: {
                    type: 'text',
                    title: 'ISBN',
                    validate: function (v) {
                        var re = /[^\u4e00-\u9fa5a-zA-Z]/;
                        if (!re.test(v))
                        {
                            return '请输入正确的数值数字';
                        }
                    }
                }

            }
            ],
            onEditableSave:function (field, row, oldValue, $el) {
                    $.ajax({
                        type: "post",
                        url: appPath + '/admin/updateDocumentation',
                        data: {'data': JSON.stringify(row)},
                        dataType: 'JSON',
                        success: function (data) {

                        },
                        error: function () {
                            alert('编辑失败');
                        }
                    });
                }
        });

    });

    /*多选框选择执行*/
    function initCK(ck,isbn){
        $("#documentation_table").bootstrapTable('destroy');
        $('#documentation_table').bootstrapTable({
            url:appPath + '/admin/documentationCK',
            dataType:'json',
            cache:true,
            search:true,
            showRefresh:true,
            striped:true,
            checkboxHeader:true,
            pagination:true,
            sidePagination:'server',
            pageSize:10,
            pageList:[20, 50, 100, 200],
            iconsPrefix:'glyphicon',
            icons:{
                refresh: 'md-refresh',
                columns:'md-view-list-alt',
            },
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize: params.limit, //页面大小
                    currPage: params.offset, //页码
                    search: params.search,
                    ckData:ck,
                    isbnData:isbn,
                };
                return temp;
            }
            ,
            columns: [{
                checkbox: false,
                align: 'center'
            }, {
                field: 'id',
                class: 'hidden'
            }, {
                field: 'content',
                title: '参考文献',
                formatter:function(value,row){
                    var content = row.content;
                    var d;
                    if(content.length>60){
                        d = '<span title='+content+'>'+content.substring(1,60)+"..."+'</span>';
                    }else{
                        d=content;
                    }
                    return  d;
                }
            }, {
                field: 'type',
                title: '类型',
                formatter:function(value,row){
                    var type = row.type;
                    var d;
                    if(type == null){
                        d = '无';
                    }else{
                        d=type;
                    }
                    return  d;
                },
                editable: {
                    type: 'select',
                    title: '类型',
                    source: [{ value: "无", text: "无" }, { value: "电子书", text: "电子书" }, { value: "期刊", text: "期刊" }, { value: "标准", text: "标准" }]
                }

            },
                {
                    field: 'isbn',
                    title: 'ISBN',
                    formatter:function(value,row){
                        var isbn = row.isbn;
                        var d;
                        if(isbn == null){
                            d = '无';
                        }else{
                            d=isbn;
                        }
                        return  d;
                    },
                    editable: {
                        type: 'text',
                        title: 'ISBN',
                        validate: function (v) {
                            var re = /[^\u4e00-\u9fa5a-zA-Z]/;
                            if (!re.test(v))
                            {
                                return '请输入正确的数值数字';
                            }
                        }
                    }

                }
            ],
            onEditableSave:function (field, row, oldValue, $el) {
                $.ajax({
                    type: "post",
                    url: appPath + '/admin/updateDocumentation',
                    data: {'data': JSON.stringify(row)},
                    dataType: 'JSON',
                    success: function (data) {
                        // if (data.status == "1") {
                        //     alert(data.msg);
                        // }
                     //   alert(JSON.stringify(data));

                    },
                    error: function () {
                        alert('编辑失败');
                    }
                });
            }
        });
    };

    /*多选框未选中执行*/
    function initNull(){
        $("#documentation_table").bootstrapTable('destroy');
        $('#documentation_table').bootstrapTable({
            url:appPath + '/admin/documentationCK',
            dataType:'json',
            cache:true,
            search:true,
            showRefresh:true,
            striped:true,
            checkboxHeader:true,
            pagination:true,
            sidePagination:'server',
            pageSize:10,
            pageList:[20, 50, 100, 200],
            iconsPrefix:'glyphicon',
            icons:{
                refresh: 'md-refresh',
                columns:'md-view-list-alt',
            },
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize: params.limit, //页面大小
                    currPage: params.offset, //页码
                    search: params.search,
                    ckData:"未选中",
                };
                return temp;
            }
            ,
            columns: [{
                checkbox: false,
                align: 'center'
            }, {
                field: 'id',
                class: 'hidden'

            }, {
                field: 'content',
                title: '参考文献',
                formatter:function(value,row){
                    var content = row.content;
                    var d;
                    if(content.length>60){
                        d = '<span title='+content+'>'+content.substring(1,60)+"..."+'</span>';
                    }else{
                        d=content;
                    }
                    return  d;
                }
            }, {
                field: 'type',
                title: '类型',
                formatter:function(value,row){
                    var type = row.type;
                    var d;
                    if(type == null){
                        d = '无';
                    }else{
                        d=type;
                    }
                    return  d;
                },
                editable: {
                    type: 'select',
                    title: '类型',
                    source: [{ value: "无", text: "无" }, { value: "电子书", text: "电子书" }, { value: "期刊", text: "期刊" }, { value: "标准", text: "标准" }]
                }

            },
                {
                    field: 'isbn',
                    title: 'ISBN',
                    formatter:function(value,row){
                        var isbn = row.isbn;
                        var d;
                        if(isbn == null){
                            d = '无';
                        }else{
                            d=isbn;
                        }
                        return  d;
                    },
                    editable: {
                        type: 'text',
                        title: 'ISBN',
                        validate: function (v) {
                            var re = /[^\u4e00-\u9fa5a-zA-Z]/;
                            if (!re.test(v))
                            {
                                return '请输入正确的数值数字';
                            }
                        }
                    }

                }
            ],
            onEditableSave:function (field, row, oldValue, $el) {
                $.ajax({
                    type: "post",
                    url: appPath + '/admin/updateDocumentation',
                    data: {'data': JSON.stringify(row)},
                    dataType: 'JSON',
                    success: function (data) {
                       // alert(JSON.stringify(data));
                    },
                    error: function () {
                        alert('编辑失败');
                    }
                });
            }
        });
    };

    /*参考文献处理结束*/

    return {
        init: init,
        btnstate: btnstate,
        btnstate:btnstate,
        initCK:initCK,
        initNull:initNull,
    }
})();
/*参考文献处理复选框选择事件*/
function ckChange(){
    var ck="";// 化工出版社
    var isbn="";// 无
    $("input:checkbox[name='ckChemical']:checked").each(function() {
        ck = $(this).val();
    });
    $("input:checkbox[name='ckISBN']:checked").each(function() {
        isbn = $(this).val();
    });
    if(ck==""&&isbn==""){
        BookManager.initNull(ck,isbn);
    }else{
        BookManager.initCK(ck,isbn);
    }


}
/*参考文献处理复选框选择事件*/
BookManager.init();