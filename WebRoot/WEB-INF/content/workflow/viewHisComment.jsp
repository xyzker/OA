<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>请假任务办理</title>
    <%@ include file="/WEB-INF/content/public/commons.jspf" %>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 请假申请的任务办理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">

    <s:form action="submitTask" namespace="/workflow">
    	<s:hidden name="taskId"></s:hidden>
    	<s:hidden name="leaveBill.id"></s:hidden>
    
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="<s:url value='/style/blue/images/item_point.gif'/>" /> 岗位信息 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                	<tr>
                        <td width="50">请假天数</td>
                        <td><s:textfield name="leaveBill.days" disabled="true" cssClass="InputStyle" /> </td>
                    </tr>
                    <tr>
                        <td width="100">请假原因</td>
                        <td><s:textfield name="leaveBill.content" disabled="true" cssClass="InputStyle" /> </td>
                    </tr>
                    <tr>
                        <td>请假	备注</td>
                        <td><s:textarea name="leaveBill.remark" disabled="true" cssClass="TextareaStyle"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div align="middle">
        	<input type="button" name="button" value="返回" class="button_ok" onclick="javascript:history.go(-1);"/>
        </div>
    </s:form>
</div>

<br/>
<br/>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 显示请假申请的批注信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="15%">时间</td>
				<td width="10%">批注人</td>
				<td width="75%">批注信息</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" >
        <s:if test="commentList == null || commentList.size()== 0">
       		<div style="color:red">暂时没有批注信息！</div>
       	</s:if>
        <s:iterator value="commentList">
			<tr class="TableDetail1 template">
				<td><s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td><s:property value="userId"/>&nbsp;</td>
				<td><s:property value="fullMessage"/>&nbsp;</td>
			</tr>
		</s:iterator>	
			
        </tbody>
    </table>
    
</div>

</body>
</html>

