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
		<title>管理员</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	    <!-- <link href="resources/Flat-UI-master/docs/assets/css/demo.css" rel="stylesheet"> -->
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
		<style>
			#screenshot div {margin-top: 15px;}
			#screenshot div span.badge {position: absolute; top: 0; left: 0;}
		</style>
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div class="col-xs-12">
					<c:import url="/template/cs_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="cs/home">任务管理</a></li>
						<li class="active">脚本测试详细报告</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div>
						<blockquote>
							<h4>终端信息</h4>
						</blockquote>
						<table class="table table-striped table-bordered">
							<tr>
								<th>终端型号</th>
								<td>${rc.device.manufacturer }&nbsp;${rc.device.model }</td>
								<th>操作系统</th>
								<td>${rc.device.os }
									<c:if test="${rc.device.rom != null }">(${rc.device.rom })</c:if>
								</td>
							</tr>
							<tr>
								<th>分辨率</th>
								<td>${rc.device.width }*${rc.device.height }</td>
								<th></th>
								<td></td>
							</tr>
						</table>
					</div>
					
					<div>
						<blockquote>
							<h4>测试结果</h4>
						</blockquote>
						<div class="tabbable">
							<ul id="tab" class="nav nav-tabs">
								<li>
									<a href="#detail" data-toggle="tab">测试结果</a>
								</li>
								<li class="active">
									<a href="#screenshot" data-toggle="tab">屏幕截图</a>
								</li>
								<li>
									<a href="#log" data-toggle="tab">日志记录</a>
								</li>
							</ul>
							<div class="tab-content">
								<div id="detail" class="tab-pane">
									<c:if test="${rc.runner != null}">
										<div>
											<table class="table table-hover table-bordered">
												<tr>
													<th>安装结果</th>
													<td>${rc.runner.installResult }</td>
													<th>启动结果</th>
													<td>${rc.runner.loadResult }</td>
												</tr>
											</table>
										</div>
									</c:if>
								</div>
								<div id="screenshot" class="tab-pane active">
					                <div class="col-xs-12">
					                	<label class="checkbox pull-right" for="checkbox">
							            	<input type="checkbox" id="checkbox"
							            		data-toggle="checkbox" onclick="show(this)">
						            		预期结果
							          	</label>
					                </div>
					                
									<c:if test="${rc.ssList != null}">
										<c:forEach var="ss" items="${rc.ssList}" varStatus="s">
											<div class="col-xs-6" style="border: 1px solid #ccc;">
												<div class="col-xs-4 expect" style="display: none;">
													<p>预期结果</p>
													<img src="upload/crSs/${ss.script.screenshot }" class="img-rounded img-responsive" />
												</div>
												<div class="col-xs-8">
													<p>测试结果</p>
													<img src="upload/csSs/${ss.image }" class="img-rounded img-responsive" />
												</div>
												<span class="badge">${s.count }</span>
												<p class="col-xs-12 img-comment">
													<strong>备注&nbsp;:&nbsp;</strong>${ss.note }
												</p>
												<p class="col-xs-12 img-comment">
													<strong>匹配度&nbsp;:&nbsp;</strong>
													<fmt:formatNumber value="${ss.matches * 100 }" pattern="##.####"/>%
												</p>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(rc.ssList) == 0}">
										<span style="padding-left: 40px;">	暂无内容 </span>
									</c:if>
								</div>
								<div id="log" class="tab-pane">${rc.logcat }</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>

		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script>
	    	function show(that) {
	    		if(that.checked) {
	    			$('.expect').show();
	    		} else {
	    			$('.expect').hide();
	    		}
	    	}
	    </script>
	</body>
</html>
