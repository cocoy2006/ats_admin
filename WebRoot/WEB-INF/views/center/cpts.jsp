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
						<div class="panel-heading">全部客户
							<button class="btn btn-primary btn-sm pull-right" title="添加客户" 
			               		onclick="openModal(this, -1, '', '', '', '', 'http://')">
			               		<span class="fui-plus"></span>&nbsp;添加</button>
						</div>
					  	<div class="panel-body">
				    		<!-- Table -->
						  	<table class="table table-condensed table-hover table-bordered">
						  		<tr class="active text-muted">
						  			<td style="width: 43px;">
						  				<input type="checkbox" id="checkboxAll"
						            		onclick="checkAll(this, 'ids')">
						  			</td>
						  			<td>客户名称(中文)</td>
									<td>客户名称(英文)</td>
									<td>客户地址</td>
									<td>客户所在行业</td>
									<td>客户主页</td>
						  			<td style="width: 123px;"></td>
						  		</tr>
						    	<c:if test="${customerList != null}">
						    		<c:forEach var="customer" items="${customerList }" varStatus="s">
						    			<tr>
								    		<td>
								    			<input type="checkbox" name="ids" value="${customer.id}" id="checkbox${s.index}"
								            		onclick="cancelCheckAll('checkboxAll', 'ids')">
								    		</td>
								    		<td>
								    			<a href="<%=basePath%>center/cpts/${customer.id}" class="customerName">${customer.name }</a>
								    		</td>
											<td>${customer.enName}</td>
											<td>${customer.location}</td>
											<td>${customer.industry}</td>
											<td>
												<a href="${customer.webSite}" target="_blank">${customer.webSite}</a>
											</td>
								    		<td>
								    			<button class="btn btn-info btn-xs" title="编辑客户名"
													onclick="openModal(this, ${customer.id}, '${customer.name}', 
														'${customer.enName}', '${customer.location}', 
														'${customer.industry}', '${customer.webSite}')">
							                  		<span class="fui-new"></span>&nbsp;编辑</button>
								    		</td>
								    	</tr>
						    		</c:forEach>
						    	</c:if>
						  	</table>
					  	</div>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
		
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>
		
		<!-- modal -->
		<div id="customerModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">客户管理</h4>
					</div>
					<div class="modal-body">
						<form id="form" name="form" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="name">
									<em>*</em>&nbsp;客户名称(中文)
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="name" name="name" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="enName">
									客户名称(英文)
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="enName" name="enName" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="location">
									客户地址
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="location" name="location" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="industry">
									客户所在行业
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="industry" name="industry" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="webSite">
									客户主页
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="webSite" name="webSite" value=""/>
								</div>
							</div>
							<input type="hidden" id="id" name="id" value="-1"/>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="saveOrUpdate()">保存</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	
		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/js/checkbox.js"></script>
	    <script src="resources/js/messager.js"></script>
	    <script type="text/javascript">
	    function openModal(obj, id, name, enName, location, industry, webSite) {
	    	$('.modal-title').html(obj.title);
	    	$('#id').val(id);
	    	$('#name').val(name);
	    	$('#enName').val(enName);
	    	$('#location').val(location);
	    	$('#industry').val(industry);
	    	$('#webSite').val(webSite);
	    	$('.modal').modal();
	    }
	    
	    function saveOrUpdate() {
	    	// name exist or not
			var name = $('#name').val();
	    	if(!name) {
	    		danger(' 客户名称(中文)不能为空.');
	    		$('#name').focus();
	    		return false;
	    	}
	    	try {
	    		$('.customerName').each(function() {
					if($(this).text().toLowerCase() == name.toLowerCase()) {
						throw ' 客户名已存在.';
					}
				});
	    	} catch(e) {
	    		danger(e);
	    		return false;
	    	}
			$.ajax({
				url: 'customer/saveOrUpdate',
				type: 'POST',
				data: $('#form').serialize(),
				dataType: 'json',
				success: function(data) {
					location.reload();
				}
			});
	    }
	    </script>
	</body>
</html>
