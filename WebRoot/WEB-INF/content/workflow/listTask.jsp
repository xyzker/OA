<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
    <title>任务管理</title>
    <%@ include file="/WEB-INF/content/public/commons.jspf" %>
    <script type="text/javascript">
    	$(function(){
    		if($("#message").html() == "complete"){
    			window.parent.parent.TopMenu.location.reload(true);
    		}
    	});
    </script>
</head>
<body>

<!-- 辅助信息 --> 
<div id="message" style="display: none">${message}</div>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/>个人任务管理列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="15%">任务ID</td>
				<td width="25%">任务名称</td>
				<td width="20%">创建时间</td>
				<td width="20%">办理人</td>
				<td width="20%">相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer">
        
        <s:if test="taskList==null || taskList.size()==0">
        	<div style="color:red">没有任何任务管理信息！</div>
        </s:if>
        <s:iterator value="taskList">
			<tr class="TableDetail1 template">
				<td>${id}&nbsp;</td>
				<td>${name}&nbsp;</td>
				<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td><s:property value="assignee != null ? assignee : '尚未指定,您可以拾取任务'"/>&nbsp;</td>
				<td>
					<s:a action="viewTaskForm?taskId=%{id}" namespace="/workflow">办理任务</s:a>
					<a target="_blank" href="${pageContext.request.contextPath}/workflow/viewCurrentImage?taskId=<s:property value="id"/>">查看当前流程图</a>
				</td>
			</tr>
		</s:iterator>	
			
        </tbody>
    </table>
    
</div>

</body>
</html>
