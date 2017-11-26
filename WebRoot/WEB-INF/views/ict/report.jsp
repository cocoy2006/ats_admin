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
		<title>管理员 - 查看报告</title>
		<meta http-equiv="refresh" content="60" />
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
		<style type="text/css">
			.throughput {border: #9aa8b8 solid 10px; border-radius: 50%; height: 100px; margin: 10px auto; text-align: center; width: 100px;}
			.throughput span {display: block; font-size: 18px; padding-top: 10px;}
			.throughput strong {font-size: 20px;}
			table td {font-size: 14px;}
		</style>
	</head>
	
	<body>
		<div class="container">
			<div class="row demo-row">
				<div class="col-xs-12">
					<c:import url="/template/ict_nav.html" charEncoding="UTF-8"/>

					<ol class="breadcrumb">
						<li><a href="#">主页</a></li>
						<li><a href="ict/home">任务管理</a></li>
						<li class="active">iOS兼容测试报告查看</li>
					</ol>
					<!-- /breadcrumb -->
					
					<c:if test="${customer != null}">
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
									<td>${customer.name }</td>
									<td>${customer.enName }</td>
									<td>${customer.location }</td>
									<td>${customer.industry }</td>
									<td>
										<a href="${customer.webSite }" target="_blank">${customer.webSite }</a>
									</td>
								</tr>
							</table>
						</div><!-- /customer -->
					</c:if>
					
					<div id="title"><!-- title -->
						<blockquote>
							<h4>应用信息</h4>
						</blockquote>
						<table class="table table-hover table-bordered">
							<c:if test="${application != null}">
								<tr>
									<td rowspan="2" style="width: 90px;">
										<img src="data:image/png;base64,${application.icon }" onerror="this.src='upload/ipa/default.png'" 
											class="img-polaroid" style="width: 80px;" />
									</td>
									<th>应用名称</th>
									<th>文件大小</th>
									<th>等待时间</th>
									<th>测试终端数</th>
									<th>任务状态</th>
								</tr>
							</c:if>
							<c:if test="${dispatcher != null && runnerCount != null}">
								<tr>
									<td>${application.name }</td>
									<td><fmt:formatNumber value="${application.size / 1048576 }" pattern="###.##"/>MB</td>
									<td>${dispatcher.holdTime }秒</td>
									<td>${runnerCount }</td>
									<td>
										<c:choose>
											<c:when test="${dispatcher.state == 0 }">
												<span class="label label-primary">正在测试</span></c:when>
											<c:when test="${dispatcher.state == 1 }">
												<span class="label label-info">正在生成报告</span></c:when>
											<c:when test="${dispatcher.state == 2 }">
												<span class="label label-success">测试完成</span></c:when>
											<c:when test="${dispatcher.state == 9 }">
												<span class="label label-default">已删除</span></c:when>
										</c:choose>
									</td>
								</tr>
							</c:if>
						</table>
					</div><!-- /title -->
					
					<div id="summary"><!-- summary -->
						<c:if test="${summary != null }">
							<blockquote>
								<h4>兼容概况</h4>
							</blockquote>
							<div class="col-xs-12">
								<div id="summaryChart" class="col-xs-9" style="height: 350px;"></div>
								<div class="col-xs-3">
									<div class="throughput">
										<span>通过率</span>
										<strong>
											<fmt:formatNumber value="${summary.passCount / summary.total * 100 }" pattern="###"/>%
										</strong>
									</div>
									<table class="table table-hover table-bordered">
										<tr>
											<th>合计</th>
											<th>${summary.installFailureCount + summary.loadFailureCount + summary.uninstallFailureCount }</th>
										</tr>
										<tr>
											<td>安装未通过</td>
											<td>${summary.installFailureCount }</td>
										</tr>
										<tr>
											<td>启动未通过</td>
											<td>${summary.loadFailureCount }</td>
										</tr>
										<tr>
											<td>卸载未通过</td>
											<td>${summary.uninstallFailureCount }</td>
										</tr>
									</table>
								</div>
							</div>
						</c:if>
					</div><!-- /summary -->
					<div id="analysis"><!-- analysis -->
					
					</div><!-- /analysis -->
					<div id="deviceList"><!-- deviceList -->
						<c:if test="${rcList != null}">
							<blockquote>
								<h4>终端列表</h4>
							</blockquote>
							<table class="table table-hover table-bordered">
								<tr>
									<th>
										<label class="checkbox" for="checkboxAll">
							            	<input type="checkbox" id="checkboxAll"
							            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">编号
							          	</label>
									</th>
									<th>测试设备</th>
									<th>安装耗时</th>
									<th>启动耗时</th>
									<th>当前进度</th>
									<th>状态</th>
									<th>
										<button class="btn btn-xs btn-warning" onclick="temporarilyDelete()">删除</button><!--  -->
										<button class="btn btn-xs btn-danger" onclick="permanentlyDelete()">彻底删除</button>
									</th>
								</tr>
								<c:forEach var="rc" items="${rcList}" varStatus="s">
									<tr>
										<td>
											<label class="checkbox" for="checkbox${s.index }">
								            	<input type="checkbox" name="ids" value="${rc.runner.id }" id="checkbox${s.index }"
								            		data-toggle="checkbox" onclick="cancelCheckAll('checkboxAll', 'ids')">
							            		${rc.runner.id }
								          	</label>
										</td>
										<td>
											${rc.device.model.manufacturer.name }/${rc.device.model.name }<br/>
											<span class="label label-default">${rc.device.sn }</span>
										</td>
										<td>
											<c:if test="${rc.runner.installTime == 0 }">-</c:if>
											<c:if test="${rc.runner.installTime != 0 }">${rc.displayInstallTime }秒</c:if>
										</td>
										<td>
											<c:if test="${rc.runner.loadTime == 0 }">-</c:if>
											<c:if test="${rc.runner.loadTime != 0 }">${rc.displayLoadTime }秒</c:if>
										</td>
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
								              	<c:when test="${rc.runner.state == 6 }">正在兼容性测试</c:when>
								              	<c:when test="${rc.runner.state == 7 }">app已卸载</c:when>
								              	<c:when test="${rc.runner.state == 8 && rc.runner.installTime > 0 && rc.runner.loadTime > 0}">
								              		已完成
								              	</c:when>
								              	<c:when test="${rc.runner.state == 8 && rc.runner.installTime == 0}">
								              		未通过<br/>
								              		<span class="label label-warning">安装未通过</span>
								              	</c:when>
								              	<c:when test="${rc.runner.state == 8 && rc.runner.loadTime == 0}">
								              		未通过<br/>
								              		<span class="label label-warning">启动未通过</span>
								              	</c:when>
								              	<c:when test="${rc.runner.state == 9 }">已删除</c:when>
									       </c:choose>
						
										</td>
										<td>
											<c:if test="${rc.runner.state != 9 }">
												<c:if test="${rc.runner.state == 8 }">
													<a class="btn btn-primary btn-xs" href="ict/detail/${rc.runner.id }">详情</a>
												</c:if>
												<c:if test="${rc.runner.state != 8 }">
													<button class="btn btn-danger btn-xs" onclick="stop(${rc.runner.id })">停止</button>
												</c:if>
												<button class="btn btn-warning btn-xs" onclick="restart(${rc.runner.id })">重启</button>
											</c:if>
										</td>
									</tr>
								</c:forEach>
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
	    <script src="resources/dhtmlxChart_v413/dhtmlxchart.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/deleter.js"></script>
	    <script type="text/javascript">
			var summaryData = [
				{title: "安装未通过", count: "${summary.installFailureCount }", color: "#ee3639"},
				{title: "启动未通过", count: "${summary.loadFailureCount }", color: "#ee9e36"},
				{title: "卸载未通过", count: "${summary.uninstallFailureCount }", color: "#9b36ee"},
				{title: "通过", count: "${summary.passCount }", color: "#90ed7d"}
			];
			var summaryChart = new dhtmlXChart({
	            view: "pie",
	            container: "summaryChart",
	            value: "#count#",
	            color: "#color#",
	            label: "#title#",
	            pieInnerText: "#count#",
	            shadow: 0,
	            legend:{
					width: 175,
					align: "right",
					valign: "middle",
					template: function(obj){
						var sum = summaryChart.sum("#count#");
						var text = obj.title + Math.round(parseFloat(obj.count) / sum * 100) + "%";
						return text;
					}
				}
	        });
	        summaryChart.parse(summaryData, "json");
	        
	        function restart(id) {
	        	if(confirm(" 重启将刷新所有数据，确定继续? ")) {
	        		$.ajax({
	        			url: "ictRunner/restart/" + id,
	        			success: function() {
	        				location.reload();
	        			}
	        		});
	        	}
	        }
	        
	        function stop(id) {
	        	if(confirm(" 确定继续? ")) {
	        		$.ajax({
	        			url: "ictRunner/stop/" + id,
	        			success: function() {
	        				location.reload();
	        			}
	        		});
	        	}
	        }

	        function temporarilyDelete() {
	        	del(" 将从系统删除，确定继续?", 'ictRunner/temporarilyDelete');
	        }
	        
	        function permanentlyDelete() {
	        	del(" 将从系统彻底删除且无法恢复，确定继续?", 'ictRunner/permanentlyDelete');
	        }
		</script>
	</body>
</html>
