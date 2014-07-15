<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 10.07.14
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>New repository</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <meta charset="utf-8">

    <style type="text/css">
        body {
            padding-top: 40px;
            background-color: #eee;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            background-color: rgba(250,250,250,0.8);
            border: 1px solid #e5e5e5;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<%@include file='navbar.html' %>

<div class="container" align="center" style="padding-top: 40px;">

    <div class="form-signin">
        <form:form role="form" method="post"  action="add.html" commandName="rep">
            <h2 class="form-signin-heading">Repository</h2>
            <br>
            <form:input type="text" id="id_name" path="repository" class="form-control" style="height: auto; font-size: 16px; " placeholder="Name" />
            <br>
            <form:input type="text" id="id_url" path="url" class="form-control" style="height: auto; font-size: 16px; " placeholder="URL" />
            <br>
            <form:input type="text" id="id_login" path="login" class="form-control" style="height: auto; font-size: 16px; " placeholder="Login" />
            <br>
            <form:input type="password" id="id_password" path="password" class="form-control" style="height: auto; font-size: 16px; " placeholder="Password" />
            <br>
            <font color="FF0000">
                    ${message}
            </font>
            <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>

        </form:form>
    </div>

</div>

</body>
</html>