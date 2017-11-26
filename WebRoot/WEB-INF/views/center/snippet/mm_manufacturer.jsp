<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	</head>
	
	<body>
		<div class="panel panel-success">
			<div class="panel-heading">全部厂商
				<button class="btn btn-primary btn-sm pull-right" title="添加厂商" 
               		onclick="openModal(this, -1, '')">
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
			  			<td>厂商名</td>
			  			<td style="width: 140px;"></td>
			  		</tr>
			    	<c:if test="${manufacturerList != null}">
			    		<c:forEach var="manufacturer" items="${manufacturerList }" varStatus="s">
			    			<tr>
					    		<td>
					    			<input type="checkbox" name="ids" value="${manufacturer.id}" id="checkbox${s.index}"
					            		onclick="cancelCheckAll('checkboxAll', 'ids')">
					    		</td>
					    		<td>
					    			<a href="javascript:void(0);" class="manufacturerName"
					    				onclick="loadModel(${manufacturer.id})">${manufacturer.name }</a>
					    		</td>
					    		<td>
					    			<button class="btn btn-info btn-xs" title="编辑厂商名"
										onclick="openModal(this, ${manufacturer.id }, '${manufacturer.name }')">
				                  		<span class="fui-new"></span>&nbsp;编辑</button>
					    		</td>
					    	</tr>
			    		</c:forEach>
			    	</c:if>
			  	</table>
		  	</div>
		</div>
		
		<!-- modal -->
		<div id="manufacturerModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">厂商</h4>
					</div>
					<div class="modal-body">
						<form id="manufacturerForm" class="form-horizontal" name="manufacturerForm">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="name">
									厂商名
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="name" name="name" value=""/>
								</div>
							</div>
							<input type="hidden" id="manufacturerId" name="manufacturerId" value="-1"/>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="saveOrUpdate()">保存</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		<script type="text/javascript">
		    function openModal(obj, id, name) {
		    	$('#manufacturerModal h4').html(obj.title);
		    	$('#manufacturerId').val(id);
		    	$('#name').val(name);
		    	$('#manufacturerModal').modal();
		    }
		    
		    function saveOrUpdate() {
		    	var name = $('#name').val();
		    	try {
		    		$('.manufacturerName').each(function() {
			    		if($(this).text().toLowerCase() == name.toLowerCase()) {
			    			throw ' 厂商名已存在.';
			    		}
			    	});
		    	} catch(e) {
		    		danger(e);
		    		return false;
		    	}
		    	$.ajax({
		    		url: 'manufacturer/saveOrUpdate',
		    		type: 'POST',
		    		data: $('#manufacturerForm').serialize(),
		    		success: function(data) {
		    			location.reload();
		    		}
		    	});
		    }
	    </script>
	</body>
</html>
