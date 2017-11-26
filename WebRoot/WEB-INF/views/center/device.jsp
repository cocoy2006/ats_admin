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
		<title>测试设备管理</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	 	<link href="resources/font-awesome/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
	 	<link href="resources/DataTables-1.10.13/media/css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
		<style>
			em {color: #FF0000}
		</style>
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div class="col-xs-12">
					<c:import url="/template/center_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li class="active">测试设备管理</li>
					</ol>
					<!-- /breadcrumb -->
					
					
					<!-- <div class="pull-right">
						<a class="btn btn-info btn-xs" href="javascript:void(0);" onclick="openModal()">
	                  		<i class="fa fa-plus" aria-hidden="true"></i>&nbsp;新增设备</a>
	               		<a class="btn btn-primary btn-xs" href="javascript:void(0);" onclick="active()">
	                  		<i class="fa fa-power-off" aria-hidden="true"></i>&nbsp;激活设备</a>
	                  	<a class="btn btn-warning btn-xs" href="javascript:void(0);" onclick="unactive()">
	                  		<i class="fa fa-plug" aria-hidden="true"></i>&nbsp;移除设备</a>
	                  	<a class="btn btn-danger btn-xs" href="javascript:void(0);" onclick="del()">
	                  		<i class="fa fa-trash" aria-hidden="true"></i>&nbsp;删除设备</a>	
                  		<div class="dropdown" style="display: inline-block;">
						  <button class="btn btn-inverse btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
					    	<i class="fa fa-list" aria-hidden="true"></i>&nbsp;调整类型<span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
						    <li role="presentation"><a role="menuitem" tabindex="1" href="javascript:void(0);">
						    	<i class="fa fa-android" aria-hidden="true"></i>&nbsp;兼容测试</a></li>
						    <li role="presentation"><a role="menuitem" tabindex="2" href="javascript:void(0);">
						    	<i class="fa fa-apple" aria-hidden="true"></i>&nbsp;iOS兼容测试</a></li>
						    <li role="presentation"><a role="menuitem" tabindex="3" href="javascript:void(0);">
						    	<i class="fa fa-stop" aria-hidden="true"></i>&nbsp;脚本录制</a></li>
						    <li role="presentation"><a role="menuitem" tabindex="4" href="javascript:void(0);">
						    	<i class="fa fa-play" aria-hidden="true"></i>&nbsp;脚本测试</a></li>
						    <li role="presentation" class="disabled"><a role="menuitem" tabindex="5" href="javascript:void(0);">
						    	<i class="fa fa-random" aria-hidden="true"></i>&nbsp;随机测试</a></li>
						    <li role="presentation" class="disabled"><a role="menuitem" tabindex="6" href="javascript:void(0);">
						    	<i class="fa fa-rocket" aria-hidden="true"></i>&nbsp;性能测试</a></li>
						    <li role="presentation" class="disabled"><a role="menuitem" tabindex="7" href="javascript:void(0);">
						    	<i class="fa fa-window-close" aria-hidden="true"></i>&nbsp;中断测试</a></li>
						  	<li role="presentation" class="divider"></li>
						  	<li role="presentation"><a role="menuitem" tabindex="0" href="javascript:void(0);">
						  		<i class="fa fa-circle-o" aria-hidden="true"></i>&nbsp;取消分配</a></li>
						  </ul>
						</div>		
					</div> -->
					
					
					<c:if test="${deviceList != null}">
					<div class="table-responsive">
					  	<table class="table table-hover table-bordered table-condensed">
					  		<thead>
								<tr>
									<!-- <th>
										<label class="checkbox" for="checkboxAll">
							            	<input type="checkbox" id="checkboxAll"
							            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">
							          	</label>
									</th> -->
									<th>序号</th>
									<th>服务器信息</th>
									<th>设备信息</th>
									<th>设备类型</th>
									<th>系统编号</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="device" items="${deviceList }" varStatus="s">
								<tr>
									<!-- <td>
										<label class="checkbox" for="checkbox${s.index}">
							            	<input type="checkbox" name="ids" value="${device.id}" id="checkbox${s.index}"
							            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
							          	</label>
									</td> -->
									<td>
										<fmt:formatNumber value="${s.index + 1}" pattern="000"/>
									</td>
									<td>${device.server }:${device.port }</td>
									<td>${device.manufacturer }/${device.model }<br/>
										<span class="label label-default">${device.sn }</span>
										<span class="label label-default">${device.os }</span>
									</td>
									<td>
										<c:choose>
												<c:when test="${device.type == 0}">未分配</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${device.type == 1}">
													<i class="fa fa-android" aria-hidden="true"></i>&nbsp;兼容测试</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${device.type == 2}">
													<i class="fa fa-apple" aria-hidden="true"></i>&nbsp;iOS兼容测试</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${device.type == 3}">
													<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;脚本录制</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${device.type == 4}">
													<i class="fa fa-play" aria-hidden="true"></i>&nbsp;脚本测试</c:when>
											</c:choose>
									</td>
									<td>
										<c:out value="${device.label}">--</c:out>
										<!-- <a href="javascript:void(0);" onclick="openLabelModal(${device.id}, '${device.label}')">编辑</a> -->
										<i class="fa fa-pencil" style="cursor: pointer;"
											onclick="openLabelModal(${device.id}, '${device.label}')"></i>
									</td>
									<td>
										<c:choose>
											<c:when test="${device.state == 0}">
												<span class="label label-success">可使用</span>
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
									<td>
										<a href="javascript:void(0);" class="text-danger" onclick="del(${device.id })">彻底删除</a>
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

		<div id="labelModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">系统编号</h4>
					</div>
					<div class="modal-body">
						<form id="labelForm" class="form-horizontal" name="labelForm">
							<div class="form-group">
								<div class="col-xs-12">
									<input type="text" class="form-control" id="label" name="label" value=""/>
								</div>
							</div>
							<input type="hidden" class="form-control" id="deviceId" name="deviceId" value=""/>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="update()">保存</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
	
		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/jquery.dataTables.min.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/dataTables.bootstrap.min.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/map.js"></script>
	    <script type="text/javascript">
	    $(document).ready(function() {
	    	$('table').DataTable({
	    		"language": {
	                "url": "resources/DataTables-1.10.13/chinese.json"
	            },
	            "ordering": false
	    	});
		});
	    
    	function openLabelModal(id, label) {
    		$('#deviceId').val(id);
    		$('#label').val(label);
    		$('#labelModal').modal();
    	}
    	
    	function update() {
    		$.ajax({
	    		url: 'device/update',
				type: 'POST',
				data: $('#labelForm').serialize(),
				dataType: 'json',
				success: function(data) {
					location.reload();
				}
    		});
    	}
    
    	function active() {
	    	if($(":checked[name='ids']").length > 0) {
	    		generateAjax("激活后将立即使用，确定继续?", "active");
	    	}
	    }
	    
	    function unactive() {
	    	if($(":checked[name='ids']").length > 0) {
	    		generateAjax("移除后将无法使用，但不会删除设备，确定继续?", "unactive");
	    	}
	    }
	    
	    function del(id) {
	    	if(confirm(" 删除后将无法使用且无法恢复，确定继续?")) {
	    		$.ajax({
	        		url: 'device/remove/' + id,
	        		success: function(data) {
	        			location.reload();
	        		}
	        	});
	    	}
	    }
	    
	    function batchDel() {
	    	if($(":checked[name='ids']").length > 0) {
	    		generateAjax(" 删除后将无法使用且无法恢复，确定继续?", "batchRemove");
	    	}
	    }
	    
	    function generateAjax(confirmContent, url) {
	    	if(confirm(confirmContent)) {
	    		$.ajax({
	    			url: "device/" + url,
	    			type: "POST",
	    			data: generateIds(),
	    			dataType: "json",
	    			success: function(data) {
	    				location.reload();
	    			}
	    		});
	    	}
	    }
	    
	    function generateIds() {
	    	var ids = "";
	    	$(":checked[name='ids']").each(function() {
	    		ids += "ids=" + this.value + "&";
	    	});
	    	return ids;
	    }
	    </script>
	</body>
</html>
