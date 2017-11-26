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
				<div class="col-xs-12">
					<c:import url="/template/ct_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="ct/home">任务管理</a></li>
						<li class="active">创建任务</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div class="col-xs-6">
						<h6>1. 选择所属项目</h6>
						<select id="customerId" class="form-control select select-primary" data-toggle="select">
            				<option value="0">请选择...</option>
            				<c:if test="${customerList != null}">
            					<c:forEach var="customer" items="${customerList }" varStatus="s">
									<option value="${customer.id }">${customer.name }</option>
								</c:forEach>
            				</c:if>
          				</select>
						<h6>2. 选择后缀名是.apk的文件</h6>
						<form id="uploadForm" name="uploadForm" enctype="multipart/form-data" target="hiddenUploadFrame" method="post">
							<input type="file" id="file" name="file" class="form-control" 
								data-placement="bottom" title="请先选择程序安装文件" />
						</form>
						<h6>3. APP启动后的停等时间</h6>
						<div class="input-group">
						  	<input type="text" id="holdTime" name="holdTime" class="form-control" />
						  	<span class="input-group-addon">秒</span>
						</div>
					</div>
					<div class="col-xs-6">
						<h6>4. 已选择 <span class="count label label-primary">0</span> 款终端</h6>
						<div id="deviceTree"></div>
					</div>
					
					<iframe name="hiddenUploadFrame" id="hiddenUploadFrame" style="display: none;"></iframe>
					
					<div class="col-xs-12">
						<div class="progress">
							<div class="progress-bar" style="width: 0%;" data-placement="top" title="数据读取中..."></div>
						</div>
						<button class="btn btn-primary btn-block" onclick="build()">5. 提交任务</button>
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
	    <script src="resources/js/messager.js"></script>
	    <script>
	    $.ui.dynatree.nodedatadefaults["icon"] = false; // Turn off icons by default
		$("#deviceTree").dynatree({
			minExpandLevel : 1, // 1: root node is not collapsible
			initAjax: {url: "device/dynatree/1"},
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
		String.prototype.endWith = function(str) {
		    var reg = new RegExp(str+"$");
		    return reg.test(this);
	    };

	    function build() {
	    	var customerId = $("#customerId").val();
	    	if(customerId == 0) {
	    		danger(" 请选择所属项目！ ");
	    		return false;
	    	}
	    	var file = $("#file").val();
	    	if(!file || !file.endWith(".apk")) {
	    		danger(" 请选择正确的测试文件！ ");
	    		return false;
	    	}
	    	var holdTime = $("#holdTime").val();
	    	if(isNaN(holdTime)) {
	    		danger(" 停等时间格式不正确，请重新输入！ ");
	    		return false;
	    	}
	    	var selectedNodes = $("#deviceTree").dynatree("getSelectedNodes");
	    	if (selectedNodes.length <= 0) {
	    		danger(" 请选择测试终端！ ");
	    		return false;
	    	}
	    	var idList = "";
    		$.map(selectedNodes, function(node) {
    			if (!node.data.isFolder) {
    				idList += "&id=" + node.data.key;
    			}
    		});
    		var url = "ctDispatcher/build?customerId=" + customerId + "&holdTime=" + holdTime + idList;
    		$("#uploadForm").attr("action", url).submit();
    		setTimeout(updateProgressbar, 2000);
	    }
	    
	    var updateProgressbarFrequency = 1000; // 进度条的更新频率，单位ms
	    var attempts = 0;
	    function updateProgressbar() {
	    	var bar = $(".progress-bar");
	    	$.ajax( {
	    		url : "ctDispatcher/percentDone",
	    		async : false,
	    		dataType : "json",
	    		success : function(data) {
	    			if(data == -3) {
	    				danger(" apk文件解析失败，请联系管理员.");
	    			} else if (data == null) {
	    				if (attempts++ < 3) {
	    					setTimeout(updateProgressbar, 2000);
	    				} else {
	    					danger("网络不稳定，请稍后重试.");
	    					location.reload();
	    				}
	    			} else {
	    				bar.attr("style", "width:" + data + "%;").html(data + "%");
	    				if (data < 99) {
	    					window.setTimeout(updateProgressbar, updateProgressbarFrequency);
	    					$(".btn").html(" 正在上传apk文件，请等待... ").addClass("disabled");
	    				} else {
	    					location = "ct/success";
	    				}
	    			}
	    		}
	    	});
	    }
	    </script>
	</body>
</html>
