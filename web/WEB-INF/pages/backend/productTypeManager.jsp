<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yaung
  Date: 2024/4/19
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>backend</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstarp/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/nmms.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/bootstarp/js/bootstrap.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/js/userSetting.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js" type="text/javascript"
            charset="utf-8"></script>
    <script>
        $(function () {
            $("#productTypePage").bootstrapPaginator({
                bootstrapMajorVersion: 3,
                currentPage: "${typePage.pageNum == 0 ? 1 : typePage.pageNum}",
                totalPages: "${typePage.pages== 0 ? 1 : typePage.pages}",
                size: "normal",
                alignment: "center",
                pageUrl: function (type, page, current) {
                    console.log("${pageContext.request.contextPath}/type/findAll.do?pageNo=" + page);
                    return "${pageContext.request.contextPath}/type/findAll.do?pageNo=" + page;
                }
            });
            $(".addProductType").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/type/addType.do",
                    data: {"name": $("#productTypeName").val()},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/type/findAll.do?pageNo=${typePage.pageNum == 0 ? 1 : typePage.pageNum}";
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                });
            });
            $(".updateProType").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/type/modifyType.do",
                    data: {"id": $("#proTypeNum").val(), "name": $("#proTypeName").val()},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode === "2001") {
                            location.href = "${pageContext.request.contextPath}/type/findAll.do?pageNo=${typePage.pageNum == 0 ? 1 : typePage.pageNum}";
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                });
            });
            $(".doProTypeDisable").click(function () {
                let typeId = $(this).closest("tr").find("td:eq(0)").text();
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/type/modifyStatusType0.do",
                    data: {"id": typeId},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode === "2001") {
                            location.reload();
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                });
            });
            $(".doProDisable").click(function () {
                let typeId = $(this).closest("tr").find("td:eq(0)").text();
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/type/modifyStatusType1.do",
                    data: {"id": typeId},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode === "2001") {
                            location.reload();
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                });
            });

        });
    </script>
    <script>
        $(function () {
            $(".doProTypeModify").click(function () {
                let $row = $(this).closest("tr");
                let id = $row.find("td:eq(0)").text();
                let name = $row.find("td:eq(1)").text();
                $("#proTypeNum").val(id);
                $("#proTypeName").val(name);
                $("#proTypeNum").prop('readonly', true);
                $('#myProductType').modal('show');
            });
        });
    </script>


</head>

<body>
<div class="panel panel-default" id="userSet">
    <div class="panel-heading">
        <h3 class="panel-title">商品类型管理&nbsp;&nbsp;&nbsp;<span id="errorMsg"></span></h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加商品类型" class="btn btn-primary" id="doAddProTpye">
        <div class="modal fade" tabindex="-1" id="ProductType">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加商品类型信息</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="productTypeName" class="col-sm-4 control-label">类型名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="productTypeName">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addProductType">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="show-list">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">编号</th>
                    <th class="text-center">类型名称</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${typePage.list}" var="type">
                    <tr>
                        <td>${type.id}</td>
                        <td>${type.name}</td>
                        <td>
                            <c:if test="${type.status==1}">启用</c:if>
                            <c:if test="${type.status==0}">禁用</c:if>
                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doProTypeModify" value="修改">
                            <c:if test="${type.status==1}">
                                <input type="button" class="btn btn-danger btn-sm doProTypeDisable" value="禁用">
                            </c:if>
                            <c:if test="${type.status==0}">
                                <input type="button" class="btn btn-success btn-sm doProDisable" value="启用">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="productTypePage"></ul>
        </div>
        <div class="modal fade" tabindex="-1" id="myProductType">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">商品修改</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="proTypeNum" class="col-sm-4 control-label">编号：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="proTypeNum">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="proTypeName" class="col-sm-4 control-label">类型名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="proTypeName">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-warning updateProType">修改</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>