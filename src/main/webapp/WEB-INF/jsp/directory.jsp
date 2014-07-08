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
                        Callback(result);
                    },
                    error: function() {
                        alert("Ajax request broken");
                    }
                });
            }

            /*function getText(text) {
                var htmls = [];
                var lines = text.split(/\n/);
                var tmpDiv = jQuery(document.createElement('div'));
                for (var i = 0 ; i < lines.length ; i++) {
                    htmls.push(tmpDiv.text(lines[i]).html());
                }
                return htmls.join("<br>");
            }*/

            function Callback(result) {
                $('.div-data').empty();
                var jsonData = JSON.parse(result);
                if (jsonData.type == "file") {
                    var count = jsonData.count;
                    $('.div-data').append($("<div></div>")
                            .attr("id", "id_line")
                            //.addClass("lines-area")
                            .append($("<code></code>")
                                    .attr("id", "id_code")
                                    .attr("style", "background-color: rgba(243,243,243,0.8);")));
                    for (i = 1; i <= count; i++) {
                        $('<font color="CCCCCC">' + i + ' </font>').appendTo('#id_code');
                        $('<br>').appendTo('#id_code');
                    }
                    $('.div-data').append($("<div></div>")
                            .attr("id", "id_text")
                            .append($("<code></code>")
                                    .attr("id", "id_code")
                                    .attr("style", "background-color: rgba(247,247,247,0.8);")
                                    .html(jsonData.file)));
                    $("#id_line").addClass("lines-area");
                    $("#id_text").addClass("text-area");
                    alert("lalal");
                    return;
                }
                if (jsonData.type == "dir") {
                    for (var i = 0; i < jsonData.files.length; i++) {
                        alert(jsonData.files[i].name);
                    }
                    return;
                }
                alert(":(");
                return;
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

        .text-area {
            background-color: rgba(247,247,247,0.8);
            border: 1px solid #e5e5e5;
            border-bottom-right-radius: 10px;
            border-top-right-radius: 10px;
            margin-top: 10px;
            margin-left: -5px;
            position:relative;
            left: 20%;
            width: 55%;
            padding-top: 10px;
            padding-left: 10px;
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
    <div class="div-area" id="id_path">
        <p style="padding-left: 30px; display: inline-block;">/</p>
    </div>

    <div class="div-data">
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
</div>

</body>
</html>