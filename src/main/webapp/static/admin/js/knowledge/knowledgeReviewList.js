var BookManager = (function () {

    var option = {
        url: appPath + '/admin/knowledge/review/list',
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
            field: 'knowledgeName',
            title: '知识元名称',
            formatter: function (value, row) {
                return '<a href="javascript:void(0);" class="open" style="text-decoration: none;color: #757575;">' + row.knowledgeName + '</a>';
            },
            events: 'openKnowledge'
        }, {
            field: 'ip',
            title: '用户的IP地址'
        }, {
            field: 'manager',
            title: '审核员'
        }, {
            field: 'createDate',
            title: '创建时间'
        }, {
            field: 'examineDate',
            title: '审核时间'
        }, {
            field: 'status',
            title: '状态',
            formatter: function (value, row) {
                var status = row.status;
                var s;
                if (status == 0) {
                    s = "<span class='label label-warning'>未审核</span>";
                } else if (status == 1) {
                    s = "<span class='label label-success'>已经审核</span>";
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
    function changeState(ids, state) {
        $.ajax({
            url: appPath + '/admin/knowledge/review/examine',
            dataType: 'json',
            type: 'get',
            data: {"id": ids, "state": state},
            success: function (result) {
                cssSytle();
                console.log("执行成功" + result);
                $('#book_table').bootstrapTable('refresh');
            }
        })
    }

    /* 撤销发布资源结束 */


    /*影响操作*/
    window.openKnowledge = {
        'click .open': function (e, value, row) {
            var name = row.knowledgeName;
            var id = row.id;
            showReviewInfo(id, name);
        },
    };
    /*状态下拉框*/
    $(".dropdown-menu li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '全部') {
            state = '';
        } else if ($this_val == '已审核') {
            state = '1';
        } else if ($this_val == '未审核') {
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