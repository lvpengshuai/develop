    <%--
      User: zly
      Date: 2017-3-6
      Time: 18:24
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%--一些共用的模态框可以直接调用，无需再定义，如果有要单独定义的模态框，请按照通用模态框的命名方式，这样方便调用，也方便知道其用处--%>
    <%--通用提示信息模态框--%>
    <div class="modal fade" id="result-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="result-modal-title">
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center" id="result-modal-body"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="result-modal-btn-false"></button>
                    <button type="button" class="btn btn-danger" id="result-modal-btn-true"></button>
                </div>
            </div>
        </div>
    </div>
    <%--通用提示信息模态框开始--%>

    <%--导入模态框开始--%>
    <div class="modal fade" id="import-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button id="close-modal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="import-modal-title">导入
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body" id="import-modal-body">
                    <div class="form-group form-material floating">
                        <form class="form-horizontal" autocomplete="off"
                              id="up-load" method="post"
                              enctype="multipart/form-data">
                            <input type="text" id="tmp-file-input" class="form-control" readonly=""/>
                            <input type="file" id="file_input" name="file" multiple=""/>
                            <label class="floating-label">请选择..</label>
                            <span id="file-up-load-value" class="valid_message" style="font-size: 12px;color: red"></span>
                        </form>
                    </div>
                    <div id="uploadScroll" style="display: none;">
                        <span>上传进度：</span>
                        <div class="progress progress-lg">
                            <div class="progress-bar progress-bar-info progress-bar-striped" id="scroll"  role="progressbar">
                                <span class="sr-only" id="baifenbi"></span>
                                <%--<font id="baifenbi"></font>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="import-modal-btn-false">取消
                    </button>
                    <button type="button" class="btn btn-danger"
                            id="import-modal-btn-true">导入
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%--导入模态框开始--%>
    <%--  等待模态 --%>
    <div class="modal fade" id="wait-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="margin-left: -9px;background: none">
                <div class="modal-body" id="wait-modal-body" style="text-align: center;height: 302px;">
                    <div style="margin-top: 80px">
                        <div>
                            <img style="width: 50px;height: 50px" src="${pageContext.request.contextPath}/static/admin/images/wait.gif">
                        </div>
                        <div class="modal-header">
                            <h4 class="modal-title" id="wait-modal-title">正在解析，请稍后……
                                <small></small>
                            </h4>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <%-- 等待模态 --%>
    <%--电子书使用指定文件目录导入模态框开始--%>
    <div class="modal fade" id="path-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="path-modal-title">导入
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body" id="path-modal-body">
                    <div class="form-group form-material floating">
                        <form class="form-horizontal" autocomplete="off"
                              id="path-up-load" method="post">
                            <input type="text" class="form-control" id="path-file_input" placeholder="请输入目录" onpropertychange="BookManager.btnstate()" oninput="BookManager.btnstate()" name="path"/>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="path-import-modal-btn-false">取消</button>
                    <button type="button" class="btn btn-danger" id="path-import-modal-btn-true">导入
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%--电子书使用指定文件目录导入模态框结束--%>

    <%-- 发布摸态框开始 --%>
    <div class="modal fade" id="publish-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">发布
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    您确定要发布吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="publish-modal-btn-true">发布</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 发布摸态框结束 --%>

    <%-- 撤销发布摸态框开始 --%>
    <div class="modal fade" id="un-publish-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">撤销发布
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    您确定要撤销发布吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="un-publish-modal-btn-true">撤销发布</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 撤销发布摸态框结束 --%>

    <%-- 删除摸态框开始 --%>
    <div class="modal fade" id="remove-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">删除
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    您确定要删除吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="remove-modal-btn-true">删除</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 删除摸态框结束 --%>

    <%-- 知识体系-本体 --%>
    <div class="modal fade" id="ontology-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="share-modal-title">
                        <small></small>
                    </h4>
                </div>

                <div class="modal-body" id="share-modal-body">
                    <div class="form-group form-material floating">
                        <form class="form-horizontal" autocomplete="off" id="rdf-form" method="post"
                              enctype="multipart/form-data">
                            <input type="text" class="form-control" readonly=""/>
                            <input type="file" id="rdf-file" name="file" multiple=""/>
                            <label class="floating-label">请选择..</label>
                        </form>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消
                    </button>
                    <button type="button" class="btn btn-danger" id="rdf-btn-upload">导入
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%-- 本体结束 --%>

    <%-- 知识元同步 --%>
    <div class="modal fade" id="sys_knowledge_model" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">任务
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    <ul id="showInfo">
                        <span style="color: #f92672">全部同步耗费系统资源，建议夜间执行，您确定要同步吗？</span>
                    </ul>
                </div>
                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="alllsyn-modal-btn-true">同步</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 知识元同步结束 --%>

    <%-- 知识元同步信息 --%>
    <div class="modal fade" id="syn-info-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 id="status_syn" class="modal-title">任务未成功提交,请联系管理员</h4>
                </div>
                <div class="modal-body">
                    <div id="syn_info_show" class="example-grid">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">总词数：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="syn_words_all" class="example-col">none</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">未同步的基础属性个数：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="n_syn_words_base" class="example-col">none</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">未同步的专业属性个数：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="n_syn_words_pro" class="example-col">none</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">已经同步的基础属性个数：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="y_syn_words_base" class="example-col">none</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">已经同步的专业属性个数：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="y_syn_words_pro" class="example-col">none</div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">分段同步的知识元：</div>
                            </div>
                            <div class="col-md-6">
                                <div  class="example-col"><span id="begin-syn" class="">none</span>-<span id="stop-syn" class="">none</span></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">执行同步任务的主机ip：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="syn-ip" class="example-col">none</div>
                            </div>
                        </div>



                    </div>
                    <div id="syn_info_show_single" class="example-grid">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="example-col">同步任务：</div>
                            </div>
                            <div class="col-md-6">
                                <div id="current_stn_info" class="example-col"></div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <%-- 知识元同步信息结束 --%>

    <%--后台查看纠错详情--%>
    <div class="modal fade in" id="knowledge_info" aria-hidden="true" aria-labelledby="exampleModalTabs" role="dialog"
         tabindex="-1" style="display: none; padding-right: 21px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="h-review" id="knowledge_review_info">
                        <h6>知识元名称：</h6>
                        <a style="cursor:pointer" class="" id="review-k-go"></a>
                        <h6>模块名称：</h6>
                        <a style="cursor:pointer" class="" id="review-m-go"></a>
                        <h6>用户反馈：</h6>
                        <div readonly="readonly" name="" id="review-info-user-tickling"></div>
                    </div>
                    <button style="margin-left:85%" type="button" class="btn btn-success btn-icon"
                            id="review-info-examine">
                        <i class="" aria-hidden="true"></i> 审核
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%-- 后台查看纠错详情结束 --%>

    <%-- 同步摸态框开始 --%>
    <div class="modal fade" id="knowledge_syn" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">同步
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    您确定要同步吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="knowledge_syn_go">同步</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 发布摸态框结束 --%>

    <%-- 用户重置密码模态框开始 --%>
    <div class="modal fade" id="resetpassword_modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="resetpwd_form" method="post">
                    <input type="hidden" id="id" name="id">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">重置密码
                            <small></small>
                        </h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="form-group height-div">
                            <input type="password" class="form-control oldpassword" placeholder="请填写原始密码" id="oldpassword"
                                   name="oldpassword" required/>
                        </div>
                        <br>
                        <div class="form-group height-div" style="height: 50px;">
                            <input type="password" class="form-control newpassword" placeholder="请填写新密码" id="newpassword"
                                   name="newpassword" required/>
                        </div>
                        <br>
                        <div class="form-group height-div" style="height: 50px;">
                            <input type="password" class="form-control qnewpassword" placeholder="确认密码" id="qnewpassword"
                                   name="qnewpassword" equalTo="#newpassword" required/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="resetpassword_modal_btn_false"
                                data-dismiss="modal">取消
                        </button>
                        <button type="button" class="btn btn-danger" id="resetpassword_modal_btn_true">重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%-- 用户重置密码模态框开始结束 --%>

    <%-- 角色选择框 --%>
    <div class="modal fade" id="role-attribute-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="role-attribute-form" method="post">
                    <input type="hidden" id="role-id" name="id">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">选择角色
                            <small></small>
                        </h4>
                    </div>
                    <div class="modal-body text-left" style="float: left;z-index: 999;">
                        <span class="page-list2">
                            <span id="attribute-span" class="btn-group dropdown" style="width: 140px;">
                                <button class="btn btn-success  btn-outline dropdown-toggle"
                                        id="attribute-button" type="button"
                                        data-toggle="dropdown" style="width: 140px">
                                    <span class="role-attribute-value" name="role-name"
                                          id="role-name-attribute">请选择</span>
                                    <span id="logo" class="caret"/>
                                </button>
                                <ul class="dropdown-menu role-attribute-value" role="menu" id="role-name">

                                </ul>
                            </span>
                        </span>
                    </div>
                    <div class="modal-body text-center">
                        <textarea class="form-control" id="remark"
                                  style="min-height: 100px;resize: none;width: 70%;"
                                  name="remark" placeholder="该功能用于修改会员的访问权限。"
                                  readonly></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="role-attribute-modal-btn-false"
                                data-dismiss="modal">取消
                        </button>
                        <button type="button" class="btn btn-danger" id="role-attribute-modal-btn-true">更改</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%-- 角色选择框 --%>

    <%-- 审核--%>
    <div class="modal fade" id="review-examine-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">审核
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    您确定要审核吗？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="review-examine-btn-true">确定</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 审核--%>

    <%-- 机构页面获取会员用户模态框开始 --%>
    <div class="modal fade" id="member_modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width: 825px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择机构所属用户
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body"  style="height: 400px;width: 380px;float: left;">
                    <table id="member_table"></table>
                </div>
                <div style="float: left;margin-top: 160px">
                    <button  id="btn2right" style="margin-bottom: 10px"><i class="glyphicon glyphicon-chevron-right"></i></button><br>
                    <button  id="btn2left"><i class="glyphicon glyphicon-chevron-left"></i></button>
                </div>
                <div class="modal-body" id="adminList" style="height: 400px;width: 380px;float: right;margin-right: 20px;padding: 21px 0 0 0;">
                    <table id="admin_table"></table>
                </div>
                <div class="modal-footer" style="clear: both">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="member-delete">删除</button>
                    <button type="button" class="btn btn-danger" id="member-btn-true">添加</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 机构页面获取会员用户模态框结束 --%>

    <%-- 会员详细信息开始 --%>
    <div class="modal fade" id="member-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">详细资料
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    <div class="form-group height-div" style="color: #828482;" id="member-info-show"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 会员详细信息结束 --%>

    <%--后台查看反馈信息--%>
    <div class="modal fade in" id="feedback_modal" aria-hidden="true" aria-labelledby="exampleModalTabs" role="dialog"
         tabindex="-1" style="display: none; padding-right: 21px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="h-review" id="feedback_info">
                        <h6>用户反馈：</h6>
                        <div readonly="readonly" name="" id="feedbackinfo-show"></div>
                        <h6>文件：</h6>
                        <a style="cursor:pointer"; target="_blank" class="" id="feedback-file"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%-- 后台查看反馈信息 --%>

    <%--查看上传进度--%>
    <div class="modal fade in" id="progress_upload" aria-hidden="true" aria-labelledby="exampleModalTabs" role="dialog"
         tabindex="-1" style="display: none; padding-right: 21px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                            <span>上传进度：</span>
                            <div class="progress progress-lg">
                                <div class="progress-bar progress-bar-info progress-bar-striped" id="progress_upload_scroll" role="progressbar">
                                    <span class="sr-only" id="progress_upload_info"></span>
                                </div>
                            </div>
                    </div>

        </div>
    </div>
    </div>
         <%-- 查看上传进度信息 --%>
    <div class="modal fade" id="upload_information" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">
                        <small>提示信息</small>
                    </h4>
                    <div style="text-align: center">
                   <span id="upload_information_show"></span>
                    <span id="upload_information_complete_show"></span>
                    </div>
                </div>

                <div class="modal-footer">
                    <button onclick="refreshPage()" id="ontology_upload_button" type="button" class="btn btn-default" data-dismiss="modal">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <%-- 词库编辑，批量删除 --%>
    <div class="modal fade" id="checkedTreeId-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">
                        <small>选中的主题词将要删除</small>
                    </h4>
                </div>
                <div id="selectedNodeName" class="modal-body text-danger text-center">
                    您确定要删除吗
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button onclick="deleteSelectedNodes()" type="button" class="btn btn-danger" id="tree-delete">删除</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 词库增加词 --%>
    <div class="modal fade" id="thesaurus-add-modal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">
                        <small>从当前位置新建主题词</small>
                    </h4>
                </div>
                <form action="" method="post" class="parent">
                <div  class="modal-body text-danger text-center">

                        <p>
                            主题词：<input style="" type="text" name="word" value="">
                            <input class="mainWord" style="display: none" type="text" name="parent" value="">
                        </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button onclick="vm.addWord(this)" type="button" class="btn btn-danger" id="thesaurus-addWord">增加</button>
                </div>
                </form>
            </div>
        </div>
    </div>
    <%-- 词库增加词结束 --%>

    <%-- ckm同步 --%>
    <div class="modal fade" id="sys_ckm_model" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">任务
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    <ul id="syn-ckm">
                        <span style="color: #f92672">确定同步词库到ckm吗？</span>
                    </ul>
                </div>
                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button onclick="vm.synCkm()" type="button" class="btn btn-danger" id="ckm-modal-btn-true">同步</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 查看上传进度信息 --%>
    <div class="modal fade" id="syn-ckm-status" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">
                        <small>提示信息</small>
                    </h4>
                   <span id="ckm-result-info">请稍后，任务正在执行...</span>
                </div>

                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>
    <%-- ckm同步结束 --%>
    <%-- 导出词库 --%>
    <div class="modal fade" id="export_thesaurus_model" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">导出
                        <small></small>
                    </h4>
                </div>
                <div class="modal-body text-danger text-center">
                    <ul>
                        <span style="color: #f92672">确定要导出吗？</span>
                    </ul>
                </div>
                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button onclick="vm.downloadThesaurus(1)" type="button" class="btn btn-danger">导出</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 查看导出进度信息 --%>
    <div class="modal fade" id="export_thesaurus_status" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">
                        <small>提示信息</small>
                    </h4>
                    <span id="download-result-info">请稍后，正在处理数据...</span>
                </div>

                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>
    <%-- 导出词库结束 --%>

    <%--参考文献处理--%>
    <div class="modal fade" id="reference-documentation-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document" style="width:70%;height: 700px;">
            <div class="modal-content" >
                <div class="widget widget-shadow padding-bottom-5">
                    <div class="widget-content margin-10">
                        <div class="form-group" id="book_table_toolbars" role="group">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="reference-documentation-modal-title">参考文献处理
                                    <small></small>
                                </h4>
                            </div>
                            <div style="position:fixed; position:fixed;top:65px;">
                                <div class="checkbox-custom checkbox-primary">
                                    <input type="checkbox" id="inputUnchecked" name="ckChemical" onclick="ckChange()" value="化工出版社" />
                                    <label for="inputUnchecked"><strong>社内资源</strong></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="ckISBN" onclick="ckChange()" value="无" id="ckISBN"/>
                                    <label for="ckISBN"><strong>无ISBN</strong></label>
                                </div>
                            </div>
                            <table class="editable-table table table-striped" id="documentation_table"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--参考文献处理结束--%>