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
<title>用户信息</title>
</head>
<body>

<!-- 弹出框 -->
<div class="easyui-form" title="查看" iconCls="icon-save" modal="true"
	closed="true" id="dlg">
	<form id="extinfo" method="post">
		<div class="fitem">
			<label>家庭住址：</label>
			<input class="easyui-textbox" editable="false" value="" id="address"/>
			<label>芝麻分：</label>
			<input class="easyui-textbox"  editable="false" value="" id="alipayscore"/>
		</div>
		<div class="fitem">
			<label>工作单位：</label>
			<input class="easyui-textbox" editable="false" value="" id="company"/>
			<label>工作地址：</label>
			<input class="easyui-textbox" editable="false" value="" id="workaddr"/>
		</div>
		<div class="fitem">
			<label>紧急联系人1：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user1"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone1"/>
		</div>
		<div class="fitem">
			<label>紧急联系人2：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user2"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone2"/>
		</div>
		<div class="fitem">
			<label>紧急联系人3：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user3"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone3"/>
		</div>
		<div class="fitem">
			<label>身份证号码：</label>
			<input class="easyui-textbox" editable="false" value="" id="idcard"/>
		</div>
		<div class="fitem">
			<label>身份证照1：</label>
			<img src="img/logo.png" id="idcard1">
		</div>
		<div class="fitem">
			<label>身份证照2：</label>
			<img src="img/logo.png" id="idcard2">
		</div>
		<div class="fitem">
			<label>身份证照3：</label>
			<img src="img/logo.png" id="idcard3">
		</div>
	</form>
</div>
<!-- /弹出框 -->

<script type="text/javascript">
	$(function() {
		//打开弹出框
		$("#editBtn").click(function(e) {
			var row = $('#userlist').datagrid('getSelected');
			if (row){
				$.ajax({
		             type: "POST",
		             url: "/ztjr/admin/getUserInfo.do",
		             data: {'userId':row.id},
		             dataType: "json",
		             success: function(data){
		            	 $('#address').textbox('setValue',data['address']);
		            	 $('#alipayscore').textbox('setValue',data['alipay_score']);
		            	 $('#workaddr').textbox('setValue',data['workAddr']);
		            	 $('#company').textbox('setValue',data['company']);
		            	 $('#ct_user1').textbox('setValue',data['ct_user1']);
		            	 $('#ct_user2').textbox('setValue',data['ct_user2']);
		            	 $('#ct_user3').textbox('setValue',data['ct_user3']);
		            	 $('#ct_phone1').textbox('setValue',data['ct_phone1']);
		            	 $('#ct_phone2').textbox('setValue',data['ct_phone2']);
		            	 $('#ct_phone3').textbox('setValue',data['ct_phone3']);
		            	 $('#idcard').textbox('setValue',data['IDcard']);
		            	 $('#idcard1').attr("src","resources/"+data['idCardRight']);
		            	 $('#idcard2').attr("src","resources/"+data['idCardRight']);
		            	 $('#idcard3').attr("src","resources/"+data['idCardRight']);
		             }
		         });
				e.preventDefault();
				$("#dlg").dialog("open");
			}else{
				alert('未选中行');
			}
		});
	});
</script>

</body>
</html>