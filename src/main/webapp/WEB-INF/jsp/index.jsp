<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
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

<div class="container" align="center">

    <form class="form-signin" role="form" method="post"  action="<c:url value='content.html' />">
        <h2 class="form-signin-heading">Please sign in</h2>
        <br>
        <input type="text" name = "j_username" class="form-control" style="height: auto; font-size: 16px; " placeholder="Login" required autofocus>
        <br>
        <input type="password" name="j_password" class="form-control" style="height: auto; font-size: 16px; " placeholder="Password" required>
        <br>
        <font color="FF0000">
            ${auth_status}
        </font>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <br>
    </form>

</div>
</body>
</html>