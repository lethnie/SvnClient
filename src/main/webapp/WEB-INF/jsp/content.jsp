<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 07.07.14
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            height: 100%;
            overflow-y : scroll;
        }

        .div-area {
            position:relative;
            left: 20%;
            width: 60%;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .text-area {
            background-color: rgba(247,247,247,0.8);
            border: 1px solid #e5e5e5;
            border-bottom-right-radius: 10px;
            border-top-right-radius: 10px;
            margin-top: 10px;
            margin-left: -5px;
            position:relative;
            left: 20%;
            padding-top: 10px;
            padding-left: 5px;
            padding-bottom: 10px;
            display:inline-block;
        }

        .lines-area {
            background-color: rgba(243,243,243,0.8);
            border: 1px solid #e5e5e5;
            border-bottom-left-radius: 10px;
            border-top-left-radius: 10px;
            margin-top: 10px;
            margin-left: 10px;
            position:relative;
            left: 20%;
            padding-top: 10px;
            padding-bottom: 10px;
            text-align: right;
            display:inline-block;
            width: 30px;
        }
    </style>
</head>
<body>

<%@include file='navbar.html' %>
<div style=" padding-top: 60px; padding-bottom: 10px;">
    <div class="div-area">
        <b style="padding-left: 30px;"><a href="#">Rep1/lala</a></b>
    </div>

    <div class="lines-area">
        <code style="background-color: rgba(243,243,243,0.8);">
                <font  color="CCCCCC">
                <c:forEach begin="1" end="${count}" var="val">
                    <c:out value="${val}"/>
                    <br>
                </c:forEach>
                </font>
        </code>
    </div>
    <div class="text-area">
            <code style="background-color: rgba(247,247,247,0.8);">
                ${file}
            </code>
    </div>
</div>

</body>
</html>