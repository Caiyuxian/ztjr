<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<table class="easyui-datagrid" title="借款单列表" fit="true" fitColumns="true"
	url="<%=basePath %>admin/getallbill.do" method="get" toolbar="#toolbar_bill" 
	striped="true" rownumbers="true" pagination="true" remoteSort="false" data-options="singleSelect:true" id="billlist">
	<thead>
		<tr>
			<th field="billno" width="10%" align="center" sortable="true">单据编号</th>
			<th field="username" width="5%" align="center" sortable="true">借款人</th>
			<th field="phone" width="5%" align="center" sortable="true">手机号</th>
			<th field="createTime" formatter="formatDate" width="10%" align="center" sortable="true">申请时间</th>
			<th field="amount" width="8%" align="center" sortable="true">借款额度</th>
			<th field="borrowingCycle" formatter="formatCycle" width="8%" align="center" sortable="true">借款期限</th>
			<th field="preauditName" width="5%" align="center" sortable="true">初审人</th>
			<th field="preauditTime" formatter="formatDate" width="10%" align="center" sortable="true">初审时间</th>
			<th field="reauditName" width="5%" align="center" sortable="true">再审人</th>
			<th field="reauditTime" formatter="formatDate" width="10%" align="center" sortable="true">再审时间</th>
			<th field="loanStatus" formatter="formatstatus" width="5%" align="center" sortable="true">单据状态</th>
			<th field="paybackTime" width="8%" align="center" sortable="true">应还款时间</th>
			<th field="isPayBack" formatter="formatIspayback" width="5%" align="center" sortable="true">是否还款</th>
			<th field="id" width="5%" align="center" sortable="true" hidden="true">单据ID</th>
			<th field="userid" width="5%" align="center" sortable="true" hidden="true">用户ID</th>
		</tr>
	</thead>
</table>
<!-- /数据表 -->

<!-- 数据表工具栏 -->
<div class="toolbar" id="toolbar_bill">
	<div class="search-div">
		<label>单据编码：</label>
		<input type="text" class="easyui-textbox" id="s_billno"/>
		<label>手机号码：</label>
		<input type="text" class="easyui-textbox" id="s_phone" />
		<label>单据状态：</label>
		<select id="bill_status" class="easyui-combobox">
			<option value="0">全部</option>
			<option value="1">初审中</option>
			<option value="2">再审中</option>
			<option value="3">放款中</option>
			<option value="4">已放款</option>
			<option value="5">初审不通过</option>
			<option value="6">再审不通过</option>
		</select>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
	</div>
	<div class="ctrl-div">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="checkinfo">审查资料</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="preaudit">初审</a>
	<c:if test="${role == '2'}">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="reaudit">再审</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="loan">放款</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="payback">还款</a>
	</c:if>
	</div>
</div>
<!-- /数据表工具栏 -->


<!-- 弹出框 -->
<div class="easyui-dialog" title="资料查看" style="width:45%;height:80%;padding-left: 100px;" iconCls="icon-save" modal="true"
	 closed="true" id="dlg_userinfo">
	<form id="extinfo" method="post">
		<div class="fitem">
			<label>姓名：</label>
			<input class="easyui-textbox" editable="false" value="" id="idcardname"/>
			<label>身份证号码：</label>
			<input class="easyui-textbox" editable="false" value="" id="idcard"/>
		</div>
		<div class="fitem">
			<label>手机号：</label>
			<input class="easyui-textbox" editable="false" value="" id="phone"/>
			<label>芝麻分：</label>
			<input class="easyui-textbox"  editable="false" value="" id="alipayscore"/>
		</div>
		<div class="fitem">
			<label>居住地址：</label>
			<input class="easyui-textbox" editable="false" value="" id="address"/>
		</div>
		<div class="fitem">
			<label>工作单位：</label>
			<input class="easyui-textbox" editable="false" value="" id="company"/>
		</div>
		<div class="fitem">
			<label>工作地址：</label>
			<input class="easyui-textbox" editable="false" value="" id="workaddr"/>
		</div>
		<div class="fitem">
			<label>备用联系人1：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user1"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone1"/>
		</div>
		<div class="fitem">
			<label>备用联系人2：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user2"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone2"/>
		</div>
		<div class="fitem">
			<label>备用联系人3：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_user3"/>
			<label>电话：</label>
			<input class="easyui-textbox" editable="false" value="" id="ct_phone3"/>
		</div>
		<div class="fitem">
			<label>证件照：</label>
		</div>
		<div >
			<img src="/resources/img/head.jpg" width="500" max-width="500" border="0" id="idcard1">
		</div>
		<div >
			<img src="/resources/img/head.jpg" width="500" max-width="500" border="0" id="idcard2">
		</div>
		<div >
			<img src="/resources/img/head.jpg" width="500" max-width="500" border="0" id="idcard3">
		</div>
	</form>
