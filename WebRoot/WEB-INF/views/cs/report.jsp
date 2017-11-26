<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<title>管理员 - 查看报告</title>
		<meta http-equiv="refresh" content="60" />
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	    <link href="resources/dhtmlxChart_v413/dhtmlxchart.css" rel="stylesheet">
	    <link href="resources/DataTables-1.10.13/media/css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
			table td {font-size: 14px;}
		</style>
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div>
					<c:import url="/template/cs_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="cs/home">任务管理</a></li>
						<li class="active">脚本测试报告查看</li>
					</ol>
					<!-- /breadcrumb -->
					
					<c:if test="${disCom != null}">
						<div><!-- customer -->
							<blockquote>
								<h4>项目信息</h4>
							</blockquote>
							<table class="table table-hover table-bordered">
								<tr>
									<th>项目名称(中文)</th>
									<th>项目名称(英文)</th>
									<th>项目地址</th>
									<th>项目所在行业</th>
									<th>项目主页</th>
								</tr>
								<tr>
									<td>${disCom.customer.name }</td>
									<td>${disCom.customer.enName }</td>
									<td>${disCom.customer.location }</td>
									<td>${disCom.customer.industry }</td>
									<td>
										<a href="${disCom.customer.webSite }" target="_blank">${disCom.customer.webSite }</a>
									</td>
								</tr>
							</table>
						</div><!-- /customer -->
						
						<div id="title"><!-- title -->
							<blockquote>
								<h4>应用信息</h4>
							</blockquote>
							<table class="table table-hover table-bordered">
								<tr>
									<td rowspan="2" style="width: 90px;">
										<img src="upload/apk/${disCom.application.md5 }.png" onerror="this.src='upload/apk/default.png'" 
											class="img-polaroid" style="width: 80px;"/>
									</td>
									<th>应用名称</th>
									<td><c:out value="${disCom.application.label}">${disCom.application.name}</c:out></td>
									<th>版本号</th>
									<td><c:out value="${disCom.application.version}">...</c:out></td>
									<th>包名</th>
									<td>${disCom.application.packagename }</td>
								</tr>
								<tr>
									<th>文件大小</th>
									<td><fmt:formatNumber value="${disCom.application.size / 1048576 }" pattern="###.##"/>MB</td>
									<th>MD5</th>
									<td>${disCom.application.md5 }</td>
									<th></th>
									<td></td>
								</tr>
							</table>
						</div><!-- /title -->
					</c:if>
					
					<div><!-- deviceList -->
						<c:if test="${rcList != null}">
							<blockquote>
								<h4>终端列表
									<small>
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
									</small>
								</h4>
							</blockquote>
							<table id="deviceList" class="table table-hover table-bordered">
								<thead>
									<tr>
										<th>
											<!-- <label class="checkbox" for="checkboxAll">
								            	<input type="checkbox" id="checkboxAll"
								            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">编号
								          	</label> -->
										</th>
										<th>测试设备</th>
										<th>系统版本</th>
										<th>当前进度</th>
										<th>状态</th>
										<th>操作
											<!-- <button class="btn btn-xs btn-warning" onclick="temporarilyDelete()">删除</button>
											<button class="btn btn-xs btn-danger" onclick="permanentlyDelete()">彻底删除</button> -->
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="rc" items="${rcList}" varStatus="s">
										<tr>
											<td>${rc.runner.id }
												<!-- <label class="checkbox" for="checkbox${s.index }">
									            	<input type="checkbox" name="ids" value="${rc.runner.id }" id="checkbox${s.index }"
									            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
								            		${rc.runner.id }
									          	</label> -->
											</td>
											<td>
												${rc.device.manufacturer }/${rc.device.model }<br/>
												<span class="label label-default">${rc.device.sn }</span>
											</td>
											<td>${rc.device.os }</td>
											<td>
												<c:if test="${rc.runner.state != 9 }">
													<div class="progress">
														<div class="progress-bar" style="width: ${(rc.runner.state + 2) * 10 }%;">
															${(rc.runner.state + 2) * 10 }%
														</div>
													</div>
												</c:if>
											</td>
											<td>
												<c:choose>
									              	<c:when test="${rc.runner.state == 0 }">正在启动</c:when>
									              	<c:when test="${rc.runner.state == 1 }">正在准备app文件</c:when>
									              	<c:when test="${rc.runner.state == 2 }">正在测试</c:when>
									              	<c:when test="${rc.runner.state == 3 }">正在测试</c:when>
									              	<c:when test="${rc.runner.state == 4 }">app已安装</c:when>
									              	<c:when test="${rc.runner.state == 5 }">app已启动</c:when>
									              	<c:when test="${rc.runner.state == 6 }">正在回放</c:when>
									              	<c:when test="${rc.runner.state == 7 }">app已卸载</c:when>
									              	<c:when test="${rc.runner.state == 8 }">完成</c:when>
									              	<c:when test="${rc.runner.state == 9 }">已删除</c:when>
										       </c:choose>
							
											</td>
											<td>
												<c:if test="${rc.runner.state != 9 }">
													<c:if test="${rc.runner.state == 8 }">
														<a href="cs/detail/${rc.runner.id }" class="text-info">查看</a>|
													</c:if>
													<c:if test="${rc.runner.state != 8 }">
														<a href="javascript:void(0);" class="text-danger" onclick="stop(${rc.runner.id })">停止</a>|
													</c:if>
													<a href="javascript:void(0);" class="text-success" onclick="restart(${rc.runner.id })">重启</a>|
													<a href="javascript:void(0);" class="text-warning" onclick="temporarilyDelete(${rc.runner.id })">删除</a>|
												</c:if>
												<a href="javascript:void(0);" class="text-danger" onclick="permanentlyDelete(${rc.runner.id })">彻底删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div><!-- /deviceList -->
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
	    $(document).ready(function() {
	    	$('#deviceList').DataTable({
	    		"language": {
	                "url": "resources/DataTables-1.10.13/chinese.json"
	            },
	            "ordering": false
	    	});
		});
	    
        function restart(id) {
        	if(confirm(" 重启将刷新所有数据，确定继续? ")) {
        		$.ajax({
        			url: "csRunner/restart/" + id,
        			success: function() {
        				location.reload();
        			}
        		});
        	}
        }
        
        function stop(id) {
        	if(confirm(" 确定继续? ")) {
        		$.ajax({
        			url: "csRunner/stop/" + id,
        			success: function() {
        				location.reload();
        			}
        		});
        	}
        }
        
        function temporarilyDelete() {
        	del(" 将从系统删除，确定继续?", 'csRunner/temporarilyDelete');
        }
        
        function permanentlyDelete() {
        	del(" 将从系统彻底删除且无法恢复，确定继续?", 'csRunner/permanentlyDelete');
        }
		</script>
	</body>
</html>
