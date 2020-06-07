<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 数据表 -->
<table class="easyui-datagrid" title="管理人员列表" fit="true" fitColumns="true"
	url="<%=basePath %>admin/getAllManager.do" method="get" toolbar="#toolbar_admin" 
	striped="true" rownumbers="true" pagination="true" remoteSort="false" data-options="singleSelect:true" id="adminlist">
	<thead>
		<tr>
			<th field="id" width="20" align="center" sortable="true" hidden="true">id</th>
			<th field="username" width="20" align="center" sortable="true">用户名</th>
			<th field="role" formatter="formatRole" width="20" align="center" sortable="true">角色</th>
			<th field="createtime" formatter="formatDate" width="20" align="center" sortable="true">创建时间</th>
		</tr>
	</thead>
</table>
<!-- /数据表 -->

<!-- 数据表工具栏 -->
<div class="toolbar" id="toolbar_admin">
	<div class="ctrl-div">
   		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addadmin">新增</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="reset">重置密码</a>
	</div>
</div>
<!-- /数据表工具栏 -->

<!-- 弹出框 新增-->
<div class="easyui-dialog" title="新增/编辑" iconCls="icon-save" modal="true"
	closed="true" buttons="#dlg-btns" id="adduser_dlg">
	<form id="fm" method="post">
		<div class="fitem">
			<label>用户名：</label>
			<input class="easyui-textbox" value="" id="username"/>
		</div>
		<div class="fitem">
			<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
			<input class="easyui-textbox" value="" type="password" id="password"/>
		</div>
		<div class="fitem">
			<label>角&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
			<select class="easyui-combobox" id="role">
				<option value="2">客服</option>
				<option value="3">老板</option>
			</select>
		</div>
	</form>
</div>
<!-- /弹出框 -->
<!-- 弹出框按钮组-->
<div id="dlg-btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="m_saveBtn">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="m_cancelBtn">取消</a>
</div>
<!-- /弹出框 按钮组-->

<!-- 弹出框 重置密码-->
<div class="easyui-dialog" title="重置密码" iconCls="icon-save" modal="true"
	closed="true" buttons="#dlg-pwd" id="reset_dlg">
	<form id="fm" method="post">
		<div class="fitem">
			<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
			<input class="easyui-textbox" value="" type="password" id="resetpwd"/>
		</div>
	</form>
</div>
<!-- /弹出框 -->
<!-- 弹出框按钮组-->
<div id="dlg-pwd">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="pwd_saveBtn">重置</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="pwd_cancelBtn">取消</a>
</div>
<!-- /弹出框 按钮组-->


<script type="text/javascript">
	$(function() {
		//打开弹出框
		$("#addadmin").click(function(e) {
			e.preventDefault();
			$("#adduser_dlg").dialog("open");
		});
		//关闭弹出框
		$("#m_cancelBtn").click(function(e) {
			e.preventDefault();
			$("#adduser_dlg").dialog("close");
		});
		//保存
		$('#m_saveBtn').click(function(e){
			var name = document.getElementById('username').value;
			var pwd = document.getElementById('password').value;
			var role = document.getElementById('role').value;
			$.ajax({
	             type: "POST",
	             url: "<%=basePath%>admin/addmanager.do",
	             data: {'username':name,'password':pwd,'role':role},
	             dataType: "json",
	             success: function(data){
	            	 if(data == "1"){
	            		 messageShow("添加成功", 2000, "show");
	            		 $('#adminlist').datagrid('reload');
	            	 }else if(data =="2"){
	            		 messageShow("该用户名已存在", 3000, "show");
	            	 }else{
	            		 messageShow("添加失败", 2000, "show");
	            	 }
	            	 e.preventDefault();
	            	 $("#adduser_dlg").dialog("close");
	            	 $('#adduser_dlg').datagrid('reload');
	            	 
	             }
			});
		});
		//重置密码
		$('#pwd_saveBtn').click(function(e){
			var row = $('#adminlist').datagrid('getSelected');
			var pwd = document.getElementById('resetpwd').value;
			$.ajax({
	             type: "POST",
	             url: "<%=basePath%>admin/resetpwd.do",
	             data: {'password':pwd,'userid':row.id},
	             dataType: "json",
	             success: function(data){
	            	 if(data == "1"){
	            		 messageShow("密码重置成功", 2000, "show");
	            	 }else{
	            		 messageShow("重置失败", 2000, "show");
	            	 }
	            	 e.preventDefault();
	            	 $("#reset_dlg").dialog("close");
	             },
	             error:function(){
	            	 messageShow("系统错误", 3000, "show");
	             }
			});
			
		});
		//打开弹出框
		$("#reset").click(function(e) {
			var row = $('#adminlist').datagrid('getSelected');
			if(row){
				e.preventDefault();
				$("#reset_dlg").dialog("open");
			}else{
				messageShow("未选中数据行", 2000, "show");
			}
		});
		//关闭弹出框
		$("#pwd_cancelBtn").click(function(e) {
			e.preventDefault();
			$("#reset_dlg").dialog("close");
		});
	});
	function formatRole(value, row, index){
		return value=="1"?"管理员":value=="2"?"老板":"客服";
	};
</script>

</body>
</html>