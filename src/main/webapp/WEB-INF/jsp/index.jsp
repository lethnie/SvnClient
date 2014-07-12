<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <script language="javascript">
        function addUser() {
            var name = $('#id_name').val();
            var pass = $('#id_pass').val();
            $.ajax({
                type: 'POST',
                url: '/SvnClient/add_user.html',
                dataType: "text",
                async: false,
                data: JSON.stringify({ name: name, pass: pass }),
                contentType: "application/json; charset=utf-8",
                success: function(result) {
                    if (result == "ok") {
                        alert("You've signed up successfully");
                    }
                    else {
                        alert(result);
                    }
                },
                error: function() {
                    alert("Ajax request broken");
                }
            });
        }
    </script>
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

    <div class="form-signin">
    <form role="form" method="post"  action="<c:url value='/j_spring_security_check.html' />">
        <h2 class="form-signin-heading">Please sign in</h2>
        <br>
        <input type="text" name = "j_username" id="id_name" class="form-control" style="height: auto; font-size: 16px; " placeholder="Login" required autofocus>
        <br>
        <input type="password" name="j_password" id="id_pass" class="form-control" style="height: auto; font-size: 16px; " placeholder="Password" required>
        <br>
        <font color="FF0000">
            ${auth_status}
        </font>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

    </form>
    <button class="btn btn-lg btn-success btn-block" onclick="addUser();">Sign up</button>
    <br>
    </div>

</div>
</body>
</html>