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
			<div class="panel-heading">
				<a href="javascript:void(0);" onclick="loadManufacturer()">返回上一级</a>
				<button class="btn btn-primary btn-sm pull-right" title="添加型号" 
               		onclick="openModal(this, -1, '', '', '', '')">
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
			  			<td>型号名</td>
			  			<td>分辨率</td>
			  			<td>操作系统</td>
			  			<td style="width: 140px;"></td>
			  		</tr>
			    	<c:if test="${modelList != null}">
			    		<c:forEach var="model" items="${modelList }" varStatus="s">
			    			<tr>
					    		<td>
					    			<input type="checkbox" name="ids" value="${model.id}" id="checkbox${s.index}"
					            		onclick="cancelCheckAll('checkboxAll', 'ids')">
					    		</td>
					    		<td class="modelName">${model.name }</td>
					    		<td>${model.width }*${model.height }</td>
					    		<td>${model.os }</td>
					    		<td>
					    			<button class="btn btn-info btn-xs" title="编辑型号"
										onclick="openModal(this, ${model.id}, '${model.name }', ${model.width }, ${model.height }, '${model.os }')">
				                  		<span class="fui-new"></span>&nbsp;编辑</button>
					    		</td>
					    	</tr>
			    		</c:forEach>
			    	</c:if>
			  	</table>
		  	</div>
		</div>
		
		<!-- modal -->
		<div id="modelModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">型号</h4>
					</div>
					<div class="modal-body">
						<form id="modelForm" class="form-horizontal" name="modelForm">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="name">
									型号名
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="name" name="name" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="width">
									分辨率
								</label>
								<div class="col-sm-9">
									<div class="col-sm-6" style="padding-left: 0;">
										<div class="input-group">
											<span class="input-group-btn">
										        <button class="btn btn-default" type="button">宽</button>
									      	</span>
									      	<input class="form-control" type="text" id="width" name="width" required>
									    </div><!-- /input-group -->
									</div>
									<div class="col-sm-6" style="padding-left: 0; padding-right: 0;">
										<div class="input-group">
											<span class="input-group-btn">
										        <button class="btn btn-default" type="button">高</button>
									      	</span>
									      	<input class="form-control" type="text" id="height" name="height" required>
									    </div><!-- /input-group -->
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="os">
									操作系统
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="os" name="os" value=""/>
									不支持Android 4.0以下版本
								</div>
							</div>
							<input type="hidden" id="modelId" name="modelId" value="-1"/>
							<input type="hidden" id="manufacturerId" name="manufacturerId" value="${manufacturer.id }"/>
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
			$(document).ready(function() {
				$('#modelModal').on('hidden.bs.modal', function (e) {
					loadModel(${manufacturer.id });
				});
			});
		
		    function openModal(obj, id, name, width, height, os) {
		    	$('#modelModal h4').html(obj.title);
		    	$('#modelId').val(id);
		    	$('#name').val(name);
		    	$('#width').val(width);
		    	$('#height').val(height);
		    	$('#os').val(os);
		    	$('#modelModal').modal();
		    }
		    
		    function saveOrUpdate() {
		    	// name exist or not
				var name = $('#name').val();
		    	try {
		    		$('.modelName').each(function() {
						if($(this).text().toLowerCase() == name.toLowerCase()) {
							throw '型号名已存在.';
						}
					});
		    	} catch(e) {
		    		danger(e);
		    		return false;
		    	}
				// width
				var width = $('#width').val();
				if(!width || !$.isNumeric(width) || width <= 0) {
					danger('宽度数据不合法');
					return false;
				}
				// height
				var height = $('#height').val();
				if(!height || !$.isNumeric(height) || height <= 0) {
					danger('高度数据不合法');
					return false;
				}
				$.ajax({
					url: 'model/saveOrUpdate',
					type: 'POST',
					data: $('#modelForm').serialize(),
					dataType: 'json',
					success: function(data) {
						$('#modelModal').modal('hide');
					}
				});
		    }
	    </script>
	</body>
</html>
