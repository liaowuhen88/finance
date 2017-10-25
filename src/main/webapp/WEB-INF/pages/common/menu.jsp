<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="header">导航菜单</li>
            <!-- Optionally, you can add icons to the links -->
            <li class="treeview">
                <a href="#"><i class="fa fa-link"></i> <span>实收款管理</span>
                    <span class="pull-right-container">
                      <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/realCollection/list">
                            <i class="fa fa-link"></i> <span>实收款列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/realCollection/toAdd">
                            <i class="fa fa-link"></i> <span>新增实收</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-link"></i> <span>发票管理</span>
                    <span class="pull-right-container">
                      <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/billing/list">
                            <i class="fa fa-link"></i> <span>发票列表</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/billing/toAdd">
                            <i class="fa fa-link"></i> <span>新增发票</span>
                        </a>
                    </li>
                </ul>
            </li>
            <%--<li class="treeview">--%>
            <%--<a href="#"><i class="fa fa-link"></i> <span>Multilevel</span>--%>
            <%--<span class="pull-right-container">--%>
            <%--<i class="fa fa-angle-left pull-right"></i>--%>
            <%--</span>--%>
            <%--</a>--%>
            <%--<ul class="treeview-menu">--%>
            <%--<li><a href="#">Link in level 2</a></li>--%>
            <%--<li><a href="#">Link in level 2</a></li>--%>
            <%--</ul>--%>
            <%--</li>--%>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>