</div>
<style type="text/css">
	div img{
		margin-left: 20px;
	}
</style>
<!-- /弹出框 -->

<!-- 弹出框 初审 -->
<div class="easyui-dialog" title="初审" iconCls="icon-save" modal="true"
	closed="true" buttons="#dlg-preaudit" id="dlg_pre">
	<form id="fm_reaudit" method="post">
		<div class="fitem">
			<label>审核状态：</label>
				<select id="preaudit_status" class="easyui-combobox" defaulvalue="通过">
				   <option value="1">通过</option>
				   <option value="0">不通过</option>
				</select> 
		</div>
	</form>
</div>
<!-- /弹出框 -->

<!-- 弹出框按钮组-->
<div id="dlg-preaudit">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="p_sbtn">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="p_cbtn">取消</a>
</div>
<!-- /弹出框 按钮组-->

<!-- 弹出框 初审 -->
<div class="easyui-dialog" title="再审" iconCls="icon-save" modal="true"
	closed="true" buttons="#dlg-reaudit" id="dlg_re">
	<form id="fm_preaudit" method="post">
		<div class="fitem">
			<label>额度：</label>
			<input class="easyui-textbox" value="" id="amount"/>
		</div>
		<div class="fitem">
			<label>借款期限：</label>
			<select id="borrowcycle" class="easyui-combobox" defaulvalue="七天">
			   <option value="1">七天</option>
			   <option value="2">十天</option>
			   <option value="3">半个月</option>
			   <option value="4">一个月</option>
			</select> 
		</div>
		<div class="fitem">
			<label>审核状态：</label>
			<select id="reaudit_status" class="easyui-combobox" defaulvalue="通过">
				<option value="1">通过</option>
				<option value="0">不通过</option>
			</select>
		</div>
	</form>
</div>
<!-- /弹出框 -->

<!-- 弹出框按钮组-->
<div id="dlg-reaudit">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="r_sbtn">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="r_cbtn">取消</a>
</div>
<!-- /弹出框 按钮组-->

