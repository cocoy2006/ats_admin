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
						<li class="active">客户/项目/脚本管理</li>
					</ol>
					<!-- /breadcrumb -->
					
					<div class="panel panel-success">
						<div class="panel-heading">
							<a href="<%=basePath%>center/cpts">首页</a> &gt;
							<a href="<%=basePath%>center/cpts/${customer.id }">${customer.name }</a> &gt;
							<a href="<%=basePath%>center/cpts/${customer.id }/${project.id }">${project.name }</a> &gt;
							${testcase.name }
						</div>
					  	<div class="panel-body">
					  		<c:if test="${scriptList != null}">
								<c:forEach var="script" items="${scriptList}" varStatus="s">
									<div class="col-xs-6" style="margin: 10px 0;">
										<div class="col-xs-6">
											<img src="${script.screenshot }" 
												class="img-rounded img-responsive" style="height: 300px;" />
										</div>
										<div class="col-xs-6">
											<p><label class="label label-default">步骤</label>${script.step }</p>
											<p><label class="label label-default">操作</label></p>
											<p class="hide action">${script.action }</p>
											<p class="hide">${script.params }</p>
											<p><!-- for parsed operation --></p>
											<p><label class="label label-default">控件类型</label>${script.mclass }</p>
											<p><label class="label label-default">备注</label>${script.note }</p>
										</div>
									</div>
								</c:forEach>
							</c:if>
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
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/messager.js"></script>
	    <script src="resources/js/parseCommand.js"></script>
	    <script type="text/javascript">
	    $(document).ready(function() {
			$('.action').each(function() {
				var action = $(this).text();
				var params = $(this).next().text();
				var op = parseCommand(action, params);
				$(this).next().next().html(op);
			});
		});
	    </script>
	</body>
</html>
