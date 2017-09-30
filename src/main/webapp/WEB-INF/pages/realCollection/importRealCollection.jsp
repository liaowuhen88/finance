<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var applicationContextPath = '${pageContext.request.contextPath}';
</script>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/pages/common/header.jsp"/>
<jsp:include page="/WEB-INF/pages/common/menu.jsp"/>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            豆包网财务系统
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/realCollection/list"><i class="fa fa-dashboard"></i> 豆包网财务系统</a>
            </li>
            <li class="active">实收列表导入</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <!-- /.box -->
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">导入实收</h3>
                    </div>
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-10">
                                <div class="form-group">
                                    <input type="file" class="file-loading" id="importFile" name="file"
                                           value="导入实收"/>
                                </div>
                            </div>
                        </div>
                    </div>
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
<script>

    $(document).ready(function () {

        $("#importFile").fileinput({
            language: 'zh',
            allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀,
            enctype: 'multipart/form-data',
            uploadAsync: true,
            showRemove: false,
            uploadUrl: "${pageContext.request.contextPath}/realCollection/import",
            uploadAsync: true,
            maxFileCount: 1
        }).on('fileuploaded', function (event, data, id, index) {
            if (!data.response.success) {
                var dialog = bootbox.dialog({
                    title: data.response.message,
                    message: '<p><i class="fa fa-spin fa-spinner"></i> Loading...</p>'
                });
                var errorMsg = data.response.data;
                var reg = new RegExp("\r\n", "g");//g,表示全部替换。
                errorMsg = errorMsg.replace(reg, '<br/>');
                dialog.init(function () {
                    setTimeout(function () {
                        dialog.find('.bootbox-body').html(errorMsg);
                    }, 300);
                });
                return;
            }
            bootbox.alert("导入成功");
        });

    });


</script>