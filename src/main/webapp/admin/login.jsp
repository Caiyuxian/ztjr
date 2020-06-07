<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta charset="utf-8" />
		<title>中天金融管理系统</title>
		<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css" />
	    <link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css" />
	    <link rel="stylesheet" type="text/css" href="../resources/css/common.css" />
	    <script type="text/javascript" src="../resources/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
	</head>
	<body class="easyui-layout" id="layout" style="">
	<div id="login" class="easyui-window" buttons="#dlg-btns" style="padding-top: 5px;padding-left: 15px" >
		<form id="loginForm" method="post" action="#">
			<table>
			<tr>
				<td>
				<table>
				<tr><td>用户名</td><td><input  class="easyui-validatebox"   id="userName" name="username" type="text"/></td><td></td></tr>
				<tr></tr>
				<tr></tr>
				<tr><td>密 码</td><td><input  class="easyui-validatebox"  id="passWord" name="password" type="password"></td><td></td></tr>
				<div style="text-align: center;color: red;" id="showMsg"></div>
				</table>
			</td>
			<td>
			<img src="../resources/img/head.jpg" style="width:95px;height:130px"/>
			</td>
			</tr>
		</table>
	</form>
	</div>
	</body>
	
	<script type="text/javascript">
		$(function() {
			$("#login").dialog({
				title : '登录',
				backcolor:'#00f',
				iconCls : 'icon-lock',	
				width : '420',
				height : '230',
				closable : false,
				minimizable : false,
				maximizable : false,
				resizable : false,
				modal : true,
				buttons : [ {
					text : '登录',
					iconCls : 'icon-ok',
					handler:function(){
					doLogin();
					}
				} ]
			});
		});
		function doLogin(){
			if($("input[name='username']").val()=="" || $("input[name='password']").val()==""){
				$("#showMsg").html("用户名或密码为空，请输入");
				$("input[name='login']").focus();
			}else{
				var name = $("input[name='username']").val();
				var pwd = $("input[name='password']").val();
				$.ajax({
					type: "POST",
					url: "<%=basePath%>login.do",
					data: {'username':name,'password':pwd},
					dataType: "json",
					success: function(data){
						console.log(data);
						if(data == "1"){
							$("#showMsg").html("用户名或密码错误");
						}else{
							window.location.href="<%=basePath%>main.do";
							$("#login").dialog("close");
						}
					}
				});
				// $("#loginForm").get(0).submit();
			}
		}
	</script>
</html>
