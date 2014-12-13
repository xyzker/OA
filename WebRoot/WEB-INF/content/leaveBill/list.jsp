<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
    <title>请假管理</title>
    <%@ include file="/WEB-INF/content/public/commons.jspf" %>
</head>
<body>
 
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 请假管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="5%">ID</td>
				<td width="10%">请假人</td>
				<td width="5%">请假天数</td>
				<td width="15%">请假事由</td>
				<td width="20%">请假备注</td>
				<td width="15%">请假时间</td>
				<td width="10%">请假状态</td>
				<td width="20%">相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer">
        <s:if test="leaveBillList==null || leaveBillList.size()==0">
        	<div style="color:red">您尚无任何请假单信息！</div>
        </s:if>
        <s:iterator value="leaveBillList">
			<tr class="TableDetail1 template">
				<td>${id}&nbsp;</td>
				<td>${user.loginName}&nbsp;</td>
				<td>${days}&nbsp;</td>
				<td>${content}&nbsp;</td>
				<td>${remark}&nbsp;</td>
				<td><s:date name="leaveDate" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td>
					<s:if test="state==0">
			        			初始录入
			        		</s:if>
			 				<s:elseif test="state==1">
			 					审核中
			 				</s:elseif>
			 				<s:else>
			 					审核完成
			 				</s:else>
				</td>
				<td>
					<s:if test="state==0">
	        			<s:a action="editUI?leaveBill.id=%{id}" namespace="/leaveBill" >修改</s:a>
						<s:a action="delete?leaveBill.id=%{id}" namespace="/leaveBill"  onclick="return confirm('确定要删除吗？')" >删除</s:a>
	        			<s:a action="startProcess?leaveBillId=%{id}" namespace="/workflow" >申请请假</s:a>
	        		</s:if>
	 				<s:elseif test="state==1">
	 					<s:a action="viewHisComment?leaveBill.id=%{id}" namespace="/workflow" >查看审核记录</s:a>
	 				</s:elseif>
	 				<s:else>
						<s:a action="delete?leaveBill.id=%{id}" namespace="/leaveBill"  onclick="return confirm('确定要删除吗？')" >删除</s:a>
						<s:a action="viewHisComment?leaveBill.id=%{id}" namespace="/workflow" >查看审核记录</s:a>
	 				</s:else>
				</td>
			</tr>
		</s:iterator>	
			
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
		<div id="TableTail_inside">
			<s:a action="addUI" namespace="/leaveBill"><img src="<s:url value='/style/images/createNew.png' />" /></s:a>
        </div>
    </div>
</div>

</body>
</html>
