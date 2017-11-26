<%@ page language="java" pageEncoding="UTF-8"%>
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
	    <link href="resources/Flat-UI-master/docs/assets/css/demo.css" rel="stylesheet">
	    <link rel="shortcut icon" href="favicon.ico">
	    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
	    <!--[if lt IE 9]>
	      <script src="resources/Flat-UI-master/dist/js/vendor/html5shiv.js"></script>
	      <script src="resources/Flat-UI-master/dist/js/vendor/respond.min.js"></script>
	    <![endif]-->
	</head>
	  
	<body>
		<div class="container">
			<div class="login">
		        <div class="login-screen">
		          <div class="login-icon">
		            <img src="resources/Flat-UI-master/img/login/icon.png" />
		            <h4>欢迎登录 <small>云管理平台</small></h4>
		          </div>
		
		          <div class="login-form">
		            <div class="form-group">
		              <input type="text" id="username" class="form-control login-field" value="" placeholder="username" />
		              <label class="login-field-icon fui-user" for="login-name"></label>
		            </div>
		
		            <div class="form-group">
		              <input type="password" id="password" class="form-control login-field" value="" placeholder="password" />
		              <label class="login-field-icon fui-lock" for="login-pass"></label>
		            </div>
		
		            <a class="btn btn-primary btn-lg btn-block" href="javascript:void(0);" onclick="login()">登&nbsp;录</a>
		            <!-- <a class="login-link" href="#">忘记密码?</a> -->
		          </div>
		        </div>
		      </div>
		</div>
		
		<script src="resources/Flat-UI-master/dist/js/vendor/jquery.min.js"></script>
	    <script src="resources/Flat-UI-master/dist/js/flat-ui.min.js"></script>
	    <script src="resources/Flat-UI-master/docs/assets/js/application.js"></script>
	    <script src="resources/js/md5.js"></script>
	    <script type="text/javascript">
	    	function login() {
	    		var username = $("#username").val();
	    		var password = $("#password").val();
	    		if(!username || !password) {
	    			alert(" 用户名和密码不能为空.");
	    			return false;
	    		} else {
	    			var md5Password = faultylabs.MD5(password);
	    			$("#password").val(md5Password);
	    			$.ajax({
		    			url: "user/login/" + username + "/" + md5Password,
		    			dataType: "json",
		    			success: function(data) {
		    				if(data == 1) {
		    					location = "center/ctDevice";
		    				} else {
		    					alert(" 用户名或密码错误.");
		    					$("#username").focus();
		    				}
		    			}
		    		});
	    		}
	    	}
	    </script>
 	</body>
</html>
