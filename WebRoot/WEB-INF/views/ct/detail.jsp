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
	    <link href="resources/dhtmlxChart_v413/dhtmlxchart.css" rel="stylesheet">
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
		<style>
			#screenshot div {margin-top: 15px;}
			#screenshot div span {position: absolute; top: 0; left: 0;}
			.logcatHeader {color: #1abc9c; padding-left: 10px;}
		</style>
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div class="col-xs-12">
					<c:import url="/template/ct_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="ct/home">任务管理</a></li>
						<li class="active">兼容测试详细报告</li>
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
								<li class="active">
									<a href="#detail" data-toggle="tab">测试概况</a>
								</li>
								<li>
									<a href="#screenshot" data-toggle="tab">全部截图
										<label class="badge">${fn:length(rc.ssList) }</label>
									</a>
								</li>
								<li>
									<a href="#log" data-toggle="tab">错误日志
										<label class="badge">${fn:length(rc.logcatList) }</label>
									</a>
								</li>
							</ul>
							<div class="tab-content">
								<div id="detail" class="tab-pane active">
									<c:if test="${rc.runner != null}">
										<div>
											<table class="table table-hover table-bordered">
												<c:if test="${rc.runner.installTime == 0}">
													<tr>
														<th>安装结果</th>
														<td>
															<c:if test="${rc.runner.state == 0}">-</c:if>
															<c:if test="${rc.runner.state > 0}">
																<span class="label label-warning">
																	<i class="icon-remove icon-white"></i>
																	<c:out value="测试未通过"></c:out>	
																</span>
																<span class="label label-warning">${rc.runner.installResult }</span>
															</c:if>
														</td>
														<th>安装时间</th>
														<td>-</td>
													</tr>
												</c:if>
												<c:if test="${rc.runner.installTime > 0}">
													<tr>
														<th>安装结果</th>
														<td>
															<span class="label label-success">
																<i class="icon-ok icon-white"></i>
																<c:out value="测试通过"></c:out>
															</span>
														</td>
														<th>安装时间</th>
														<td>${rc.displayInstallTime }秒</td>
													</tr>
												</c:if>
												<c:if test="${rc.runner.loadTime == 0}">
													<tr>
														<th>启动结果</th>
														<td>
															<c:if test="${rc.runner.state == 0}">-</c:if>
															<c:if test="${rc.runner.state > 0}">
																<span class="label label-warning">
																	<i class="icon-remove icon-white"></i>
																	<c:out value="测试未通过"></c:out>
																</span>
															</c:if>
														</td>
														<th>启动时间</th>
														<td>-</td>
													</tr>
												</c:if>
												<c:if test="${rc.runner.loadTime > 0}">
													<tr>
														<th>启动结果</th>
														<td>
															<span class="label label-success">
																<i class="icon-ok icon-white"></i>
																<c:out value="测试通过"></c:out>
															</span>
														</td>
														<th>启动时间</th>
														<td>${rc.displayLoadTime }秒</td>
													</tr>
												</c:if>
												<c:if test="${rc.runner.uninstallTime == 0}">
													<tr>
														<th>卸载结果</th>
														<td>
															<c:if test="${rc.runner.state == 0}">-</c:if>
															<c:if test="${rc.runner.state > 0}">
																<span class="label label-warning">
																	<i class="icon-remove icon-white"></i>
																	<c:out value="测试未通过"></c:out>
																</span>
															</c:if>
														</td>
														<th>卸载时间</th>
														<td>-</td>
													</tr>
												</c:if>
												<c:if test="${rc.runner.uninstallTime > 0}">
													<tr>
														<th>卸载结果</th>
														<td>
															<span class="label label-success">
																<i class="icon-ok icon-white"></i>
																<c:out value="测试通过"></c:out>
															</span>
														</td>
														<th>卸载时间</th>
														<td>${rc.displayUninstallTime }秒</td>
													</tr>
												</c:if>
											</table>
										</div>
									</c:if>
									<c:if test="${rc.srList != null}">
										<div class="table-bordered" style="margin-bottom: 20px;">
											<h4 style="text-align: center;">CPU占用率
												<span style="font-size: 75%;">均值：<fmt:formatNumber value="${rc.runner.averageCpu }" pattern="###.##"/>%</span>
											</h4>
											<div id="cpu_chart_container" style="height:330px;"></div>
										</div>
										<div class="table-bordered" style="margin-bottom: 20px;">
											<h4 style="text-align: center;">内存占用
												<span style="font-size: 75%;">均值：<fmt:formatNumber value="${rc.runner.averageMemory / 1024}" pattern="###.##"/>MB</span>
											</h4>
											<div id="memory_chart_container" style="height:330px;"></div>
										</div>
									</c:if>
								</div>
								<div id="screenshot" class="tab-pane">
									<c:if test="${fn:length(rc.ssList) > 0}">
										<div class="col-xs-12">
											<p class="bg-info text-center">点击图片删除</p>
										</div>
										<c:forEach var="ss" items="${rc.ssList}" varStatus="s">
											<div class="col-xs-2">
												<img src="upload/ctSs/${ss.image }" class="img-rounded img-responsive" onclick="deleteSs(${ss.id })"/>
												<span class="badge">${s.count }</span>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(rc.ssList) == 0}">
										<span style="padding-left: 40px;">	暂无内容 </span>
									</c:if>
								</div>
								<div id="log" class="tab-pane">
									<c:if test="${fn:length(rc.logcatList) > 0}">
										<c:forEach var="logcat" items="${rc.logcatList }" varStatus="s">
											<p class="logcatHeader">
												${s.count }&nbsp;.&nbsp;
												<c:choose>
													<c:when test="${logcat.type == 1 }">Crash&nbsp;:&nbsp;Native crash</c:when>
													<c:when test="${logcat.type == 2 }">Anr&nbsp;:</c:when>
													<c:when test="${logcat.type == 3 }">Exception&nbsp;:</c:when>
												</c:choose>
											</p>
											<div class="col-xs-12">
												<div class="col-xs-1">
													<span class="label label-success">错误栈</span>
												</div>
												<div class="col-xs-11">
													<pre>${logcat.content }</pre>
												</div>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(rc.logcatList) == 0}">
										<span style="padding-left: 40px;">	暂无内容 </span>
									</c:if>
								</div>
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
	    <script src="resources/dhtmlxChart_v413/dhtmlxchart.js"></script>
	    <script>
	    $(document).ready(function() {});
	    
	    function deleteSs(id) {
	    	if(confirm('删除无法恢复，确认?')) {
	    		$.ajax({
	    			url: 'ctSs/delete/' + id,
					success: function(data) {
						location.reload();
					}
	    		});
	    	}
	    }
	    </script>
	    <c:if test="${fn:length(rc.srList) > 0}">
	    	<script type="text/javascript">
				var cpuData = [
			        <c:forEach var="subRunner" items="${rc.srList }" varStatus="item">
						{cpu : ${subRunner.cpu }, time : ${item.index * 2 }},
					</c:forEach>
				];
				var cpuChart =  new dhtmlXChart({
					view: "spline",
					container: "cpu_chart_container",
					value: "#cpu#",
			        tooltip: "CPU占用#cpu#%",
			        xAxis:{
				    	title:" 相对测试时间（S） ",
				    	template:"#time#"
					},
		            yAxis:{
				     	title:"CPU占用（%）"
			        },
				});
				cpuChart.parse(cpuData, "json");
				var memoryData = [
			        <c:forEach var="subRunner" items="${rc.srList }" varStatus="item">
						{memory : <fmt:formatNumber value="${subRunner.memory / 1024}" pattern="###.##"/>, time : ${item.index * 2 }},
					</c:forEach>
				];
				var memoryChart =  new dhtmlXChart({
					view: "spline",
					container: "memory_chart_container",
					value: "#memory#",
			        tooltip: "内存占用#memory#MB",
			        xAxis:{
				    	title:" 相对测试时间（S） ",
				    	template:"#time#"
					},
		            yAxis:{
				     	title:"内存占用（MB）"
			        }
				});
				memoryChart.parse(memoryData, "json");
			</script>
	    </c:if>
	</body>
</html>
