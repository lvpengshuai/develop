var BookManager = (function () {

    var option = {
        url: appPath + '/admin/knowledge/list',
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
            field: 'parentClass',
            title: '类别'
        }, {
            field: 'createTime',
            title: '创建时间'
        }, {
            field: 'lastSynTime',
            title: '同步时间'
        }, {
            field: 'status',
            title: '状态',
            formatter: function (value, row) {
                var status = row.status;
                var s;
                if (status == 0) {
                    s = "<span class='label label-warning'>未发布</span>";
                } else if (status == 1) {
                    s = "<span class='label label-success'>已发布</span>";
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

    /* 全部同步 */
    $("#syn_knowledge").click(function () {
        $('#sys_knowledge_model').modal({backdrop: 'static', keyboard: false}).modal('show');

    });
    $('#alllsyn-modal-btn-true').click(function () {
        $('#sys_knowledge_model').modal('hide');
        var ids = getIdSelections();
        $('#syn-info-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        showResult();
    });
    /* 全部同步结束 */

    /* 根据id同步 */
    $("#syn_knowledge_single").click(function () {
        $('#knowledge_syn').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $("#knowledge_syn_go").click(function () {
        $('#knowledge_syn').modal({backdrop: 'static', keyboard: false}).modal('hide');
        $('#syn-info-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
        $('#syn_info_show').hide();
        $('#syn_info_show_single').show();

        var ids = getIdSelections();
        $.ajax({
            url: 'knowledge/synchro',
            dataType: 'json',
            type: 'post',
            data: {"id": ids},
            success: function (result) {
                $('#syn_words_all').text(result.counts);
                $('#syn_words_base').text(result.baseWords);
                $('#syn_words_pro').text(result.proWords);
                $('#status_syn').text(result.status);
                $('#current_stn_info').text(result.current);
            }
        });
    });
    //根据id同步结束


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

    /* 发布资源开始 */
    $("#publish").click(function () {
        $('#publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');

    });
    $('#publish-modal-btn-true').click(function () {
        $('#publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '1');
    });
    /* 发布资源结束 */
    /* 撤销发布资源开始 */
    $("#un-publish").click(function () {
        $('#un-publish-modal').modal({backdrop: 'static', keyboard: false}).modal('show');
    });
    $('#un-publish-modal-btn-true').click(function () {
        $('#un-publish-modal').modal('hide');
        var ids = getIdSelections();
        changeState(ids, '0');
    })
    /*更改图书状态*/
    function changeState(ids, state) {
        $.ajax({
            url: appPath + '/admin/knowledge/publish',
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

    /*全部同步*/
    function showResult() {
        $('#syn_info_show').show()
        $('#syn_info_show_single').hide()
        $.ajax({
            url: appPath + '/admin/knowledge/synchro/all',
            dataType: 'json',
            type: 'post',
            success: function (result) {
                cssSytle();
                $('#syn_words_all').text(result.counts);
                $('#n_syn_words_base').text(result.n_aseWords);
                $('#n_syn_words_pro').text(result.n_proWords);
                $('#y_syn_words_base').text(result.y_baseWords);
                $('#y_syn_words_pro').text(result.y_proWords);
                $('#status_syn').text(result.status);
                $('#begin-syn').text(result.begin);
                $('#stop-syn').text(result.stop);
                $('#syn-ip').text(result.ip);
            }
        })
    }


    /*影响操作*/
    window.openKnowledge = {
        'click .open': function (e, value, row) {
            var name = row.knowledgeName;

            window.open(appPath + '/knowledge/' + name);
        },
    };
    /*状态下拉框*/
    $("#knowledge-dropdown-menu li a").click(function () {
        var $this_val = $(this).html();
        $(".page-size2").text($this_val);
        var state = '';
        if ($this_val == '全部') {
            state = '';
        } else if ($this_val == '已发布') {
            state = '1';
        } else if ($this_val == '未发布') {
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