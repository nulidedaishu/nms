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
        $("#staffName").val("${staffName}");
        $("#loginName").val("${loginName}");
        $("#phone").val("${phone}");
        $("#email").val("${email}");
        if ("${deptId}" != '') {
            $("#deptId").val("${deptId}");
        }
        if ("${role}" != '') {
            $("#role").val("${role}");
        }
        if ("${isValid}" != '') {
            $("#isValid").val("${isValid}");
        }


        $("#staffPageHelper").bootstrapPaginator({
            bootstrapMajorVersion: 3,
            currentPage: "${staffPage.pageNum == 0 ? 1 : staffPage.pageNum}",
            totalPages: "${staffPage.pages == 0 ? 1 : staffPage.pages}",
            size: "normal",
            alignment: "center",
            onPageClicked: function (event, originalEvent, type, page) {
                $("#pageNo").val(page);
                $("#searchForm").submit();
            }
        });
        $(".addManger").click(function () {
            let staffId = "${sessionScope.staff.staffId}";
            let formData = new FormData($("#addForm")[0]);
            formData.append("createStaffId", staffId);
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/staff/addStaff.do",
                data: formData,
                dataType: "json",
                processData: false,
                contentType: false,
                success: function (result) {
                    if (result.responseCode == "2001") {
                        location.href = "${pageContext.request.contextPath}/staff/findAll.do?pageNo=${staffPage.pageNum == 0 ? 1 : staffPage.pageNum}";
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
        $(".doMangerModify").click(function () {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/staff/findById.do",
                data: {"id": $(this).attr("data-id")},
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        $("#MargerStaffId").val(result.returnObject.staffId);
                        $("#MargerStaffname").val(result.returnObject.staffName);
                        $("#MargerLoginName").val(result.returnObject.loginName);
                        $("#MargerPhone").val(result.returnObject.phone);
                        $("#MargerAdrees").val(result.returnObject.email);
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
        $(".doMargerModal").click(function () {
            let createStaffId = "${sessionScope.staff.staffId}";
            let formData = new FormData($("#modifyForm")[0]);
            formData.append("createStaffId", createStaffId);
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/staff/modifyStaff.do",
                data: formData,
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        location.href = "${pageContext.request.contextPath}/staff/findAll.do?pageNo=${staffPage.pageNum == 0 ? 1 : staffPage.pageNum}";
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
        $("input[name='modifyStatus']").click(function(){
            $.ajax({
                staff: "post",
                url: "${pageContext.request.contextPath}/staff/modifyStatus.do",
                data: {"id":$(this).attr("data-id")},
                dataType: "json",
                success: function (result) {
                    if (result.responseCode == "2001") {
                        location.href = "${pageContext.request.contextPath}/staff/findAll.do?pageNo=${staffPage.pageNum == 0 ? 1 : staffPage.pageNum}";
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
<!-- 管理员管理 -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">
        <h3 class="panel-title">管理员管理&nbsp;&nbsp;&nbsp;&nbsp;<span id="errorMsg"></span></h3>
    </div>
    <div class="panel-body">
        <div class="showMargerSearch">
            <form class="form-inline" method="post" accept-charset="UTF-8">
                <div class="form-group">
                    <label for="userName">姓名:</label>
                    <input type="text" class="form-control" name="userName" placeholder="请输入姓名" id="userName">
                </div>
                <div class="form-group">
                    <label for="loginName">帐号:</label>
                    <input type="text" class="form-control" name="loginName" placeholder="请输入帐号" id="loginName">
                </div>
                <div class="form-group">
                    <label for="phone">电话:</label>
                    <input type="text" class="form-control" name="phone" placeholder="请输入电话" id="phone">
                </div>
                <div class="form-group">
                    <label for="email">邮箱:</label>
                    <input type="email" class="form-control" name="email" placeholder="请输入邮箱" id="email">
                </div>
                <div class="form-group">
                    <label for="dept">部门</label>
                    <select class="form-control" name="deptId" id="dept">
                        <option value="-1">请选择</option>
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.deptId}">${dept.deptName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="role">角色</label>
                    <select class="form-control" name="role" id="role">
                        <option value="-1">请选择</option>
                        <option value="1001">系统管理员</option>
                        <option value="1002">普通管理员</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="isValid">状态</label>
                    <select class="form-control" name="isValid" id="isValid">
                        <option value="-1">请选择</option>
                        <option value="1">---在职---</option>
                        <option value="0">---离职---</option>
                    </select>
                </div>
                <input type="submit" value="查询" class="btn btn-primary" id="doSearch">
            </form>
        </div>
        <br>
        <br>
        <input type="button" value="添加管理员" class="btn btn-primary" id="doAddManger">
        <!-- 添加管理员 -->
        <div class="modal fade" tabindex="-1" id="myMangerUser">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加管理员</h4>
                    </div>
                    <div class="modal-body text-center">
                        <form id="addForm">
                            <div class="row text-right">
                                <label for="marger-username" class="col-sm-4 control-label">用户姓名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="userName" id="marger-username">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-loginName" class="col-sm-4 control-label">登录帐号：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="loginName" id="marger-loginName">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-password" class="col-sm-4 control-label">登录密码：</label>
                                <div class="col-sm-4">
                                    <input type="password" class="form-control" name="password" id="marger-password">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-phone" class="col-sm-4 control-label">联系电话：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="phone" id="marger-phone">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-adrees" class="col-sm-4 control-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
                                <div class="col-sm-4">
                                    <input type="email" class="form-control" name="email" id="marger-adrees">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-role" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
                                <div class=" col-sm-4">
                                    <select class="form-control" name="role" id="marger-role">
                                        <option value="-1">请选择</option>
                                        <option value="1001">系统管理员</option>
                                        <option value="1002">普通管理员</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="marger-dept" class="col-sm-4 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</label>
                                <div class=" col-sm-4">
                                    <select class="form-control" name="deptId" id="marger-dept">
                                        <option value="-1">请选择</option>
                                        <c:forEach items="${depts}" var="dept">
                                            <option value="${dept.deptId}">${dept.deptName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addManger">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 管理员修改 -->
        <div class="modal fade" tabindex="-1" id="myModal-Manger">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">管理员修改</h4>
                    </div>
                    <form id="modifyForm">
                        <div class="modal-body text-center">
                            <div class="row text-right">
                                <label for="MargerStaffId" class="col-sm-4 control-label">id：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerStaffId" id="MargerStaffId"
                                           readonly="readonly">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerStaffname" class="col-sm-4 control-label">管理员姓名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerStaffname" id="MargerStaffname">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerLoginName" class="col-sm-4 control-label">登录帐号：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerLoginName" id="MargerLoginName"
                                           readonly="readonly">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerPhone" class="col-sm-4 control-label">联系电话：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="MargerPhone" id="MargerPhone">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerAdrees" class="col-sm-4 control-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
                                <div class="col-sm-4">
                                    <input type="email" class="form-control" name="MargerAdrees" id="MargerAdrees">
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerRole" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
                                <div class=" col-sm-4">
                                    <select class="form-control" name="MargerRole" id="MargerRole">
                                        <option value="-1">请选择</option>
                                        <option value="1001">系统管理员</option>
                                        <option value="1002">普通管理员</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="MargerDept" class="col-sm-4 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</label>
                                <div class=" col-sm-4">
                                    <select class="form-control" name="MargerDept" id="MargerDept">
                                        <option value="-1">请选择</option>
                                        <c:forEach items="${depts}" var="dept">
                                            <option value="${dept.deptId}">${dept.deptName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br/>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-warning doMargerModal">修改</button>
                            <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="show-list" style="position: relative; top: 10px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">邮箱</th>
                    <th class="text-center">部门</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">角色</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${staffPage.list}" var="staff">
                    <tr>
                        <td>${staff.staffId}</td>
                        <td>${staff.staffName}</td>
                        <td>${staff.loginName}</td>
                        <td>${staff.phone}</td>
                        <td>${staff.email}</td>
                        <td>${staff.dept.deptName}</td>
                        <td>
                            <c:if test="${staff.isValid==1}">在职</c:if>
                            <c:if test="${staff.isValid==0}">离职</c:if>
                        </td>
                        <td>
                            <c:if test="${staff.role==1001}">系统管理员</c:if>
                            <c:if test="${staff.role==1002}">普通管理员</c:if>
                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doMangerModify"
                                   data-id="${staff.staffId}" value="修改">
                            <c:if test="${staff.isValid==1}">
                                <input type="button" class="btn btn-danger btn-sm doMangerDisable" name="modifyStatus"
                                       data-id="${staff.staffId}" value="禁用">
                            </c:if>
                            <c:if test="${staff.isValid==0}">
                                <input type="button" class="btn btn-success btn-sm doProMangerDisable" name="modifyStatus"
                                       data-id="${staff.staffId}" value="启用">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="staffPageHelper"></ul>
        </div>
    </div>
</div>
</body>

</html>