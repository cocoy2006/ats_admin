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
		<title>管理员</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta charset="utf-8">
		<meta name="viewport" content="width=1000, initial-scale=1.0, maximum-scale=1.0">
		<!-- Loading Bootstrap -->
		<link href="resources/Flat-UI-master/dist/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Loading Flat UI -->
	 	<link href="resources/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	    <link href="resources/jquery.ui/css/main/jquery-ui-1.9.2.custom.css" rel="stylesheet">
		<!--[if IE 7]>
		<link rel="stylesheet" type="text/css" href="resources/jquery.ui/css/main/jquery.ui.1.9.2.ie.css">
		<![endif]-->
		<link href="resources/dynaTree/skin-vista/ui.dynatree.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
		<link rel="shortcut icon" href="favicon.ico">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
		<!--[if lt IE 9]>
		<script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
		<script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
		<![endif]-->
		
	</head>
	
	<body onbeforeunload="return '请点击安全退出按钮!'" >
		<div class="container">
			<div class="row demo-row">
				<div>
					<nav class="navbar navbar-inverse navbar-embossed" role="navigation">
						<div class="collapse navbar-collapse">
							<ul class="nav navbar-nav">
								<li>
									<a href="javascript:void(0);"
			                			onclick="openUnend()">继续录制</a>
								</li>
								<li>
									<a href="javascript:void(0);"
			                			onclick="openUpload()">新脚本</a>
								</li>
								<li class="divider"></li>
								<li>
			                		<a class="startRecordBtn" href="javascript:void(0);" 
			                  			onclick="startRecord()">开始录制</a>
								</li>
								<li>
				                  	<a class="finishRecordBtn" href="javascript:void(0);" 
				                  		onclick="finishRecord()" style="display: none;">结束录制</a>
								</li>
								<li class="dropdown">
						          <a href="#" class="dropdown-toggle" data-toggle="dropdown">常用命令<span class="caret"></span></a>
						          <ul class="dropdown-menu" role="menu">
						            <li><a href="javascript:void(0);" onclick="swipe('left')">
						            	<i class="fa fa-angle-double-left" aria-hidden="true"></i>&nbsp;左滑</a></li>
						            <li><a href="javascript:void(0);" onclick="swipe('right')">
						            	<i class="fa fa-angle-double-right" aria-hidden="true"></i>&nbsp;右滑</a></li>
						            <li><a href="javascript:void(0);" onclick="swipe('up')">
						            	<i class="fa fa-angle-double-up" aria-hidden="true"></i>&nbsp;上滑</a></li>
						            <li><a href="javascript:void(0);" onclick="swipe('down')">
						            	<i class="fa fa-angle-double-down" aria-hidden="true"></i>&nbsp;下滑</a></li>
						          	<li class="divider"></li>
						          	<li><a href="javascript:void(0);" onclick="keyevent(4)">
						            	<i class="fa fa-chevron-left" aria-hidden="true"></i>&nbsp;返回</a></li>
						            <li><a href="javascript:void(0);" onclick="keyevent(3)">
						            	<i class="fa fa-circle-o" aria-hidden="true"></i>&nbsp;桌面</a></li>
						            <li><a href="javascript:void(0);" onclick="keyevent(82)">
						            	<i class="fa fa-bars" aria-hidden="true"></i>&nbsp;菜单</a></li>
						          </ul>
						        </li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<li>
									<a href="cr/release/${device.id }" style="color: #F39C12;">安全退出</a>
								</li>
							</ul>
							<div class="navbar-form navbar-right" role="search">
						        <div class="form-group">
						          	<input type="text" id="typeText" class="form-control" 
						          		placeholder="不支持中文、/、\等特殊字符">
						        </div>
					        	<button class="btn btn-default" onclick="text()">输入</button>
					      	</div>
							<!-- <ul class="nav navbar-nav navbar-right">
								<li>
			                		<a class="startRecordBtn" href="javascript:void(0);" 
			                  			onclick="startRecord()">开始录制</a>
								</li>
								<li>
				                  	<a class="finishRecordBtn" href="javascript:void(0);" 
				                  		onclick="finishRecord()" style="display: none;">结束录制</a>
								</li>
							</ul> -->
						</div><!-- /.navbar-collapse -->
					</nav><!-- /navbar -->
					
					<div class="col-xs-5">
						<div id="screenBox" onMouseDown="start(event)" onMouseUp="finish(event)" style="display: inline-block;">
							<img id="screen" onMouseDown="if(event.preventDefault) event.preventDefault()" onDrag="return false"/>
						</div>
						<!-- <div class="btn-toolbar">
		                	<div class="btn-group">
			                  	<a class="btn btn-default screenBtn" href="javascript:void(0);" 
			                  		onclick="keyevent(4)">
			                  		<span class="fui-triangle-left-large"></span></a>
		                  		<a class="btn btn-default screenBtn" href="javascript:void(0);" 
		                  			onclick="keyevent(3)">
			                  		<span class="fui-radio-unchecked"></span></a>
			                  	<a class="btn btn-default screenBtn" href="javascript:void(0);" 
			                  		onclick="keyevent(82)">
			                  		<span class="fui-list"></span></a>
		                	</div>
		              	</div> --> 
					</div>
					<div class="col-xs-7">
						<h5>脚本记录</h5>
						<div id="scriptBox" style="font-size: 14px;"></div>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
	
		<footer><c:import url="/footer.html" charEncoding="UTF-8"/></footer>
		
		<div id="unendModal" class="modal fade">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">选择未完脚本并继续录制</h4>
					</div>
					<div class="modal-body">
						<p>选择脚本</p>
						<div id="testcaseTree" style="margin-bottom: 15px; max-height: 200px; overflow-y: auto;"></div>
						<p>预览</p>
						<div id="scriptPreview" style="margin-bottom: 15px; max-height: 200px; overflow-y: auto;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="goon()">确认</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		<div id="uploadModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">安装APP并录制脚本</h4>
					</div>
					<div class="modal-body">
						<p>1.选择项目</p>
						<div id="projectTree" style="margin-bottom: 15px; max-height: 200px; overflow-y: auto;"></div>
						<p>2.输入脚本名称</p>
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="text" class="form-control" id="testcaseName" name="testcaseName" />
								</div>
							</div>
						</form>
						<p>3.选择本地apk文件</p>
						<form id="uploadForm" name="uploadForm" enctype="multipart/form-data" 
							target="hiddenUploadFrame" method="post">
							<input type="file" id="file" name="file"
								data-placement="bottom" title="请选择apk文件" />
						</form>
						<iframe name="hiddenUploadFrame" id="hiddenUploadFrame" style="display: none;"></iframe>
						<div class="progress">
							<div class="progress-bar" style="width: 0%;" data-placement="top" title="数据读取中..."></div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="upload()">安装</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
				
		<div id="actionModal" class="modal fade">
			<div class="modal-dialog pull-right" style="top: 50px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">屏幕正常反应后再点击确认</h4>
					</div>
					<div class="modal-body">
						<form id="actionForm" class="form-horizontal" name="actionForm">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									脚本命令
								</label>
								<div id="parsedCommand" class="col-sm-9"></div>
							</div>
							<!--  
							<div class="form-group">
								<label class="col-sm-3 control-label" for="note">
									<em>*</em>&nbsp;备注
								</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="note" name="note" />
								</div>
							</div>
							-->
							<div id="widgets">
							
							</div>
							<input type="hidden" id="action" name="action" />
							<input type="hidden" id="params" name="params" />
						</form>
					</div>
					<div class="modal-footer" style="border-top: none;">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="record()">确认</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/jquery.ui/js/jquery-ui-1.8.16.custom.min.js"></script>
	    <script src="resources/dynaTree/jquery.dynatree.js"></script>
	    <script src="resources/js/coord.js"></script>
	    <script src="resources/js/messager.js"></script>
	    <script src="resources/js/parseCommand.js"></script>
	    <script type="text/javascript">
			var width = ${device.width };
			var height = ${device.height };
			var selectedProjectNode = null;
			var pos1, pos2, time1, time2, action, params;
			var screenQ = 0.9;
			var ssCount = 0;
			
			$(document).ready(function() {
				screen();
		    	screenshot();
			});
			
			function screen() {
				if(width <= 320) {
					
				} else if(width <= 480) {
					screenQ = 0.7;
				} else if(width <= 700) {
					screenQ = 0.5;
				} else if(width <= 1300){
					screenQ = 0.3;
				} else {
					screenQ = 0.1;
				}
				width = width * screenQ;
				height = height * screenQ;
				$("#screenBox").attr("width", width + "px");
				$("#screen").attr("width", width + "px");
//				$(".screenBtn").attr("style", "width: " + width / 3 + "px");
			}
			
		    function screenshot() {
		    	$.ajax({
		    		url: "crRecord/${device.id }/screenshot",
		    		data: "screenQ=" + screenQ,
		    		success: function(data) {	
		    			if(data) {
		    				ssCount = 0;
		    				$("#screen").attr("src", data);
		    			} else {
		    				if(ssCount++ > 10) {
		    					ssCount = 10;
		    				}
		    			}
		    			setTimeout(screenshot, ssCount * 1000);
		    		},
		    		error: function(data) {
		    			setTimeout(screenshot, 5000);
		    		}
		    	});
		    }
		    
		    
		    function start(e) {
		    	pos1 = getXY(e, screenQ); time1 = new Date().getTime();
		    }
		    
		    function finish(e) {
		    	pos2 = getXY(e, screenQ); time2 = new Date().getTime();
		    	if(isNearby(pos1, pos2)) { // tap
		    		action = 'tap';
		    		params = pos1.x + ' ' + pos1.y;
		    	} else { // swipe
		    		action = 'swipe';
		    		params = pos1.x + ' ' + pos1.y + ' ' + pos2.x + ' ' + pos2.y;
		    	}
		    	ajax(action, params);
		    }
		    
		    function swipe(direction) {
		    	ajax('swipe', direction);
		    }
		    
		    function keyevent(keycode) {
				ajax('keyevent', keycode);
		    }
		    
		    function text() {
				ajax('text', $('#typeText').val());
		    }
		    
		    function ajax(action, params) {
		    	var scriptList = null;
		    	if(recordable) {
		    		m_success(" UI解析中，请等待5~10秒. ", 5000);
		    	}
		    	$.ajax({
	    			url: "crRecord/${device.id }/op",
	    			async: false,
	    			data: "recordable=" + recordable + "&action=" + action + "&params=" + params,
	    			dataType: "json",
	    			success: function(data) {
	    				scriptList = data;
	    			}
	    		});
		    	if(recordable) {
		    		if(scriptList == -1) {
		    			toggleRecordable();
		    			danger(" 先选择被测APP ");
		    			return;
		    		}
		    		/*
		    		if(scriptList == -2) {
		    			toggleRecordable();
		    			danger(" 没找到APP(是否被切换至后台?) ");
		    			return;
		    		}
		    		*/
		    		$("#parsedCommand").html(parseCommand(action, params));
		    		$("#action").val(action);
		    		$("#params").val(params);
		    		if(scriptList != null) {
		    			var content = '';
		    			if(scriptList.length == 1) {
		    				var script = scriptList[0];
		    				content +=
		    					'<div class="form-group">' +
									'<label class="col-sm-3 control-label">控件类型</label>' +
									'<div class="col-sm-9">' + script.mclass + '</div>' +
								'</div>' +
								'<div class="form-group">' +
									'<label class="col-sm-3 control-label">控件ID</label>' +
									'<div class="col-sm-9">' + script.mid + '</div>' +
								'</div>' +
								'<input type="hidden" id="num" name="num" value="0" />';
		    			} else {
		    				content += '<p>检测到多个控件，请选择:</p>';
		    				$.each(scriptList, function(index, script) {
			    				content += 
			    					'<div>' +
										'<div class="col-sm-1">' +
											'<input type="radio" name="num" value="' + index + '">' +
										'</div>' +
										'<div class="col-sm-11">' +
											'<div class="form-group">' +
												'<label class="col-sm-2 control-label">类型</label>' +
												'<div class="col-sm-10">' + script.mclass + '</div>' +
											'</div>' +
											'<div class="form-group">' +
												'<label class="col-sm-2 control-label">ID</label>' +
												'<div class="col-sm-10">' + script.mid + '</div>' +
											'</div>' +
										'</div>' +
									'</div>';
			    			});	
		    			}
		    			$('#widgets').html(content);
		    		} 
			    	$('#actionModal').modal();
		    	}
		    }
		    
		    function openUnend() {
		    	loadTestcaseTree();
		    	$('#unendModal').modal();
		    }
		    
		    /* 继续录制 */
		    function goon() {
		    	// testcase selected
		    	if(selectedTestcaseId == 0) {
		    		danger(" 请选择脚本.");
		    		return false;
		    	}
		    	$.ajax({
		    		url: "crRecord/${device.id }/goon/" + selectedTestcaseId,
		    		success: function(data) {
		    			if(data == 1) {
		    				m_success(" 操作成功，进入到指定页面后点击‘开始录制’. ", 2000);
		    				$('#scriptBox').html(scripts);
		    			} else if(data == -3) {
		    				danger( "APP启动失败，通过左侧屏幕手动点击图标启动. ");
		    			}
		    		}
		    	});
		    	$('#unendModal').modal('hide');
		    }
		    
		    function openUpload() {
		    	loadProjectTree();
		    	selectedProjectNode = null
		    	$('#uploadModal').modal();
		    }
		    
		    String.prototype.endWith = function(str) {
			    var reg = new RegExp(str+"$");
			    return reg.test(this);
		    };
		    
		    function upload() {
		    	// project selected
		    	if(selectedProjectNode.data.key == 0) {
		    		danger(" 请选择项目.");
		    		return false;
		    	}
		    	// name of testcase
		    	var exist = false;
		    	var testcaseName = $("#testcaseName").val();
		    	if(!testcaseName) {
		    		danger(" 请输入脚本名称.");
		    		return false;
		    	} else {
		    		if(selectedProjectNode.childList != null && selectedProjectNode.childList.length > 0) {
		    			$.each(selectedProjectNode.childList, function(index, node) {
				    		if(node.data.title == testcaseName) {
				    			exist = true;
				    			return;
				    		}
				    	});
		    		}
		    	}
		    	if(exist) {
		    		danger(" 脚本名称已存在.");
	    			return false;
		    	}
		    	// apk file
		    	var file = $("#file").val();
		    	if(!file || !file.endWith(".apk")) {
		    		danger(" 请选择正确的测试文件. ");
		    		return false;
		    	}
		    	var action = encodeURI("crRecord/${device.id }/upload?projectId=" 
		    			+ selectedProjectNode.data.key + "&testcaseName=" + testcaseName);
		    	$("#uploadForm").attr("action", action).submit();
	    		window.setTimeout(updateProgressbar, 2000);
		    }
		    
		    var updateProgressbarFrequency = 1000; // 进度条的更新频率，单位ms
		    var attempts = 0;
		    function updateProgressbar() {
		    	var bar = $(".progress-bar");
		    	$.ajax( {
		    		url : "crRecord/percentDone",
		    		async : false,
		    		dataType : "json",
		    		success : function(data) {
		    			if(data == -3) {
		    				danger(" apk文件解析失败，请联系管理员.");
		    			} else if (data == null) {
		    				if (attempts++ < 3) {
		    					setTimeout(updateProgressbar, 2000);
		    				} else {
		    					danger(" 网络不稳定，请稍后重试.");
		    					$("#uploadModal").modal('hide');
		    				}
		    			} else {
		    				bar.attr("style", "width:" + data + "%;").html(data + "%");
		    				if (data < 99) {
		    					setTimeout(updateProgressbar, updateProgressbarFrequency);
		    				} else {
		    					$('#uploadModal').modal('hide');
		    					m_success(" 上传成功，请等待APP安装和启动... ", 0);
		    					setTimeout(install, 5000);
		    				}
		    			}
		    		}
		    	});
		    }
		    
		    var installCount = 5;
		    function install() {
		    	$.ajax({
		    		url: "crRecord/${device.id }/install",
		    		success: function(data) {
		    			if(data) {
		    				if(data == "INSTALL_SUCCESS") {
		    					m_success(" 安装成功，请等待APP启动再点击'开始录制'.", 2000);
		    					selectedTestcaseId = 0;
		    					scripts = loadScript();
				    			$('#scriptBox').html(scripts);
		    				} else {
		    					danger(" 安装失败，原因:" + data);
		    				}
		    			} else {
		    				if(installCount++ > 10) {
		    					installCount = 10;
		    				}
		    				setTimeout(install, installCount * 1000);
		    			}
		    		}
		    	});
		    }
		    
		    var recordable = false;
		    function startRecord() {
				toggleRecordable();
		    }
		    
		    function finishRecord() {
				toggleRecordable();
				save();
		    }
		    
		    function toggleRecordable() {
		    	if(recordable) {
		    		recordable = false;
			    	$(".startRecordBtn").show();
			    	$(".finishRecordBtn").hide();
		    	} else {
		    		recordable = true;
			    	$(".startRecordBtn").hide();
			    	$(".finishRecordBtn").show();
		    	}
		    }
		    
		    var selectedTestcaseId = 0;
		    var scripts = '';
		    function loadTestcaseTree() {
		    	$("#testcaseTree").dynatree({
					minExpandLevel : 1, // 1: root node is not collapsible
					initAjax: {url: "testcase/dynatree"},
					selectMode : 1, // 1:single, 2:multi, 3:multi-hier 
					checkbox : true, // Show checkboxes.
					noLink: true, // Use <span> instead of <a> tags for all nodes
					classNames: {checkbox: "dynatree-radio"},
					onSelect: function(flag, dtnode) {
						var selectedNodes = dtnode.tree.getSelectedNodes();
						$.map(selectedNodes, function(node) {
							selectedTestcaseId = node.data.key;
						});
						if(selectedTestcaseId != 0) {
							$('#scriptPreview').empty();
							scripts = loadScript();
			    			$('#scriptPreview').html(scripts);
						}
					}
				});
		    }
		    
		    function loadScript() {
		    	scripts = '';
		    	$.ajax({
		    		url: "script/all/" + selectedTestcaseId,
		    		async: false,
		    		success: function(data) {
		    			scripts = 
		    				'<table class="table table-hover table-bordered">' +
			    				'<tr>' +
									'<th>步骤</th>' +
									'<th>操作</th>' +
									'<th>控件类型</th>' +
//									'<th>备注</th>' +
								'</tr>';
		    			if(data && data.length > 0) {
		    				$.each(data, function(index, script) {
		    					scripts += 
		    						'<tr>' +
		    							'<td>' + script.step + '</td>' +
		    							'<td>' + parseCommand(script.action, script.params) + '</td>';
		    					if(script.mclass) {
		    						scripts += 
		    							'<td>' + script.mclass + '</td>';
		    					} else {
		    						scripts += 
		    							'<td style="background-color: #eaeded;"></td>';
		    					}
		    					scripts += 
//		    							'<td>' + script.note + '</td>' +
		    						'</tr>';
		    				});
		    			} else {
		    				scripts += 
	    						'<tr>' +
	    							'<td colspan="4">无内容</td>' +
	    						'</tr>';
		    			}
		    			scripts += '</table>';
		    		}
		    	});
		    	return scripts;
		    }
		    
		    
		    function loadProjectTree() {
		    	$("#projectTree").dynatree({
					minExpandLevel : 1, // 1: root node is not collapsible
					initAjax: {url: "project/dynatree"},
					selectMode : 1, // 1:single, 2:multi, 3:multi-hier 
					checkbox : true, // Show checkboxes.
					noLink: true, // Use <span> instead of <a> tags for all nodes
					classNames: {checkbox: "dynatree-radio"},
					onSelect : function(flag, dtnode) {
						var selectedNodes = dtnode.tree.getSelectedNodes();
						$.map(selectedNodes, function(node) {
							selectedProjectNode = node;
						});
					}
				});
		    }
		    
		    function record() {
		    	/*
		    	var note = $("#note").val();
		    	if(!note) {
		    		danger("备注不能为空.");
		    		return false;
		    	}
		    	*/
		    	$.ajax({
		    		url: "crRecord/${device.id }/record",
		    		data: $("#actionForm").serialize(),
		    		type: "POST",
		    		success: function(data) {	
		    			if(data == "1") { // success
		    				m_success(" 录制成功. ", 2000);
		    				$('#scriptBox').empty();
		    				scripts = loadScript();
			    			$('#scriptBox').html(scripts);
		    			}
		    			$('#actionModal').modal('hide');
		    		}
		    	});
		    }
		    
		    function save() {
		    	$.ajax({
		    		url: "testcase/save",
		    		success: function(data) {
		    			if(data == "1") { // success
		    				m_success(" 保存成功.", 2000);
		    			} else {
		    				danger(" 请先安装APP.");
		    			}
		    		}
		    	});
		    }
	    </script>
	</body>
</html>