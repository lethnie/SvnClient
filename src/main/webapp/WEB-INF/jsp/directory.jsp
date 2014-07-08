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
                $("#id_path").append($("<p></p>")
                        .attr("style", "padding-left: 5px; display: inline-block;")
                        .text("/"));
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

            $(document).on('click', "#id_download", function() {
                alert("download! he-he");
            });

            function getFileData() {
                var filepath = "";
                $("#id_path").children().each(function() {
                    var fpath = $(this).text().trim();
                    filepath = filepath.concat(fpath);
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

            function Callback(result) {
                $('.div-data').empty();
                $("#id_download").remove();
                var jsonData = JSON.parse(result);
                if (jsonData.type == "file") {
                    $('.div-data').append($("<div></div>")
                            .attr("id", "id_text")
                                .append($("<code></code>")
                                        .attr("id", "id_code")
                                        .attr("style", "background-color: rgba(247,247,247,0.8);")
                                        .html(jsonData.file)));
                    $("#id_text").addClass("text-area");
                    $("#id_nav").prepend('<li id="id_download"><a href="">Download</a></li>');
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    //$("#id_line").addClass("lines-area");
                    return;
                }

                if (jsonData.type == "dir") {
                    $('.div-data').append($("<div></div>")
                            .attr("id", "id_div_area")
                            .append($("<table></table>")
                                    .attr("id", "id_table")
                                    .append($("<thead></thead>")
                                            .append($("<tr></tr>")
                                                    .append($("<th></th>")
                                                            .text("name"))
                                                    .append($("<th></th>")
                                                            .text("commit"))
                                                    .append($("<th></th>")
                                                            .text("author"))
                                                    .append($("<th></th>")
                                                            .text("date"))))
                                    .append($("<tbody></tbody>")
                                            .attr("id", "id_tbody")
                                    )));
                    $("#id_div_area").addClass("col-md-6 div-area");
                    $("#id_table").addClass("table table-striped");
                    for (var i = 0; i < jsonData.files.length; i++) {
                        $("#id_tbody").append($("<tr></tr>")
                                .append($("<td></td>")
                                        .html('<img src="<%=request.getContextPath() %>/images/'
                                                + jsonData.files[i].type
                                                + '.png" height="20px" width="20px" style="display:inline-block;">')
                                        .append($("<p></p>")
                                                .attr("name", "id_text_name")
                                                .attr("style", "display:inline-block;")
                                                .text(" " + jsonData.files[i].name)))
                                .append($("<td></td>")
                                        .append($("<p></p>")
                                                .text(jsonData.files[i].message == null ? "" : jsonData.files[i].message)))
                                .append($("<td></td>")
                                        .append($("<p></p>")
                                                .text(jsonData.files[i].author == null ? "" : jsonData.files[i].author)))
                                .append($("<td></td>")
                                        .append($("<p></p>")
                                                .text(jsonData.files[i].date == null ? "" : jsonData.files[i].date))));

                    }
                    $('p[name=id_text_name]').addClass("name text-link");
                    return;
                }
                alert(jsonData.type);
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
            border-bottom-left-radius: 10px;
            border-top-left-radius: 10px;
            border-bottom-right-radius: 10px;
            border-top-right-radius: 10px;
            margin-top: 10px;
            margin-left: -5px;
            position:relative;
            left: 20%;
            width: 60%;
            padding-top: 10px;
            padding-left: 10px;
            padding-bottom: 10px;
            display:inline-block;
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
        <b class="text-link path" style="padding-left: 30px; display: inline-block;">..</b>
    </div>

    <div class="div-data">
        <div class="col-md-6 div-area">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>name</th>
                    <th>commit</th>
                    <th>author</th>
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
                        <td><p class="author">${fn:escapeXml(file.author)}</p></td>
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