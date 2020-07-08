<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.bocharova.se.util.FieldConst" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted"><br/></h3>
        <h4 class="text-muted">PROJECT MANAGEMENT</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Data begin</th>
                            <th>Data End</th>
                            <th>Status</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="i" value="0"/>
                        <c:forEach var="project" items="${tasks}">
                            <tr>
                                <td>
                                        ${i=i+1}
                                </td>
                                <td>${project.getName()}
                                </td>
                                <td>${project.getDescription()}
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
                                                    value="${project.getDateBegin()}"/>
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
                                                    value="${project.getDateEnd()}"/>
                                </td>
                                <td>${project.getStatus()}
                                </td>
                                <td>
                                    <button class="btn btn-primary btn-xs"
                                            onclick="postToUrl(
                                                    '${pageContext.request.contextPath}/task/list',
                                                    {'<%=FieldConst.PROJECT_ID%>':'${project.getId( )}'},
                                                    'GET');">
                                        TASKS
                                    </button>
                                </td>
                                <td>
                                    <button class="btn btn-primary btn-xs"
                                            onclick="postToUrl(
                                                    '${pageContext.request.contextPath}/project/edit',
                                                    {'<%=FieldConst.PROJECT_ID%>':'${project.getId( )}'},
                                                    'GET');">
                                        EDIT
                                    </button>
                                </td>
                                <td>
                                    <button class="btn btn-danger btn-xs"
                                            onclick="postToUrl(
                                                    '${pageContext.request.contextPath}/project/delete',
                                                    {'<%=FieldConst.PROJECT_ID%>':'${project.getId( )}'},
                                                    'POST');">
                                        DELETE
                                    </button>
                                </td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <button class="btn btn-success"
                        onclick="postToUrl(
                                '${pageContext.request.contextPath}/project/create',
                                '',
                                'POST');">
                    CREATE
                </button>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
</body>
</html>