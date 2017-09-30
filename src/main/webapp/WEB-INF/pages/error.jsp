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
            错误提示
        </h1>
    </section>

    <!-- Main content -->
    <section class="content">

        <div class="error-page">
            <h2 class="headline text-red">${message}</h2>
        </div>
        <!-- /.error-page -->

    </section>
    <!-- /.content -->
</div>
<jsp:include page="/WEB-INF/pages/common/footer.jsp"/>
<script type="text/javascript" charset="utf-8"
        src="${pageContext.request.contextPath}/js/realCollection/realCollectionList.js"></script>