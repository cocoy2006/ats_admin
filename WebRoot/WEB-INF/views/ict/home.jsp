<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<title>管理员 - 任务管理</title>
		<meta http-equiv="refresh" content="120" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	 	<link href="resources/DataTables-1.10.13/media/css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div class="col-xs-12">
					<c:import url="/template/ict_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li class="active">任务管理</li>
						<li class="pull-right">
							<a href="ict/build" title="创建任务">
		                  		<span class="fui-plus"></span>&nbsp;创建任务</a>
						</li>
					</ol>
					<!-- /breadcrumb -->
					
					<c:if test="${disComList != null}">
					<div class="table-responsive">
					  	<table class="table table-hover table-bordered table-condensed">
					  		<thead>
								<tr>
									<th>
										<label class="checkbox" for="checkboxAll">
							            	<input type="checkbox" id="checkboxAll"
							            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">编号
							          	</label>
									</th>
									<th>测试状态</th>
									<th>APP名称</th>
									<th>提交时间</th>
									<th>
										<button class="btn btn-xs btn-warning" onclick="temporarilyDelete()">删除</button>
										<button class="btn btn-xs btn-danger" onclick="permanentlyDelete()">彻底删除</button>
									</th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="disCom" items="${disComList }" varStatus="s">
								<tr>
									<td>
										<label class="checkbox" for="checkbox${s.index }">
							            	<input type="checkbox" name="ids" value="${disCom.dispatcher.id }" id="checkbox${s.index }"
							            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
						            		${disCom.dispatcher.id }
							          	</label>
									</td>
									<td>
										<c:choose>
											<c:when test="${disCom.dispatcher.state == 0 }">
												<span class="label label-primary">正在测试</span></c:when>
											<c:when test="${disCom.dispatcher.state == 1 }">
												<span class="label label-info">正在生成报告</span></c:when>
											<c:when test="${disCom.dispatcher.state == 2 }">
												<span class="label label-success">测试完成</span></c:when>
											<c:when test="${disCom.dispatcher.state == 9 }">
												<span class="label label-default">已删除</span></c:when>
										</c:choose>
									</td>
									<td>${disCom.application.name}</td>
									<td>${disCom.oprTime}</td>
									<td>
										<c:if test="${disCom.dispatcher.state != 9 }">
											<a href="ict/report/${disCom.dispatcher.id }" class="btn btn-primary btn-xs">报告</a>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</tbody>
					  	</table>
					</div>
					</c:if>
					<!-- /table -->
				</div>
			</div>
			<!-- /row -->
		</div>
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>

		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/jquery.dataTables.min.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/dataTables.bootstrap.min.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/deleter.js"></script>
	    <script type="text/javascript">
	    function temporarilyDelete() {
        	del(" 将从系统删除，确定继续?", 'ictDispatcher/temporarilyDelete');
        }
        
        function permanentlyDelete() {
        	del(" 将从系统彻底删除且无法恢复，确定继续?", 'ictDispatcher/permanentlyDelete');
        }
	    </script>
	</body>
</html>
