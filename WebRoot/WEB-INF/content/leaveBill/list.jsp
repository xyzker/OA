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
				<td width="5%">请假状态</td>
				<td width="25%">相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="departmentList">
        
        <s:iterator value="departmentList">
			<tr class="TableDetail1 template">
				<td><s:a action="list?parent.id=%{id}" namespace="/department">${name}</s:a>&nbsp;</td>
				<td>${parent.name}&nbsp;</td>
				<td>${description}&nbsp;</td>
				<td>
					<s:a action="delete?department.id=%{id}&parent.id=%{parent.id}" namespace="/department" onclick="return window.confirm('这将删除所有的下级部门，您确定要删除吗？')">删除</s:a>
					<s:a action="editUI?department.id=%{id}" namespace="/department">修改</s:a>
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
