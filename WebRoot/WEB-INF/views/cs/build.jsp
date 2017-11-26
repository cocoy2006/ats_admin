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
		<title>管理员 - 创建任务</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	    <link rel="stylesheet" type="text/css" href="resources/jquery.ui/css/main/jquery-ui-1.9.2.custom.css">
		<!--[if IE 7]>
		<link rel="stylesheet" type="text/css" href="resources/jquery.ui/css/main/jquery.ui.1.9.2.ie.css">
		<![endif]-->
		<link rel="stylesheet" type="text/css" href="resources/dynaTree/skin-vista/ui.dynatree.css">
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
					<c:import url="/template/cs_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="ct/home">任务管理</a></li>
						<li class="active">创建任务</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div class="col-xs-6">
						<h6>1. 选择脚本</h6>
					</div>
					<div class="col-xs-6">
						<h6>2. 已选择 <span class="count label label-primary">0</span> 款终端</h6>
					</div>
					<div id="testcaseTree" class="col-xs-6" style="max-height: 300px; overflow: scroll;"></div>
					<div id="deviceTree" class="col-xs-6"></div>
					<div>
						<button class="btn btn-primary btn-block" onclick="submit()">3. 提交任务</button>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>

		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/jquery.ui/js/jquery-ui-1.8.16.custom.min.js"></script>
	    <script src="resources/dynaTree/jquery.dynatree.js"></script>
	    <script>
//	    $.ui.dynatree.nodedatadefaults["icon"] = false; // Turn off icons by default
	    
	    $("#testcaseTree").dynatree({
			minExpandLevel : 1, // 1: root node is not collapsible
			initAjax: {url: "testcase/dynatree"},
			selectMode : 1, // 1:single, 2:multi, 3:multi-hier 
			checkbox : true, // Show checkboxes.
			noLink: true, // Use <span> instead of <a> tags for all nodes
			classNames: {checkbox: "dynatree-radio"}
		});
	    
		$("#deviceTree").dynatree({
			minExpandLevel : 1, // 1: root node is not collapsible
			initAjax: {url: "device/dynatree/4"},
			selectMode : 3, // 1:single, 2:multi, 3:multi-hier 
			checkbox : true, // Show checkboxes.
			noLink: true, // Use <span> instead of <a> tags for all nodes
			onSelect : function(flag, dtnode) {
				var selectedNodes = dtnode.tree.getSelectedNodes();
				var count = 0;
				$.map(selectedNodes, function(node) {
					if (!node.data.isFolder) {
						count++;
					}
				});
				$(".count").html(count);
			}
		});

	    function submit() {
	    	// testcase id
	    	var testcaseId;
	    	var selectedNodes = $("#testcaseTree").dynatree("getSelectedNodes");
	    	if (selectedNodes.length > 0) {
	    		$.map(selectedNodes, function(node) {
	    			testcaseId = "testcaseId=" + node.data.key;
	    		});
	    	} else {
	    		alert(" 请选择测试脚本！ ");
	    		return false;
	    	}
	    	// device id array
	    	var deviceIdList = "";
	    	selectedNodes = $("#deviceTree").dynatree("getSelectedNodes");
	    	if (selectedNodes.length > 0) {
	    		$.map(selectedNodes, function(node) {
	    			if (!node.data.isFolder) {
	    				deviceIdList += "&deviceId=" + node.data.key;
	    			}
	    		});
	    	} else {
	    		alert(" 请选择测试终端！ ");
	    		return false;
	    	}
	    	$.ajax({
	    		url: "csDispatcher/build?" + testcaseId + deviceIdList,
   				type: "POST",
	    		success: function(data) {
	    			location = "cs/success";
	    		}
	    	});
	    }
	    </script>
	</body>
</html>
