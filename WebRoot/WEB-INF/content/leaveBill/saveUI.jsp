<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<title>请假管理</title>
    <%@ include file="/WEB-INF/content/public/commons.jspf" %>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 新增/修改请假申请
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">

    <s:form action="saveOrUpdate" namespace="/leaveBill">
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
                        <td><s:textfield name="leaveBillDays" value="%{leaveBill.days}"  cssClass="InputStyle" /> *</td>
                    </tr>
                    <tr>
                        <td width="100">请假原因</td>
                        <td><s:textfield name="leaveBillContent" value="%{leaveBill.content}"  cssClass="InputStyle" /> *</td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td><s:textarea name="leaveBillRemark" value="%{leaveBill.remark}" cssClass="TextareaStyle"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="<s:url value='/style/images/save.png'/>"/>
            <a href="javascript:history.go(-1);"><img src="<s:url value='/style/images/goBack.png'/>"/></a>
        </div>
    </s:form>
</div>

</body>
</html>