<script type="text/javascript">
	$(function() {
		//打开初审弹出框
		$("#preaudit").click(function(e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				e.preventDefault();
				$("#dlg_pre").dialog("open");
			}else{
				messageShow("未选中数据行", 2000, "show");
			}
		});
		//关闭初审弹出框
		$("#p_cbtn").click(function(e) {
			e.preventDefault();
			$("#dlg_pre").dialog("close");
		});
		//初审保存
		$("#p_sbtn").click(function (e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$.ajax({
					type: "POST",
					url: "<%=basePath%>admin/preaudit.do",
					data: {'billId':row.id, 'status':$('#preaudit_status').combobox('getValue')},
					dataType: "json",
					success: function(data){
						if(data == "1"){
							e.preventDefault();
							$("#dlg_pre").dialog("close");
							messageShow("审核成功", 2000, "show");
							$('#billlist').datagrid('reload');
						}else if(data == "2"){
							messageShow("该单据已审核过", 2000, "show");
						}
					},
					error:function(){
						messageShow("系统发生错误", 3000, "show");
					}
				});
			}
		});
		//再审弹出框
		$("#reaudit").click(function(e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$('#amount').textbox('setValue', row.amount);
				$('#borrowcycle').combobox('setValue', row.borrowingCycle);
				e.preventDefault();
				$("#dlg_re").dialog("open");
			}else{
				alert('未选中行');
			}
		});
		//再审保存
		$("#r_sbtn").click(function(e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$.ajax({
					type: "POST",
					url: "<%=basePath%>admin/reaudit.do",
					data: {'billId':row.id, 'status':$('#reaudit_status').combobox('getValue'), 'amount':$('#amount').textbox('getValue'), 'cycle':$('#borrowcycle').combobox('getValue')},
					dataType: "json",
					success: function(data){
						if(data == "1"){
							e.preventDefault();
							$("#dlg_re").dialog("close");
							messageShow("审核成功", 2000, "show");
							$('#billlist').datagrid('reload');
						}else if(data == "2"){
							messageShow("该单据已审核过", 2000, "show");
						}else if(data == "3"){
							messageShow("该单据还未初审，请先初审", 2000, "show");
						}
					},
					error:function(){
						messageShow("系统发生错误", 3000, "show");
					}
				});
			}
		});
		//关闭再审弹出框
		$("#r_cbtn").click(function(e) {
			e.preventDefault();
			$("#dlg_re").dialog("close");
		});
		//放款
		$('#loan').click(function (e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$.ajax({
					type: "POST",
					url: "<%=basePath%>admin/loan.do",
					data: {'billId':row.id},
					dataType: "json",
					success: function(data){
						if(data == "1"){
							messageShow("放款成功", 2000, "show");
							$('#billlist').datagrid('reload')
						}else if(data == "2"){
							messageShow("只有放款中的单据才能进行放款操作", 2000, "show");
						}
					},
					error:function(){
						messageShow("系统发生错误", 3000, "show");
					}
				});
			}else{
				messageShow("未选中数据行", 2000, "show");
			}
		});
		//还款
		$('#payback').click(function (e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$.ajax({
					type: "POST",
					url: "<%=basePath%>admin/payback.do",
					data: {'billId':row.id},
					dataType: "json",
					success: function(data){
						if(data == "1"){
							messageShow("还款成功", 2000, "show");
							$('#billlist').datagrid('reload');
						}else if(data == "2"){
							messageShow("只有已放款的单据才能进行还款操作", 2000, "show");
						}else if(data == "3"){
							messageShow("该单据已还款", 2000, "show");
						}
					},
					error:function(){
						messageShow("系统发生错误", 3000, "show");
					}
				});
			}else{
				messageShow("未选中数据行", 2000, "show");
			}
		});
		//审查资料
		$("#checkinfo").click(function(e) {
			var row = $('#billlist').datagrid('getSelected');
			if (row){
				$.ajax({
					type: "POST",
					url: "<%=basePath%>admin/getUserInfo.do",
					data: {'userId':row.userid},
					dataType: "json",
					success: function(data){
						console.log(data)
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
						$('#idcard').textbox('setValue',data['idcard']);
						$('#idcardname').textbox('setValue',data['idCardName']);
						$('#idcard1').attr('src',"resources/"+data['idCardRight']);
						$('#idcard2').attr('src',"resources/"+data['idCardBack']);
						$('#idcard3').attr('src',"resources/"+data['idCardByHand']);
					},
					error:function(){
						messageShow("系统发生错误", 3000, "show");
					}
				});
				e.preventDefault();
				$("#dlg_userinfo").dialog("open");
			}else{
				messageShow("未选中数据行", 2000, "show");
			}
		});
	});
	function formatCycle(value, rec){
		return value=="1"?"7天":value=="2"?"10天":value=="3"?"15天":value=="4"?"30天":"数据错误";
	};
	function formatIspayback(value, rec){
		return value=="0"?"否":"是";
	};
	function formatstatus(value){
		return value=="1"?"初审中":value=="2"?"再审中":value=="3"?"放款中":value=="4"?"已放款":value=="5"?"初审不通过":value=="6"?"再审不通过":"数据错误";
	};
	function doSearch(){
		$('#billlist').datagrid('load',{
			billno: $('#s_billno').val(),
			phone: $('#s_phone').val(),
			billstatus:$('#bill_status').combobox('getValue')
		});
	};
</script>

</body>
</html>