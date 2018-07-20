var BookManager = (function () {

    var option = {
        url: appPath + '/admin/feedback/list',
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
            field: 'content',
            class: 'hidden'
        }, {
            field: 'filename',
            class: 'hidden'
        }, {
            field: 'uploadfilepath',
            class: 'hidden'
        }, {
            field: 'realname',
            title: '姓名',
            formatter: function (value, row) {
                return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.realname + '</a>';
            },
            events: 'openFeendback'
        }, {
            field: 'phonenumber',
            title: '电话'
        }, {
            field: 'ip',
            title: '用户的IP地址'
        }, {
            field: 'date',
            title: '创建时间'
        }, {
            field: 'status',
            title: '状态',
            formatter: function (value, row) {
                var status = row.status;
                var s;
                if (status == 0) {
                    s = "<span class='label label-warning'>未阅读</span>";
                } else if (status == 1) {
                    s = "<span class='label label-success'>已阅读</span>";
                }
                return s;
            }
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
        $('#un-publish').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
        $('#syn_knowledge_single').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
        $('#publish').prop('disabled', !$('#book_table').bootstrapTable('getSelections').length);
    });
    /* 页面按钮disabled设置 */
    function cssSytle() {
        $('#publish').prop('disabled', true);
        $('#un-publish').prop('disabled', true);
    }

    /* 审核 */
    $("#un-publish").click(function () {
        $('#review-examine-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#review-examine-btn-true').click(function () {
        $('#review-examine-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '1');
    })
    /*审核状态*/
    function changeState(ids) {
        $.ajax({
            url: appPath + '/admin/feedback/update',
            dataType: 'json',
            type: 'get',
            data: {"id": ids},
            success: function (result) {
                cssSytle();
                console.log("执行成功" + result);
                $('#book_table').bootstrapTable('refresh');
            }
        })
    }

    /* 撤销发布资源结束 */


    /*影响操作*/
    window.openFeendback = {
        'click .open': function (e, value, row) {
            var name = row.realname;
            var id = row.id
            var content = row.content;
            var fileName = row.filename;
            showFeedBackInfo(id, name, content, fileName);
            changeState(id);
        },
    };
    /*状态下拉框*/
    $(".dropdown-menu li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '全部') {
            state = '';
        } else if ($this_val == '已阅读') {
            state = '1';
        } else if ($this_val == '未阅读') {
            state = '0';
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


    return {
        init: init,
    }
})();

BookManager.init();

function showFeedBackInfo(id, name, content, fileName) {
    $('#feedback_modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    $('#feedbackinfo-show').text(content)
    if (fileName == null) {
        $('#feedback-file').text("无")
        $('#feedback-file').attr("href", "javascript:void(0)")
    } else {
        $('#feedback-file').text(fileName)
        $('#feedback-file').attr("href", "feedback/download?id=" + id)
    }
}