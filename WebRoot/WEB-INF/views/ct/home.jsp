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
					<c:import url="/template/ct_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li class="active">任务管理</li>
						<li class="pull-right">
							<a href="ct/build" title="创建任务">
		                  		<span class="fui-plus"></span>&nbsp;创建任务</a>
						</li>
					</ol>
					<!-- /breadcrumb -->
					
					<c:if test="${disComList != null}">
					<div class="table-responsive">
					  	<table class="table table-hover table-bordered">
					  		<thead>
								<tr>
									<th>编号
										<!-- <label class="checkbox" for="checkboxAll">
							            	<input type="checkbox" id="checkboxAll"
							            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">编号
							          	</label> -->
									</th>
									
									<th>测试项</th>
									<th>提交时间</th>
									<th>状态</th>
									<th>测试结果</th>
									<th>操作
										<!-- <a href="javascript:void(0);" class="text-warning" onclick="temporarilyDelete()">删除</a>|
										<a href="javascript:void(0);" class="text-danger" onclick="permanentlyDelete()">彻底删除</a> -->
									</th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="disCom" items="${disComList }" varStatus="s">
								<tr>
									<td>${disCom.dispatcher.id }
										<!-- <label class="checkbox" for="checkbox${s.index }">
							            	<input type="checkbox" name="ids" value="${disCom.dispatcher.id }" id="checkbox${s.index }"
							            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
						            		${disCom.dispatcher.id }
							          	</label> -->
									</td>
									
									<td>
										<c:out value="${disCom.application.label}">${disCom.application.name}</c:out>
										(<c:out value="${disCom.application.version}">--</c:out>)
									</td>
									<td>${disCom.oprTime}</td>
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
									<td>
										${disCom.allCount }&nbsp;(
										<span class="text-danger">${disCom.failureCount }</span>/
										<span class="text-primary">${disCom.successCount }</span>/
										<span class="text-warning">${disCom.runningCount }</span>/
										<del>${disCom.removedCount }</del>)
									</td>
									<td>
										<c:if test="${disCom.dispatcher.state != 9 }">
											<a href="ct/report/${disCom.dispatcher.id }" class="text-info">查看</a>|
											<a href="javascript:void(0);" class="text-warning" onclick="temporarilyDelete(${disCom.dispatcher.id })">删除</a>|
										</c:if>
										<a href="javascript:void(0);" class="text-danger" onclick="permanentlyDelete(${disCom.dispatcher.id })">彻底删除</a>
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
	    $(document).ready(function() {
	    	$('table').DataTable({
	    		"language": {
	                "url": "resources/DataTables-1.10.13/chinese.json"
	            },
	            "ordering": false
	    	});
		});
	    
	    function temporarilyDelete(id) {
	    	if(confirm(" 将从系统删除，确定继续?")) {
	    		$.ajax({
	        		url: 'ctDispatcher/temporarilyDelete/' + id,
	        		success: function(data) {
	        			location.reload();
	        		}
	        	});
	    	}
        }
	    
	    function batchTemporarilyDelete() {
        	del(" 将从系统删除，确定继续?", 'ctDispatcher/batchTemporarilyDelete');
        }
        
        function permanentlyDelete(id) {
        	if(confirm(" 将从系统彻底删除且无法恢复，确定继续?")) {
        		$.ajax({
            		url: 'ctDispatcher/permanentlyDelete/' + id,
            		success: function(data) {
            			location.reload();
            		}
            	});
        	}
        }
        
        function batchPermanentlyDelete() {
        	del(" 将从系统彻底删除且无法恢复，确定继续?", 'ctDispatcher/batchPermanentlyDelete');
        }
	    </script>
	</body>
</html>
