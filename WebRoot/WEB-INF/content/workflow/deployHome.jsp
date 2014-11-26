<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
    <title>部署管理</title>
    <%@ include file="/WEB-INF/content/public/commons.jspf" %>
</head>
<body>
 
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 部署管理信息列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="10%">ID</td>
				<td width="60%">流程名称</td>
				<td width="20%">发布时间</td>
				<td width="10%">相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="departmentList">
        <s:if test="depList==null || depList.size()==0">
        	没有任何部署管理信息！
        </s:if>
        <s:iterator value="depList">
			<tr class="TableDetail1 template">
				<td>${id}&nbsp;</td>
				<td>${name}&nbsp;</td>
				<td><s:date name="deploymentTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td>
					<s:a action="deleteDeployment?deployment.id=%{id}" namespace="/workflow" onclick="return window.confirm('确认删除吗？')">删除</s:a>
				</td>
			</tr>
		</s:iterator>	
			
        </tbody>
    </table>
</div>

<br/>
<br/>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 流程定义信息列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="12%">ID</td>
		        <td width="18%">名称</td>
		        <td width="10%">流程定义的KEY</td>
		        <td width="10%">流程定义的版本</span></div></td>
		        <td width="15%">流程定义的规则文件名称</td>
		        <td width="15%">流程定义的规则图片名称</td>
		        <td width="10%">部署ID</td>
		        <td width="10%">相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="departmentList">
        <s:if test="pdList==null || pdList.size()==0">
        	没有任何流程定义信息！
        </s:if>
        <s:iterator value="pdList">
			<tr class="TableDetail1 template">
				<td>${id}&nbsp;</td>
				<td>${name}&nbsp;</td>
				<td>${key}&nbsp;</td>
				<td>${version}&nbsp;</td>
				<td>${resourceName}&nbsp;</td>
				<td>${diagramResourceName}&nbsp;</td>
				<td>${deploymentId}&nbsp;</td>
				<td>
					<a target="_blank" href="/workflow/viewImage?deploymentId=<s:property value="deploymentId"/>&imageName=<s:property value="diagramResourceName"/>">查看流程图</a> 
				</td>
			</tr>
		</s:iterator>	
			
        </tbody>
    </table>
</div>

<br/>
<br/>

<!-- 发布流程 -->
<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<s:url value='/style/images/title_arrow.gif'/>"/> 部署流程定义
        </div>
        <div id="Title_End"></div>
    </div>
</div>


<!--显示表单内容-->
<div id=MainArea>
    <s:form action="newdeploy" namespace="/workflow" enctype="multipart/form-data" method="POST">
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                	<tr>
                	流程名称：<s:textfield name="filename" cssStyle="width: 200px;"/><br/>
					流程文件:<s:file name="file" cssStyle="width: 200px;"/><br/>
					<input type="submit" value="上传流程" style="cursor:pointer;"/>
                    </tr>
                </table>
            </div>
        </div>
    </s:form>
</div>



<!--说明-->	
<div id="Description"> 
	说明：只接受zip扩展名的文件。
</div>

</body>
</html>
