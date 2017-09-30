<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--用户信息-->
<div id="header" class="header">
    <div class="logo"><a href="<%=request.getContextPath()%>/index"><img
            src="<%=request.getContextPath()%>/resources/images/logo.png" alt="" width="100"/></a></div>
    <a class="sign-out" href="javascript:void(0)" onclick="exit()" title="退出"></a>
    <a class="sign-out" href="javascript:void(0)" onclick="mainMenu()" title="退回主菜单"
       style="background-position: -125px -125px;margin: 22px 12px 0 10px;"></a>

    <div class="user_info">您好，${userName}</div>
</div>
<script>
    function exit() {
        window.location.href = "<%=request.getContextPath()%>/gatewayLog/logout";
    }

    function mainMenu() {
        // constant 表中key为 GatewayUrl
        window.location.href = "${GatewayUrl}/mainMenu";

    }
</script>