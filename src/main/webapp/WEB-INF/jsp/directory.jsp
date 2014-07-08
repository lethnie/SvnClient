<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 07.07.14
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>SVN Client</title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $(document).on('click', ".name", function() {
                var filename = $(this).text();
                if ($("#id_path").children().size() > 1) {
                    $("#id_path").append($("<p></p>")
                            .attr("style", "padding-left: 5px; display: inline-block;")
                            .text("/"));
                }
                $("#id_path").append($("<b></b>")
                        .attr("class", "text-link path")
                        .attr("style", "padding-left: 5px; display: inline-block;")
                        .text(filename));
                getFileData();
            });

            $(document).on('click', ".path", function() {
                $(this).nextAll().remove();
                getFileData();
            });

            function getFileData() {
                var filepath = "";
                $("#id_path").children().each(function() {
                    filepath = filepath.concat($(this).text());
                });
                $.ajax({
                    type: 'POST',
                    url: 'get_data.html',
                    dataType: "text",
                    async: false,
                    data: JSON.stringify({ filepath: filepath }),
                    contentType: "application/json; charset=utf-8",
                    success: function(result) {
                        alert(result);
                    },
                    error: function() {
                        alert("Ajax request broken");
                    }
                });
            }
        });
    </script>
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

        .text-link:hover {
            color: blue;
        }
    </style>
</head>
<body>

<%@include file='navbar.html' %>

<div style="position:fixed; left:50%; padding-top: 60px;">
    <div class="div-area" id="id_path">
        <p style="padding-left: 30px; display: inline-block;">/</p>
    </div>

    <div class="col-md-6 div-area">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>name</th>
                <th>commit</th>
                <th>date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${files}" var="file">
                <tr>
                    <td><img src="<%=request.getContextPath() %>/images/${fn:escapeXml(file.type)}.png" width="20px" height="20px" style="display:inline-block;"/>
                        <p class="name text-link" style="display:inline-block;">${fn:escapeXml(file.name)}</p>
                    </td>
                    <td><p class="message">${fn:escapeXml(file.message)}</p></td>
                    <td><p class="date">${fn:escapeXml(file.date)}</p></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>