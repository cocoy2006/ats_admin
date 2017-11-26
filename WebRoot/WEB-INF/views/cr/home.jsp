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
		<title>管理员</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
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
				<div>
					<c:import url="/template/cr_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li class="active">设备选择</li>
					</ol>
					<!-- /breadcrumb -->
					
					<c:if test="${deviceList != null}">
					<div class="table-responsive">
					  	<table class="table table-hover table-bordered">
					  		<thead>
								<tr>
									<th>序号</th>
									<th>服务器(端口)</th>
									<th>设备型号</th>
									<th>序列号</th>
									<th>操作系统</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="device" items="${deviceList }" varStatus="s">
								<tr>
									<td>
										<fmt:formatNumber value="${s.index + 1}" pattern="00"/>
									</td>
									<td>${device.server}(${device.port})</td>
									<td>${device.manufacturer }/${device.model }</td>
									<td>${device.sn}</td>
									<td>${device.os }</td>
									<td>
										<c:choose>
											<c:when test="${device.state == 0}">
												<a class="btn btn-primary btn-xs" href="javascript:void(0);"
													onclick="lock(this, ${device.id})">使用该设备</a>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${device.state == 1}">
												<span class="label label-default">正在使用</span>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${device.state == 3}">
												<span class="label label-warning">未就绪</span>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${device.state == 5}">
												<span class="label label-warning">离线</span>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${device.state == 9}">
												<span class="label label-danger">已删除</span>
											</c:when>
										</c:choose>
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
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/map.js"></script>
	    <script type="text/javascript">
	    	function lock(that, id) {
	    		if(confirm(" 请确认设备已经开启USB调试模式? ")) {
	    			$('.btn').addClass("disabled").html(" 正在初始化设备，稍等...");
	    			location = "cr/record/" + id;
	    		}
	    	}
	    </script>
	</body>
</html>
