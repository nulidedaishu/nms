<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
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
            $("#deptPage").bootstrapPaginator({
                bootstrapMajorVersion: 3,
                currentPage: "${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}",
                totalPages: "${deptPage.pages== 0 ? 1 : deptPage.pages}",
                size: "normal",
                alignment: "center",
                pageUrl: function (dept, page, current) {
                    return "${pageContext.request.contextPath}/dept/findAll.do?pageNo=" + page;
                }
            });
            $(".addDept").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/dept/addDept.do",
                    data: {"name": $("#addDeptName").val(), "remark": $("#addDeptDuty").val()},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/dept/findAll.do?pageNo=${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}";
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                })
            })
            $(".addSonDept").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/dept/addDept.do",
                    data: {
                        "name": $("#addSonDeptName").val(),
                        "remark": $("#addSonDeptDuty").val(),
                        "fatherDept": $("#addSonFatherDeptName").val()
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/dept/findAll.do?pageNo=${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}";
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                })
            })
            $(".modifyDept").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/dept/modifyDept.do",
                    data: {
                        "name": $("#modifyDeptName").val(),
                        "remark": $("#modifyDeptDuty").val(),
                        "id": $("#modifyDeptId").val()
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/dept/findAll.do?pageNo=${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}";
                        } else {
                            $("#errorMsg").tooltip({
                                title: "error",
                                placement: "center",
                                template: "<div class='tooltip errorMsg'>" + result.message + "</div>"
                            }).tooltip("show");
                        }
                    }
                })
            })
            $(".doAddSonDept").click(function () {
                let parentDeptName = $(this).closest("tr").find("td:eq(2)").text();
                $("#addSonFatherDeptName").val(parentDeptName);
                $("#sonDept").modal("show");
            });
            $(".doModifyDept").click(function () {
                let id = $(this).closest("tr").find("td:eq(0)").text();
                let name = $(this).closest("tr").find("td:eq(2)").text();
                let remark = $(this).closest("tr").find("td:eq(3)").text();
                $("#modifyDeptId").val(id);
                $("#modifyDeptName").val(name);
                $("#modifyDeptDuty").val(remark);
                $("#modifyDeptId-hidden").val(id);
                $("#modifyDeptId").prop("readonly", true);
                $("#modifyDept").modal("show");
            });
            $(".doEnable").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/dept/enableDept.do",
                    data: {"id": $(this).attr("data-id")},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/dept/findAll.do?pageNo=${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}";
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
            $(".doDisable").click(function () {
                $.ajax({
                    type: "post",
                    url: "${pageContext.request.contextPath}/dept/disableDept.do",
                    data: {"id": $(this).attr("data-id")},
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == "2001") {
                            location.href = "${pageContext.request.contextPath}/dept/findAll.do?pageNo=${deptPage.pageNum == 0 ? 1 : deptPage.pageNum}";
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
</head>

<body>

<!-- 部门管理 -->
<div class="panel panel-default" id="departmentSet">
    <div class="panel-heading">
        <h3 class="panel-title">部门管理&nbsp;&nbsp;&nbsp;<span id="errorMsg" style="color: red"></span></h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加部门" class="btn btn-primary" id="doAddDept">
        <br>
        <br>
        <div class="modal fade" tabindex="-1" id="Dept">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加部门</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="addDeptName" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-name" id="addDeptName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addDeptDuty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-duty" id="addDeptDuty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addDept">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 添加子部门模态框 -->
        <div class="modal fade" tabindex="-1" id="sonDept">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加子部门</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="addSonFatherDeptName" class="col-sm-4 control-label">父部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-name" value="" readonly="readonly"
                                       id="addSonFatherDeptName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addSonDeptName" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-name" id="addSonDeptName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addSonDeptDuty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-duty" id="addSonDeptDuty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addSonDept">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>


        <!-- 修改部门模态框 -->
        <div class="modal fade" tabindex="-1" id="modifyDept">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">修改部门</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="modifyDeptId" class="col-sm-4 control-label">id：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-name" value="" readonly="readonly"
                                       id="modifyDeptId">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="modifyDeptName" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-name" id="modifyDeptName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="modifyDeptDuty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dept-duty" id="modifyDeptDuty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary modifyDept">修改</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="show-list">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">部门编号</th>
                    <th class="text-center">部门名称</th>
                    <th class="text-center">部门职能</th>
                    <th class="text-center">所属部门</th>
                    <th class="text-center">部门状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${deptPage.list}" var="dept">
                    <tr>
                        <td>${dept.deptId}</td>
                        <td>${dept.deptNo}</td>
                        <td>${dept.deptName}</td>
                        <td>${dept.remark}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty dept.fatherDept}">
                                    --
                                </c:when>
                                <c:otherwise>
                                    ${dept.fatherDept.deptName}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${dept.isValid==1}">有效</c:if>
                            <c:if test="${dept.isValid==0}">无效</c:if>
                        </td>
                        <td class="text-center">
                            <input type="button" class="btn btn-info btn-sm doAddSonDept" data-id="${dept.deptId}"
                                   data-name="${dept.deptName}" value=" 添加子部门">
                            <input type="button" class="btn btn-warning btn-sm doModifyDept" data-id="${dept.deptId}"
                                   value="修改">
                            <c:if test="${dept.isValid==1}">
                                <input type="button" class="btn btn-danger btn-sm doDisable"
                                       data-id="${dept.deptId}" value="禁用">
                            </c:if>
                            <c:if test="${dept.isValid==0}">
                                <input type="button" class="btn btn-success btn-sm doEnable"
                                       data-id="${dept.deptId}" value="启用">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="deptPage"></ul>
        </div>
    </div>
</div>
</body>

</html>
