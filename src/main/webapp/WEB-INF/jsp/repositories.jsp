<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 10.07.14
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Repositories</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <script>

        $(document).on('click', "img[name=delete]", function() {
            if (confirm('Are you sure you want to delete this repository?')) {
                var filepath = $(this).attr("id").trim();
                var link = "/SvnClient/delete.html?filepath=" + filepath;
                window.location.href = link;
            }
        });
    </script>
    <meta charset="utf-8">

    <style type="text/css">
        .div-area {
            position:relative;
            left: 20%;
            width: 60%;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        body {
            padding-top: 40px;
            background-color: #eee;
            height: 100%;
            overflow-y : scroll;
        }

        .col-md-6 {
            background-color: rgba(247,247,247,0.8);
            border: 1px solid #e5e5e5;
            border-radius: 10px;
        }

        .table-striped {
            margin: 10px;
        }

        .text-link:hover {
            color: blue;
        }
    </style>
</head>
<body>

<%@include file='navbar.html' %>

<div style="padding-top: 60px; padding-bottom: 10px;">

    <div class="div-data">

        <div class="col-md-6 div-area">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>name</th>
                    <th>author</th>
                    <th>delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${files}" var="file">
                    <tr name="row">
                        <td><img src="<%=request.getContextPath() %>/images/dir.png" width="20px" height="20px" style="display:inline-block;"/>
                            <a style="display:inline-block;" href="/SvnClient/svn/${fn:escapeXml(file.user.name)}/${fn:escapeXml(file.repository)}.html">${fn:escapeXml(file.repository)}</a>
                        </td>
                        <td><p>${fn:escapeXml(file.user.name)}</p></td>
                        <td><img src="<%=request.getContextPath() %>/images/delete.png" id="${fn:escapeXml(file.repository)}" name="delete" width="20px" height="20px" style="display:inline-block;"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <font color="FF0000">
                ${error_message}
            </font>
        </div>

    </div>
</div>

</body>
</html>