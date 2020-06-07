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
<table class="easyui-datagrid" title="用户列表" fit="true" fitColumns="true"
	url="<%=basePath %>admin/getuserlist.do" method="get" toolbar="#toolbar_user" 
	striped="true" rownumbers="true" pagination="true" remoteSort="false" data-options="singleSelect:true" id="userlist">
	<thead>
		<tr>
			<th field="id" width="20" align="center" sortable="false" hidden="true">id</th>
			<th field="phone" width="20" align="center" sortable="true">用户手机号</th>
			<th field="createtime" formatter="formatDate"width="20" align="center" sortable="true">注册时间</th>
		</tr>
	</thead>
</table>
<!-- /数据表 -->

<!-- 数据表工具栏 -->
<div class="toolbar" id="toolbar_user">
	<div class="search-div">
		<label>手机号码：</label>
		<input type="text" class="easyui-textbox"  id="phonenum"/>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
    </div>
	<div class="ctrl-div">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" id="export">导出联系人</a>
	</div>
</div>
<!-- /数据表工具栏 -->

<script type="text/javascript">
	$(function() {
		//导出联系人
		$('#export').click(function(e){
			var row = $('#userlist').datagrid('getSelected');
			if (row){
				var url = "<%=basePath%>admin/export.do?userid="+row.id;
				var a = document.createElement('a');
			    a.href=url;
			    a.click();
			}else{
				alert('未选中行');
			}
		});
	});
	function doSearch(){
		$('#userlist').datagrid('load',{
			phone:$('#phonenum').val()
		});
	};
</script>

</body>
</html>