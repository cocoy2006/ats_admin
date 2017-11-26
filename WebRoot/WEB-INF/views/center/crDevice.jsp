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
		<title>脚本录制设备管理</title>
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
						<li class="active">脚本录制设备管理</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div class="btn-toolbar">
	                	<div class="btn-group">
		                  	<a class="btn btn-info" href="javascript:void(0);" onclick="openModal()">
		                  		<span class="fui-plus"></span>&nbsp;新增设备</a>
	                  		<a class="btn btn-primary" href="javascript:void(0);" onclick="active()">
		                  		<span class="fui-power"></span>&nbsp;激活设备</a>
		                  	<a class="btn btn-warning" href="javascript:void(0);" onclick="unactive()">
		                  		<span class="fui-cross"></span>&nbsp;移除设备</a>
		                  	<a class="btn btn-danger" href="javascript:void(0);" onclick="del()">
		                  		<span class="fui-trash"></span>&nbsp;删除设备</a>
	                	</div>
	              	</div> 
	              	<!-- /toolbar -->
					
					<c:if test="${deviceList != null}">
					<div class="table-responsive">
					  	<table class="table table-hover">
					  		<thead>
								<tr>
									<th>
										<label class="checkbox" for="checkboxAll">
							            	<input type="checkbox" id="checkboxAll"
							            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">
							          	</label>
									</th>
									<th>序号</th>
									<th>服务器(端口)</th>
									<th>设备序列号</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="device" items="${deviceList }" varStatus="s">
								<tr>
									<td>
										<label class="checkbox" for="checkbox${s.index}">
							            	<input type="checkbox" name="ids" value="${device.id}" id="checkbox${s.index}"
							            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
							          	</label>
									</td>
									<td>
										<fmt:formatNumber value="${s.index + 1}" pattern="0000"/>
									</td>
									<td>${device.server}(${device.port})</td>
									<td>${device.sn}</td>
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
											<c:when test="${dc.device.state == 5}">
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

		<div id="deviceModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">设备管理</h4>
					</div>
					<div class="modal-body">
						<form id="deviceForm" class="form-horizontal" name="deviceForm">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="server">
									<em>*</em>&nbsp;服务器地址
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="server" name="server" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="port">
									<em>*</em>&nbsp;服务器端口
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="port" name="port"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="sn">
									<em>*</em>&nbsp;设备序列号
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="sn" name="sn"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="save()">保存</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
	
		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script type="text/javascript">
	    	function openModal() {
	    		document.getElementById("deviceForm").reset();
	    		$("#deviceModal").modal();
	    	}
	    	
	    	function save() {
	    		$.ajax({
    				url: "crDevice/save",
    				type: "POST",
    				data: $("#deviceForm").serialize(),
    				dataType: "json",
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
		    
		    function del() {
		    	if($(":checked[name='ids']").length > 0) {
		    		generateAjax("删除后将无法使用且无法恢复，确定继续?", "delete");
		    	}
		    }
		    
		    function generateAjax(confirmContent, url) {
		    	if(confirm(confirmContent)) {
		    		$.ajax({
		    			url: "crDevice/" + url,
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
