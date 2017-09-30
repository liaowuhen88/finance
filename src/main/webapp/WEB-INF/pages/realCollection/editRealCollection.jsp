<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var applicationContextPath = '${pageContext.request.contextPath}';
</script>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/pages/common/header.jsp" />
<jsp:include page="/WEB-INF/pages/common/menu.jsp" />

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            豆包网财务系统
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 豆包网财务系统</a></li>
            <li class="active">编辑实收</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <!-- form start -->
        <form class="form-horizontal" data-toggle="validator" enctype="application/x-www-form-urlencoded"
              id="editRellCollectionForm" method="post">
            <div class="row">
                <div class="col-md-6">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">付款人信息</h3>
                        </div>
                        <div class="box box-body">
                            <div class="form-group">
                                <label for="payerAccountName" class="col-sm-2 control-label">户名*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="payerAccountName"
                                           name="payerAccountName" value="${realCollection.payerAccountName}"
                                           placeholder="付款人户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="payerAccountNumber" class="col-sm-2 control-label">账号*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="payerAccountNumber"
                                           name="payerAccountNumber" value="${realCollection.payerAccountNumber}"
                                           placeholder="付款人账号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="payerSourceAccount" class="col-sm-2 control-label">开户行:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="payerSourceAccount"
                                           name="payerSourceAccount" value="${realCollection.payerSourceAccount}"
                                           placeholder="付款人开户行">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">收款人信息</h3>
                        </div>
                        <div class="box box-body">
                            <div class="form-group">
                                <label for="payeeAccountName" class="col-sm-2 control-label">户名*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="payeeAccountName"
                                           name="payeeAccountName" value="${realCollection.payeeAccountName}"
                                           placeholder="收款人户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="payeeAccountNumber" class="col-sm-2 control-label">账号*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="payeeAccountNumber"
                                           name="payeeAccountNumber" value="${realCollection.payeeAccountNumber}"
                                           placeholder="收款人账号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="payeeSourceAccount" class="col-sm-2 control-label">开户行*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="payeeSourceAccount"
                                           name="payeeSourceAccount" value="${realCollection.payeeSourceAccount}"
                                           placeholder="收款人开户行">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">收款信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="form-group">
                                <input type="hidden" name="id" value="${realCollection.id}"/>
                                <input type="hidden" name="isDel" value="${realCollection.isDel}" />
                                <label for="serialNumber" class="col-sm-2 control-label">流水号*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="serialNumber"
                                           name="serialNumber" value="${realCollection.serialNumber}" placeholder="流水号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="money" class="col-sm-2 control-label">金额*:</label>

                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="money" name="money"
                                           value="${realCollection.money}" placeholder="金额">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="receiveDate" class="col-sm-2 control-label">回款日期*:</label>
                                <div class="col-sm-10">
                                    <input type="date" required pattern="YYYY-MM-DD" class="form-control"
                                           id="receiveDate" name="receiveDate" value="${realCollection.receiveDate}"
                                           placeholder="回款日期">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="chargeEntityId" class="col-sm-2 control-label">结算主体*:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" required id="chargeEntityId" name="chargeEntityId">
                                        <option selected="selected"
                                                value="${realCollection.chargeEntityId}">${realCollection.chargeEntityName}</option>
                                    </select>
                                    <input type="hidden" id="chargeEntityName"
                                           value="${realCollection.chargeEntityName}" name="chargeEntityName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="summary" class="col-sm-2 control-label">摘要:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="summary" name="summary"
                                           value="${realCollection.summary}" placeholder="摘要">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="usage" class="col-sm-2 control-label">用途:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="usage" name="usage"
                                           value="${realCollection.usage}" placeholder="用途">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remark" class="col-sm-2 control-label">备注:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="remark" name="remark"
                                           value="${realCollection.remark}" placeholder="备注">
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <input type="button" id="cancelBtn" class="btn btn-info pull-left" value="取消" />
                            <button type="submit" name="submitBtn" class="btn btn-info pull-right">保存</button>
                        </div>
                        <!-- /.box-footer -->
                    </div>
                </div>
            </div>
        </form>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<jsp:include page="/WEB-INF/pages/common/footer.jsp" />
<script>
    var chargeEntityId = '${realCollection.chargeEntityId}';
    var chargeEntityName = '${realCollection.chargeEntityName}';
</script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/realCollection/editRealCollection.js"></script>
