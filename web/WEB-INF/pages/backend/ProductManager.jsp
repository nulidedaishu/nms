<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
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


    <style>
        .file {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
            width: 100%;
            text-align: center;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>

    <script>
        $(function () {
            $("#productPage1").bootstrapPaginator({
                bootstrapMajorVersion: 3,
                currentPage: "${productPage.pageNum == 0 ? 1 : productPage.pageNum}",
                totalPages: "${productPage.pages== 0 ? 1 : productPage.pages}",
                size: "normal",
                alignment: "center",
                pageUrl: function (product, page, current) {
                    return "${pageContext.request.contextPath}/product/findAll.do?pageNo=" + page;
                }
            });
            $(".addProduct").click(function () {
                let formData = new FormData($("#addForm")[0]);
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/product/addProduct.do",
                    data: formData,
                    dataType: "json",
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/product/findAll.do?pageNo=${productPage.pageNum == 0 ? 1 : productPage.pageNum}";
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
            $(".updatePro").click(function () {
                let formData = new FormData($("#updateForm")[0]);
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/product/modifyProduct.do",
                    data: formData,
                    dataType: "json",
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/product/findAll.do?pageNo=${productPage.pageNum == 0 ? 1 : productPage.pageNum}";
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
                let productNo = $(this).closest("tr").find("td:eq(0)").text();
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/product/deleteProduct.do",
                    data: {"productNo": productNo},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/product/findAll.do?pageNo=${productPage.pageNum == 0 ? 1 : productPage.pageNum}";
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
        })
    </script>
    <script>
        $(function () {
            $(".doProModify").click(function () {
                let productNo = $(this).closest("tr").find("td:eq(0)").text();
                let productName = $(this).closest("tr").find("td:eq(1)").text();
                let productPrice = $(this).closest("tr").find("td:eq(2)").text();
                let productTypeId = $(this).closest("tr").find("td:eq(3)").attr("data-type-id");
                $("#pro-num").val(productNo);
                $("#pro-name").val(productName);
                $("#pro-price").val(productPrice);
                $("#product-type").val(productTypeId);
                $("#pro-num-hidden").val(productNo);
                $("#pro-num").prop("readonly", true);
                $("#myProduct").modal("show");
            });
        })
    </script>

</head>
<body>
<div class="panel panel-default" id="userPic">
    <div class="panel-heading">
        <h3 class="panel-title">商品管理&nbsp;&nbsp;&nbsp;<span id="errorMsg" style="color: red"></span></h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加商品" class="btn btn-primary" id="doAddPro">
        <!-- 添加商品模态框 -->
        <div class="modal fade" tabindex="-1" id="Product">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加商品</h4>
                    </div>
                    <div class="modal-body text-center">
                        <form id="addForm">
                            <div class="row text-right">
                                <label for="product-name" class="col-sm-4 control-label">商品名称：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="name" id="product-name">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="product-price" class="col-sm-4 control-label">商品价格：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="product-price" name="price">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="file" class="col-sm-4 control-label">商品图片：</label>
                                <div class="col-sm-4">
                                    <a href="javascript:;" class="file">选择文件
                                        <input type="file" name="file" id="file">
                                    </a>
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="typeId" class="col-sm-4 control-label">商品类型：</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="typeId" name="typeId">
                                        <option value="-1">请选择</option>
                                        <c:forEach items="${types}" var="type">
                                            <option value="${type.id}">${type.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addProduct">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>

                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="show-list">
            <!-- 商品列表 -->
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">编号</th>
                    <th class="text-center">商品</th>
                    <th class="text-center">价格</th>
                    <th class="text-center">产品类型</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <!-- 使用JSTL标签库进行动态展示 -->
                <c:forEach items="${productPage.list}" var="product">
                    <tr>
                        <td>${product.productNo}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.productType.name}</td>
                        <td>
                            <c:if test="${product.productType.status==1}">有效</c:if>
                            <c:if test="${product.productType.status==0}">无效</c:if>
                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doProModify" value="修改">
                            <input type="button" class="btn btn-danger btn-sm doProDisable" value="删除">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="productPage1"></ul>
        </div>
        <!-- 修改商品模态框 -->
        <div class="modal fade" tabindex="-1" id="myProduct">
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
                        <!-- 表单 -->
                        <form method="post" id="updateForm">
                            <div class="row text-right">
                                <label for="pro-num" class="col-sm-4 control-label">序号：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="pro-num" name="no">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="pro-name" class="col-sm-4 control-label">商品名称</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="pro-name" name="name">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="pro-price" class="col-sm-4 control-label">商品价格：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="pro-price" name="price">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="product-image" class="col-sm-4 control-label">商品图片：</label>
                                <div class="col-sm-4">
                                    <a href="javascript:;" class="file">选择文件
                                        <input type="file" name="file" id="product-image">
                                    </a>
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="product-type" class="col-sm-4 control-label">商品类型：</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="product-type" name="typeId">
                                        <option value="-1">请选择</option>
                                        <c:forEach items="${types}" var="type">
                                            <option value="${type.id}">${type.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="modal-footer">
                                <button class="btn btn-primary updatePro">修改</button>
                                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
