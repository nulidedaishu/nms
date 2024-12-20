<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="zh">

<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="ie=edge"/>
<title>backend</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstarp/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/bootstarp/js/bootstrap.js" type="text/javascript"
        charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/userSetting.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js" type="text/javascript"
        charset="utf-8"></script>
<script>
    $(function () {
        $("#userName").val("${userName}");
        $("#loginName").val("${loginName}");
        $("#phone").val("${phone}");
        $("#address").val("${address}");
        if ("${isValid}" != '') {
            $("#isValid").val("${isValid}");
        }
        $("#userPageHelper").bootstrapPaginator({
            bootstrapMajorVersion: 3,
            currentPage: "${userPage.pageNum == 0 ? 1 : userPage.pageNum}",
            totalPages: "${userPage.pages == 0 ? 1 : userPage.pages}",
            size: "normal",
            alignment: "center",
            onPageClicked: function (event, originalEvent, type, page) {
                $("#pageNo").val(page);
                $("#searchForm").submit();
            }
        });
        $(".doModify").click(function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/user/findById.do",
                data: {"id": $(this).attr("data-id")},
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        $("#MargerUserId").val(result.returnObject.userId);
                        $("#MargerUsername").val(result.returnObject.userName);
                        $("#MargerLoginName").val(result.returnObject.loginName);
                        $("#MargerPhone").val(result.returnObject.phone);
                        $("#MargerAdrees").val(result.returnObject.address);
                    } else {
                        $("#errorMsg").tooltip({
                            title: "error",
                            placement: "center",
                            template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                        }).tooltip("show");
                    }
                }
            })
        });
        $(".updateOne").click(function () {
            let formData = new FormData($("#modifyForm")[0]);
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/user/modifyUser.do",
                data: formData,
                processData: false,
                contentType: false,
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        location.href = "${pageContext.request.contextPath}/user/findAll.do?pageNo=${userPage.pageNum == 0 ? 1 : userPage.pageNum}";
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
        $("input[name='modifyStatus']").click(function () {
            $.ajax({
                staff: "post",
                url: "${pageContext.request.contextPath}/user/modifyStatus.do",
                data: {"id": $(this).attr("data-id")},
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        location.href = "${pageContext.request.contextPath}/user/findAll.do?pageNo=${userPage.pageNum == 0 ? 1 : userPage.pageNum}";
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
<body>
<div class="panel panel-default" id="userInfo" id="homeSet">
    <div class="panel-heading">
        <h3 class="panel-title">用户管理</h3>
    </div>
    <div class="panel-body">
        <div class="showusersearch">
            <form class="form-inline" method="post" accept-charset="UTF-8">
                <div class="form-group">
                    <label for="userName">姓名:</label>
                    <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入姓名">
                </div>
                <div class="form-group">
                    <label for="loginName">帐号:</label>
                    <input type="text" class="form-control" name="loginName" id="loginName" placeholder="请输入帐号">
                </div>
                <div class="form-group">
                    <label for="phone">电话:</label>
                    <input type="text" class="form-control" name="phone" id="phone" placeholder="请输入电话">
                </div>
                <div class="form-group">
                    <label for="address">地址:</label>
                    <input type="text" class="form-control" name="address" id="address" placeholder="请输入地址">
                </div>
                <div class="form-group">
                    <label for="isValid">状态</label>
                    <select class="form-control" name="isValid" id="isValid">
                        <option value="-1">全部</option>
                        <option value="1">---有效---</option>
                        <option value="0">---无效---</option>
                    </select>
                </div>
                <input type="submit" value="查询" class="btn btn-primary" id="doSearch">
            </form>
        </div>
        <br>
        <br>
        <!-- 管理员修改 -->
        <div class="modal fade" tabindex="-1" id="myModal">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">用户修改</h4>
                    </div>
                    <form id="modifyForm">
                        <div class="modal-body text-center">
                            <div class="row text-right">
                                <label for="MargerUserId" class="col-sm-4 control-label">编号：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerUserId" id="MargerUserId"
                                           readonly="readonly">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerUsername" class="col-sm-4 control-label">姓名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerUsername" id="MargerUsername">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerLoginName" class="col-sm-4 control-label">帐号：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerLoginName" id="MargerLoginName"
                                           readonly="readonly">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerPhone" class="col-sm-4 control-label">电话：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerPhone" id="MargerPhone">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerAdrees" class="col-sm-4 control-label">地址：</label>
                                <div class="col-sm-4">
                                    <input type="email" class="form-control" name="MargerAdrees" id="MargerAdrees">
                                </div>
                            </div>
                            <br>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <button class="btn btn-warning updateOne">修改</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="show-list" style="position: relative;top: 30px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">地址</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${userPage.list}" var="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.loginName}</td>
                        <td>${user.phone}</td>
                        <td>${user.address}</td>
                        <td>
                            <c:if test="${user.isValid==1}">在职</c:if>
                            <c:if test="${user.isValid==0}">离职</c:if>
                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doModify" data-id="${user.userId}"
                                   value="修改">
                            <c:if test="${user.isValid==1}">
                                <input type="button" class="btn btn-danger btn-sm doDisable" name="modifyStatus"
                                       data-id="${user.userId}" value="禁用">
                            </c:if>
                            <c:if test="${user.isValid==0}">
                                <input type="button" class="btn btn-success btn-sm doProDisable" name="modifyStatus"
                                       data-id="${user.userId}" value="启用">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="userPageHelper"></ul>
        </div>
    </div>
</div>
</body>
</html>