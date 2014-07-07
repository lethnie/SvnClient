<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 07.07.14
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SVN Client</title>
    <link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <meta charset="utf-8">

    <style type="text/css">
        body {
            padding-top: 40px;
            background-color: #eee;
        }

        .div-area {
            position:relative;
            left:-50%;
            min-width: 920px;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .col-md-6 {
            background-color: rgba(247,247,247,0.8);
            border: 1px solid #e5e5e5;
            border-radius: 10px;
        }

        .table-striped {
            margin: 10px;
        }
    </style>
</head>
<body>

<%@include file='navbar.html' %>

<div style="position:fixed; left:50%; padding-top: 60px;">
    <div class="div-area">
        <b style="padding-left: 30px;"><a href="#">Rep1/lala</a></b>
    </div>

    <div class="col-md-6 div-area" align="center">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Username</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</div>

</body>
</html>