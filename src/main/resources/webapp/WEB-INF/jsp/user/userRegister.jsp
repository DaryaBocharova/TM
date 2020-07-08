<%@ page import="ru.bocharova.se.util.FieldConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 09.06.2019
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted"><br/></h3>
        <h4 class="text-muted">REGISTRATION</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/user/register">

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputLogin">Login</label>
                            <input placeholder="Login" type="text" class="form-control" name="<%=FieldConst.LOGIN%>"
                                   id="inputLogin">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputPassword">Password</label>
                            <input placeholder="Password" type="password" class="form-control"
                                   name="<%=FieldConst.PASSWORD%>"
                                   id="inputPassword">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputName">Name</label>
                            <input placeholder="Name" type="text" class="form-control" name="<%=FieldConst.NAME%>"
                                   id="inputName">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Description</label>
                    <textarea placeholder="Description" class="form-control" name="<%=FieldConst.DESCRIPTION%>"
                              id="inputDescription"
                              rows="10"></textarea>
                </div>
                <button type="submit" class="btn btn-success">REGISTER</button>
            </form>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
</body>
</html>