<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="subNavBox" style="border-top: 1px solid #ddd;">
    <div class="subNavBox-infor">

        <%--用户管理--%>
        <c:forEach items="${menus}" var="menu">
            <div class="subNav"><a href="javascript:void(0)"><i class="iconfont">&#xe601;</i>${menu.menuname}</a></div>

            <ul class="navContent">
                <c:forEach items="${menu.children}" var="cmenu">
                    <li>
                        <a href="javascript:clickMenu('<%=request.getContextPath()%>/${cmenu.url}','menu${cmenu.id}')"
                           id="menu${cmenu.id}">${cmenu.menuname}</a>
                    </li>

                </c:forEach>
            </ul>

        </c:forEach>

    </div>
    <div class="subNavBox-packup"><img src="<%=request.getContextPath()%>/resources/images/hide.png"></div>
    <div class="subNavBox-men"><img src="<%=request.getContextPath()%>/resources/images/show.png"></div>
</div>
