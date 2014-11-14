package oa.util;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class DataUtil {
	public static String getUrlByAction(ActionInvocation invocation){
		ActionProxy proxy = invocation.getProxy();
		String nameSpace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if(!ValidateUtil.isValid(nameSpace) || nameSpace.equals("/")){
			nameSpace = "";
		}
		String url =  nameSpace + "/" + actionName;		//得到权限url
		return url;
	}
	
}
