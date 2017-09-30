<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var applicationContextPath = '${pageContext.request.contextPath}';
</script>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/pages/common/header.jsp"/>
<jsp:include page="/WEB-INF/pages/common/menu.jsp"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            豆包网财务系统
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 豆包网财务系统</a></li>
            <li class="active">开票列表</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <!-- /.box -->

                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">开票列表</h3>
                    </div>
                    <div class="box-body">
                        <div class="row">
                            <form name="searchForm">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="chargeEntityName"
                                               placeholder="结算主体名称">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                            <input type="text" class="form-control pull-right" id="receiveDate"
                                                   placeholder="请选择开票日期">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input type="button" class="btn btn-block btn-primary" id="okBtn" value="搜索"/>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input type="reset" class="btn btn-block btn-primary" id="clearBtn" value="重置" />
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input type="button" class="btn btn-block btn-primary" id="exportBtn"
                                               value="导出"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- /.box-header -->
                    <div class="box-body">
                        <table id="example" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>开票流水号</th>
                                <th>结算实体</th>
                                <th>开票金额</th>
                                <th>开票日期</th>
                                <th>张数</th>
                                <th>最后更新人</th>
                                <th>最后更新时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<jsp:include page="/WEB-INF/pages/common/footer.jsp"/>
<script type="text/javascript" charset="utf-8"
        src="${pageContext.request.contextPath}/js/billing/billingList.js"></script>