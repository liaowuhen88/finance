<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>豆包网数据后台</title>
    <link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico"/>
    <script>
        window.base = '<%=request.getContextPath()%>';
    </script>
    <!--[if lt IE 9]>
    <script src="<%=request.getContextPath()%>/resources/js/html5shiv.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/respond.min.js"></script>
    <![endif]-->
    <jsp:include page="common/commonCss.jsp"/>
</head>
<body>
<header>
    <jsp:include page="casHead.jsp"/>
</header>
<div class="container-fluid">
    <div class="row-fluid kef-fluid">
        <jsp:include page="casMenu.jsp"/>
        <div class="content" id="container">
        </div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
<jsp:include page="common/commonJs.jsp"/>

</html>