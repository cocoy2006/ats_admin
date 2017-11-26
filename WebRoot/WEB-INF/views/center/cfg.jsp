<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<div class="col-xs-12">
					<c:import url="/template/center_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li class="active">配置管理</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div id="main">
						<!--
						<div class="col-xs-12">
							<h4>全局参数</h4>
							<div>
								<div class="col-xs-2">
									<label>数据删除策略</label>
								</div>
								<div class="col-xs-10">
									<c:if test="${global_strategy_removed == 0 }">
										仅标记<button class="btn btn-xs btn-danger" onclick="editBoolean('global_strategy_removed', 1)">切换为‘永久删除’策略</button>
									</c:if>
									<c:if test="${global_strategy_removed == 1 }">
										永久删除<button class="btn btn-xs btn-primary" onclick="editBoolean('global_strategy_removed', 0)">切换为‘仅标记’策略</button>
									</c:if>
								</div>
							</div>
						</div>
						-->
						<div class="col-xs-12">
							<h4>脚本测试参数</h4>
							<div>
								<div class="col-xs-2">
									<label>控件查询超时时间</label>
								</div>
								<div class="col-xs-10">
									${cs_playback_timeout }秒
									<button class="btn btn-xs btn-primary" onclick="openNumberModal('cs_playback_timeout')">编辑</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
		
		<!-- modal -->
		<div id="numberModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">新的参数</h4>
					</div>
					<div class="modal-body">
						<form id="numberForm" name="numberForm" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="value">
									新的参数
								</label>
								<div class="col-sm-9">
									<input type="text" id="value" name="value" class="form-control" value=""/>
								</div>
							</div>
							<input type="hidden" id="key" name="key" value=""/>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="editNumber()">保存</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>

		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/js/messager.js"></script>
	    <script type="text/javascript">
		    function editBoolean(key, value) {
		    	$.ajax({
		    		url: 'cfg/set',
		    		data: 'key=' + key + '&value=' + value,
		    		dataType: 'json',
		    		success: function(data) {
		    			if(data == 1) {
		    				location.reload();
		    			} else {
		    				danger('');
		    			}
		    		}
		    	});
		    }
		    
		    function openNumberModal(key) {
		    	$('#key').val(key);
		    	$('#numberModal').modal();
		    }
		    
		    function editNumber() {
		    	var value = $('#value').val();
		    	if(!value || isNaN(value)) {
	    			danger(" 参数应该为正整数.");
	    			return false;
	    		}
		    	$.ajax({
		    		url: 'cfg/set',
		    		data: $('#numberForm').serialize(),
		    		type: 'POST',
		    		dataType: 'json',
		    		success: function(data) {
		    			if(data == 1) {
		    				location.reload();
		    			} else {
		    				danger('');
		    			}
		    		}
		    	});
		    }
	    </script>
	</body>
</html>
