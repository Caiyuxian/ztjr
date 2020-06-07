<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta charset="utf-8" />
		<title>零花钱管理系统</title>
		<link rel="stylesheet" type="text/css" href="resources/css/themes/default/easyui.css" />
	    <link rel="stylesheet" type="text/css" href="resources/css/themes/icon.css" />
	    <link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	    <script type="text/javascript" src="resources/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	</head>
	<body class="easyui-layout" id="layout" style="visibility:hidden;">
		
		<div region="north" id="header">
			<img src="resources/img/logo.png" class="logo" />
			<div class="top-btns">
				<span>欢迎您，${user.username}</span>
				<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-lock'" id="changepwd">修改密码</a>
				<a href="<%=basePath %>logout.do" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-clear'">退出系统</a>
			</div>
		</div>
		
		<div region="west" split="true" title="导航菜单" id="naver">
			<div class="easyui-accordion" fit="true" id="navmenu">
				<c:if test="${user.role=='1'}">
				<div title="管理人员">
					<ul class="navmenu">
						<li><a href="#" data-url="<%=basePath %>admin/showmanager.do">管理人员列表</a></li>
					</ul>
				</div>
				</c:if>
				<c:if test="${user.role!='1'}">
				<div title="用户管理">
					<ul class="navmenu">
						<!--<li class="active"><a href="#">首页</a></li>	-->
						<li><a href="#" data-url="<%=basePath %>admin/showuser.do">用户列表</a></li>
					</ul>
				</div>
				<div title="借款单管理">
					<ul class="navmenu">
						<li><a href="#" data-url="<%=basePath %>admin/showloanbill.do">借款单列表</a></li>
					</ul>
				</div>
				</c:if>
			</div>
		</div>
		
		<div region="center" id="content">
			<div class="easyui-tabs" fit="true" id="tt">
				
				<div title="首页" ><!-- iconCls="icon-ok" -->
					<div class="easyui-accordion" data-options="fit:true">
						<div style="font-size: 16px;padding: 20px 0 0 20px">欢迎使用本系统</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<div region="south" id="footer">零花钱后台管理系统 V1.0</div>
		
		<!-- 弹出框 修改密码-->
		<div class="easyui-dialog" title="修改" iconCls="icon-save" modal="true"
			closed="true" buttons="#dlg-btns" id="dlg_chpwd">
			<form id="fm" method="post">
				<div class="fitem">
					<label>原密码：</label>
					<input class="easyui-textbox" value="" type="password" id="oldpwd"/>
				</div>
				<div class="fitem">
					<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
					<input class="easyui-textbox" value="" type="password" id="newpwd"/>
				</div>
				<div class="fitem">
					<label>确认密码：</label>
					<input class="easyui-textbox" value="" type="password" id="newpwd2"/>
				</div>
				<div class="fitem" style="padding-left:15px;color:red;" id="msg"></div>
			</form>
		</div>
		<!-- /弹出框 -->

		<!-- 弹出框按钮组-->
		<div id="dlg-btns">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="saveBtn_pwd">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBtn_pwd">取消</a>
		</div>
		<!-- /弹出框 按钮组-->
		
		<script type="text/javascript">
			$(function() {
				//添加新的Tab页
				$("#navmenu").on("click", "a[data-url]", function(e) {
					e.preventDefault();
					var tabTitle = $(this).text();
					var tabUrl = $(this).data("url");
					
					if($("#tt").tabs("exists", tabTitle)) { //判断该Tab页是否已经存在
						$("#tt").tabs("select", tabTitle);
					}else {
						$("#tt").tabs("add", {
							title: tabTitle,
							href: tabUrl,
							closable: true
						});
					}
					$("#navmenu .active").removeClass("active");
					$(this).parent().addClass("active");
				});
				
				//解决闪屏的问题
				window.setTimeout(function() {
					$("#layout").css("visibility", "visible");
				}, 800);
				//打开弹出框
				$("#changepwd").click(function(e) {
					e.preventDefault();
					$("#dlg_chpwd").dialog("open");
				});
				//关闭弹出框
				$("#cancelBtn_pwd").click(function(e) {
					e.preventDefault();
					$("#dlg_chpwd").dialog("close");
				});
				$("#saveBtn_pwd").click(function(e) {
					var pwdold = document.getElementById('oldpwd').value;
					var pwdnew = document.getElementById('newpwd').value;
					var pwdnew2 = document.getElementById('newpwd2').value;
					if(pwdold =="" || pwdnew=="" || pwdnew2 ==""){
						$('#msg').html("请输入密码");
					}else if(pwdnew != pwdnew2){
						$('#msg').html("两次密码不一致");
					}else{
						$.ajax({
							type: "POST",
							url: "<%=basePath%>admin/changepwd.do",
							data: {'pwdold':pwdold,'pwdnew':pwdnew},
							dataType: "json",
							success: function(data){
								if(data == "1"){
									$("#msg").html("旧密码输入错误");
								}else{
									messageShow("密码修改成功，3秒后将跳转登录页面重新登录", 3000, "show");
									$("#dlg_chpwd").dialog("close");
									setTimeout('window.location.href="<%=basePath%>/admin/login.jsp"',3000);//修改完密码重新登录
								}
							}
						});
					}
				});
			});
			function formatDate(value, row, index ){
				if(value != undefined && null != value){
					var date = new Date(value + 8 * 3600 * 1000); // 增加8小时
					return date.toJSON().substr(0, 19).replace('T', ' ');
				}
			};
			function messageShow(msg, timeout, showtype){
				$.messager.show({
					title: '提示信息',
					msg: msg,
					timeout: timeout,
					showType: showtype,
					style:{
						right:'', 
	                    top:document.body.scrollTop+document.documentElement.scrollTop+50, 
	                    bottom:'' 
					}
					});
			};
		</script>
	</body>
</html>
