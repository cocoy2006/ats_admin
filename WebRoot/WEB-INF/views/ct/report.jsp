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
		<!-- <meta http-equiv="refresh" content="60" /> cancel it because of allScreenshot -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	 	<link href="resources/font-awesome/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
	    <link href="resources/dhtmlxChart_v413/dhtmlxchart.css" rel="stylesheet">
	    <link href="resources/DataTables-1.10.13/media/css/dataTables.bootstrap.min.css" rel="stylesheet">
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
		.box {border: #d9dadc solid 1px; text-align: center; /*padding: 10px 0; */margin-bottom: 10px;}
		.box a {color: white;}
		.box a:hover, .box a:focus {text-decoration: none;}
		.box a i {float: left; padding: 8px;}
		.worst {background-color: #ed5565;}
		.best {background-color: #37bc9b;}
		.img-block {border: 1px solid #e8e8e8; font-size: 13px; margin: 10px 5px; text-align: center;}
		.ellipsis {text-overflow: ellipsis; overflow: hidden; white-space:nowrap;}
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
						<li class="active">兼容测试报告查看</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div><!-- customer -->
						<c:if test="${customer != null}">
							<blockquote>
								<h4>项目信息
									<c:if test="${runnerCount != null && runnerCount > 0}">
										<a class="btn btn-link pull-right" target="_blank"
											href="ct/excel/${dispatcher.id }">下载Excel报告</a>
										<a class="btn btn-link pull-right" target="_blank"
											href="ct/pdf/${dispatcher.id }">下载Pdf报告</a>
									</c:if>
								</h4>
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
						</c:if>
					</div><!-- /customer -->
					
					<div id="title"><!-- title -->
						<blockquote>
							<h4>应用信息</h4>
						</blockquote>
						<table class="table table-hover table-bordered">
							<tr>
								<td rowspan="2" style="width: 90px;">
									<img src="upload/apk/${application.md5 }.png" onerror="this.src='upload/apk/default.png'" 
										class="img-polaroid" style="width: 80px;"/>
								</td>
								<th>应用名称</th>
								<td><c:out value="${application.label}">${application.name}</c:out></td>
								<th>版本号</th>
								<td><c:out value="${application.version}">...</c:out></td>
								<th>包名</th>
								<td>${application.packagename }</td>
							</tr>
							<tr>
								<th>文件大小</th>
								<td><fmt:formatNumber value="${application.size / 1048576 }" pattern="###.##"/>MB</td>
								<th>MD5</th>
								<td>${application.md5 }</td>
								<th>等待时间</th>
								<td>${dispatcher.holdTime }秒</td>
							</tr>
						</table>
					</div><!-- /title -->
					
					<c:if test="${summary != null }">
						<div id="summary" style="height: 396px;"><!-- summary -->
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
						</div><!-- /summary -->
					</c:if>
					<c:if test="${analysis != null }">
						<div id="analysis" style="height: 400px;">
							<blockquote>
								<h4>性能概况</h4>
							</blockquote>
							<!-- avg -->
							<div class="col-xs-12">
								<div class="col-xs-2 box">
									<strong>安装时间</strong><br>
									<c:if test="${analysis.avgInstallTime == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgInstallTime != 0 }">
										<fmt:formatNumber value="${analysis.avgInstallTime / 1000 }" pattern="###.##"/>s
									</c:if>
								</div>
								<div class="col-xs-2 box">
									<strong>启动时间</strong><br>
									<c:if test="${analysis.avgLoadTime == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgLoadTime != 0 }">
										<fmt:formatNumber value="${analysis.avgLoadTime / 1000 }" pattern="###.##"/>s
									</c:if>
								</div>
								<div class="col-xs-2 box">
									<strong>CPU占用</strong><br>
									<c:if test="${analysis.avgCpu == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgCpu != 0 }">
										<fmt:formatNumber value="${analysis.avgCpu }" pattern="###.##"/>%
									</c:if>
								</div>
								<div class="col-xs-2 box">
									<strong>内存占用</strong><br>
									<c:if test="${analysis.avgMemory == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgMemory != 0 }">
										<fmt:formatNumber value="${analysis.avgMemory / 1000 }" pattern="###.##"/>MB
									</c:if>
								</div>
								<div class="col-xs-2 box">
									<strong>上行流量</strong><br>
									<c:if test="${analysis.avgUptraffic == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgUptraffic != 0 }">
										<fmt:formatNumber value="${analysis.avgUptraffic / 1000 }" pattern="###.##"/>KB
									</c:if>
								</div>
								<div class="col-xs-2 box">
									<strong>下行流量</strong><br>
									<c:if test="${analysis.avgDowntraffic == 0 }">
										-
									</c:if>
									<c:if test="${analysis.avgDowntraffic != 0 }">
										<fmt:formatNumber value="${analysis.avgDowntraffic / 1000 }" pattern="###.##"/>KB
									</c:if>
								</div>
							</div>
							<!-- best -->
							<div class="col-xs-12">
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minInstallTimeRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minInstallTimeDevice.manufacturer }&nbsp;
											${analysis.minInstallTimeDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minInstallTime == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minInstallTime != 0 }">
											<fmt:formatNumber value="${analysis.minInstallTime / 1000 }" pattern="###.##"/>s
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minLoadTimeRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minLoadTimeDevice.manufacturer }&nbsp;
											${analysis.minLoadTimeDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minLoadTime == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minLoadTime != 0 }">
											<fmt:formatNumber value="${analysis.minLoadTime / 1000 }" pattern="###.##"/>s
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minCpuRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minCpuDevice.manufacturer }&nbsp;
											${analysis.minCpuDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minCpu == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minCpu != 0 }">
											<fmt:formatNumber value="${analysis.minCpu }" pattern="###.##"/>%
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minMemoryRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minMemoryDevice.manufacturer }&nbsp;
											${analysis.minMemoryDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minMemory == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minMemory != 0 }">
											<fmt:formatNumber value="${analysis.minMemory / 1000 }" pattern="###.##"/>MB
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minUptrafficRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minUptrafficDevice.manufacturer }&nbsp;
											${analysis.minUptrafficDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minUptraffic == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minUptraffic != 0 }">
											<fmt:formatNumber value="${analysis.minUptraffic / 1000 }" pattern="###.##"/>KB
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box best">
									<a href="ct/detail/${analysis.minDowntrafficRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-up fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.minDowntrafficDevice.manufacturer }&nbsp;
											${analysis.minDowntrafficDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.minDowntraffic == 0 }">
											-
										</c:if>
										<c:if test="${analysis.minDowntraffic != 0 }">
											<fmt:formatNumber value="${analysis.minDowntraffic / 1000 }" pattern="###.##"/>KB
										</c:if>
										</strong>
									</a>
								</div>
							</div>
							<!-- worst -->
							<div class="col-xs-12">
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxInstallTimeRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxInstallTimeDevice.manufacturer }&nbsp;
											${analysis.maxInstallTimeDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxInstallTime == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxInstallTime != 0 }">
											<fmt:formatNumber value="${analysis.maxInstallTime / 1000 }" pattern="###.##"/>s
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxLoadTimeRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxLoadTimeDevice.manufacturer }&nbsp;
											${analysis.maxLoadTimeDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxLoadTime == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxLoadTime != 0 }">
											<fmt:formatNumber value="${analysis.maxLoadTime / 1000 }" pattern="###.##"/>s
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxCpuRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxCpuDevice.manufacturer }&nbsp;
											${analysis.maxCpuDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxCpu == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxCpu != 0 }">
											<fmt:formatNumber value="${analysis.maxCpu }" pattern="###.##"/>%
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxMemoryRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxMemoryDevice.manufacturer }&nbsp;
											${analysis.maxMemoryDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxMemory == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxMemory != 0 }">
											<fmt:formatNumber value="${analysis.maxMemory / 1000 }" pattern="###.##"/>MB
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxUptrafficRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxUptrafficDevice.manufacturer }&nbsp;
											${analysis.maxUptrafficDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxUptraffic == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxUptraffic != 0 }">
											<fmt:formatNumber value="${analysis.maxUptraffic / 1000 }" pattern="###.##"/>KB
										</c:if>
										</strong>
									</a>
								</div>
								<div class="col-xs-2 box worst">
									<a href="ct/detail/${analysis.maxDowntrafficRunner.id }" target="_blank">
										<i class="fa fa-thumbs-o-down fa-2x" aria-hidden="true"></i>
										<strong>
											${analysis.maxDowntrafficDevice.manufacturer }&nbsp;
											${analysis.maxDowntrafficDevice.model }
										</strong><br>
										<strong>
										<c:if test="${analysis.maxDowntraffic == 0 }">
											-
										</c:if>
										<c:if test="${analysis.maxDowntraffic != 0 }">
											<fmt:formatNumber value="${analysis.maxDowntraffic / 1000 }" pattern="###.##"/>KB
										</c:if>
										</strong>
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<div><!-- deviceList -->
						<c:if test="${rcList != null}">
							<blockquote>
								<h4>终端列表 <span class="badge">${runnerCount }</span>
									<small>
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
									</small>
								</h4>
							</blockquote>
							<table id="deviceList" class="table table-hover table-bordered">
								<thead>
									<tr>
										<th>编号
											<!-- <label class="checkbox" for="checkboxAll">
								            	<input type="checkbox" id="checkboxAll"
								            		data-toggle="checkbox" onclick="checkAll(this, 'ids')">编号
								          	</label> -->
										</th>
										<th>设备</th>
										<th>安装(s)</th>
										<th>启动(s)</th>
										<th>CPU(%)</th>
										<th>内存(MB)</th>
										<th>截图数</th>
										<th>进度</th>
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
											<td>
												<c:if test="${rc.runner.installTime == 0 }">-</c:if>
												<c:if test="${rc.runner.installTime != 0 }">${rc.displayInstallTime }</c:if>
											</td>
											<td>
												<c:if test="${rc.runner.loadTime == 0 }">-</c:if>
												<c:if test="${rc.runner.loadTime != 0 }">${rc.displayLoadTime }</c:if>
											</td>
											<td>
												<c:if test="${rc.runner.averageCpu == 0 }">-</c:if>
												<c:if test="${rc.runner.averageCpu != 0 }">${rc.displayAverageCpu }</c:if>
											</td>
											<td>
												<c:if test="${rc.runner.averageMemory == 0 }">-</c:if>
												<c:if test="${rc.runner.averageMemory != 0 }">${rc.displayAverageMemory }</c:if>
											</td>
											<td>${rc.ssCount }</td>
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
														<a href="ct/detail/${rc.runner.id }" class="text-info">查看</a>|
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
					
					<div><!-- screenshotList -->
						<blockquote>
							<h4>全部截图 </h4>
						</blockquote>
						<c:if test="${activityList != null }">
							<div id="allActivitys">
								<div class="col-xs-2">
									<select class="form-control">
										<option value="activitys">Activity</option>
										<!--  
										<option value="resolutions">分辨率</option>
	                                    <option value="platforms">系统版本</option>
	                                    -->
									</select>
								</div>
								
								<div class="col-xs-10">
									<select class="form-control" onchange="changeActivity(this)">
										<c:forEach var="activity" items="${activityList }" varStatus="s">
											<option value="${activity }">${activity }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div id="allScreenshots"></div>
						</c:if>
						<c:if test="${activityList == null }">
							暂无数据
						</c:if>
					</div><!-- /screenshotList -->
				</div>
			</div>
			<!-- /row -->
		</div>
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>

		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/dhtmlxChart_v413/dhtmlxchart.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/jquery.dataTables.min.js"></script>
	    <script src="resources/DataTables-1.10.13/media/js/dataTables.bootstrap.min.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/deleter.js"></script>
	    <script type="text/javascript">
	    var dispatcherId = ${dispatcher.id };
	    $(document).ready(function() {
	    	dataTable();
	    	chart();     
	    	firstActivity();
		});
	    
		function chart() {
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
		}
		
		function dataTable() {
			$('#deviceList').DataTable({
	    		"language": {
	                "url": "resources/DataTables-1.10.13/chinese.json"
	            },
	            columnDefs:[{
	            	orderable: false,
	                targets: [0,1,7,8,9]
	            }]
	    	});
		}
	        
        function restart(id) {
        	if(confirm(" 重启将刷新所有数据，确定继续? ")) {
        		$.ajax({
        			url: "ctRunner/restart/" + id,
        			success: function() {
        				location.reload();
        			}
        		});
        	}
        }
        
        function stop(id) {
        	if(confirm(" 确定继续? ")) {
        		$.ajax({
        			url: "ctRunner/stop/" + id,
        			success: function() {
        				location.reload();
        			}
        		});
        	}
        }
        
        function temporarilyDelete(id) {
	    	if(confirm(" 将从系统删除，确定继续?")) {
	    		$.ajax({
	        		url: 'ctRunner/temporarilyDelete/' + id,
	        		success: function(data) {
	        			location.reload();
	        		}
	        	});
	    	}
        }
	    
	    function batchTemporarilyDelete() {
        	del(" 将从系统删除，确定继续?", 'ctRunner/batchTemporarilyDelete');
        }
        
        function permanentlyDelete(id) {
        	if(confirm(" 将从系统彻底删除且无法恢复，确定继续?")) {
        		$.ajax({
            		url: 'ctRunner/permanentlyDelete/' + id,
            		success: function(data) {
            			location.reload();
            		}
            	});
        	}
        }
        
        function batchPermanentlyDelete() {
        	del(" 将从系统彻底删除且无法恢复，确定继续?", 'ctRunner/batchPermanentlyDelete');
        }
        
        function firstActivity() {
        	allScreenshots('${activityName }');
        }
        
        function changeActivity(that) {
        	allScreenshots($(that).val());
        }
        
        function allScreenshots(activityName) {
        	$.ajax({
        		url: 'ct/ss/' + activityName + '/' + dispatcherId,
        		success: function(data) {
        			var html = '';
        			$.each(eval(data), function(index, item) {
        				html += 
        					'<div class="col-xs-2 img-block ellipsis" title="' + item.device.manufacturer + '&nbsp;' + item.device.model + '&nbsp;' + item.device.os + '">' +
        						'<img src="upload/ctSs/' + item.ssList[0].image + '" class="img-responsive">' +
        						item.device.manufacturer + '&nbsp;' + item.device.model + '&nbsp;' + item.device.os +
        					'</div>';
        			});
        			$('#allScreenshots').html(html);
        		}
        	});
        }
		</script>
	</body>
</html>
