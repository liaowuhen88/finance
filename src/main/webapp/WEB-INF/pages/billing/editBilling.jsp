<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li class="active">编辑发票</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <!-- form start -->
        <form class="form-horizontal" data-toggle="validator" enctype="application/x-www-form-urlencoded"
              id="editBillingForm" method="post">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">发票信息</h3>
                        </div>
                        <div class="box box-body">
                            <div class="form-group">
                                <input type="hidden" id="id" name="id" value="${billing.id}"/>
                                <label for="billingSerialNumber" class="col-sm-2 control-label">发票流水*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="billingSerialNumber"
                                           name="billingSerialNumber" value="${billing.billingSerialNumber}"
                                           placeholder="发票流水">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="billingNumber" class="col-sm-2 control-label">发票号*:</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="billingNumber"
                                           name="billingNumber" value="${billing.billingNumber}" placeholder="发票号">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="billingMoney" class="col-sm-2 control-label">发票金额*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="billingMoney"
                                           name="billingMoney" value="${billing.billingMoney}" placeholder="发票金额">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="billType" class="col-sm-2 control-label">发票类型:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="billType" name="billType" placeholder="发票类型">
                                        <option value="0" <c:if
                                                test="${billing.billType eq 0}"> selected </c:if> ></option>
                                        <option value="1" <c:if test="${billing.billType eq 1}"> selected </c:if> >
                                            增值税普通发票
                                        </option>
                                        <option value="2" <c:if test="${billing.billType eq 2}"> selected </c:if> >
                                            增值税专用发票
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="billProjectType" class="col-sm-2 control-label">发票项目:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="billProjectType" name="billProjectType"
                                            placeholder="发票项目">
                                        <option value="0" <c:if
                                                test="${billing.billProjectType eq 0}"> selected </c:if> ></option>
                                        <option value="1" <c:if
                                                test="${billing.billProjectType eq 1}"> selected </c:if>>服务费
                                        </option>
                                        <option value="2" <c:if
                                                test="${billing.billProjectType eq 2}"> selected </c:if> >技术服务费
                                        </option>
                                        <option value="3" <c:if
                                                test="${billing.billProjectType eq 3}"> selected </c:if> >健康服务费
                                        </option>
                                        <option value="4" <c:if
                                                test="${billing.billProjectType eq 4}"> selected </c:if> >健康咨询服务费
                                        </option>
                                        <option value="5" <c:if
                                                test="${billing.billProjectType eq 5}"> selected </c:if> >咨询服务费
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="chargeEntityId" class="col-sm-2 control-label">结算主体*:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" data-placeholder="请选择结算主体"
                                            id="chargeEntityId" name="chargeEntityId">
                                        <option selected="selected"
                                                value="${billing.chargeEntityId}">${billing.chargeEntityName}</option>
                                    </select>
                                    <input type="hidden" id="chargeEntityName" name="chargeEntityName"
                                           value="${billing.chargeEntityName}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="billingDate" class="col-sm-2 control-label">开票日期*:</label>
                                <div class="col-sm-10">
                                    <input type="date" required class="form-control" id="billingDate"
                                           name="billingDate" value="${billing.billingDate}" placeholder="开票日期">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="applicant" class="col-sm-2 control-label">申请人*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="applicant"
                                           name="applicant" value="${billing.applicant}" placeholder="申请人">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="billingImgCount" class="col-sm-2 control-label">张数*:</label>
                                <div class="col-sm-10">
                                    <input type="text" required class="form-control" id="billingImgCount"
                                           name="billingImgCount" value="${billing.billingImgCount}" placeholder="张数">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remark" class="col-sm-2 control-label">备注:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="remark"
                                           name="remark" value="${billing.remark}" placeholder="备注">
                                </div>
                            </div>
                        </div>
                        <div class="box-footer">
                            <input type="hidden" name="isDel" value="${billing.isDel}"/>
                            <input type="hidden" name="lastUpdater" value="${billing.lastUpdater}"/>
                            <input type="hidden" name="lastUpdateTime" value="${billing.lastUpdateTime}"/>
                            <input type="button" id="returnBtn" class="btn btn-cancel" value="取消" />
                            <button type="submit" class="btn btn-info pull-right">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">发票影像</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择图片:</label>
                            <div class="col-sm-10">
                                <input type="file" id="billingImgs" multiple="multiple" name="file"
                                       data-show-upload="false" data-show-caption="true"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<script>
    var chargeEntityId = '${billing.chargeEntityId}';
    var chargeEntityName = '${billing.chargeEntityName}';
</script>
<jsp:include page="/WEB-INF/pages/common/footer.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/billing/editBilling.js"></script>