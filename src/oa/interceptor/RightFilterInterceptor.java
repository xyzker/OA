package oa.interceptor;

import java.util.List;

import oa.model.User;
import oa.util.DataUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class RightFilterInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7918143213426544935L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String url = DataUtil.getUrlByAction(invocation);
		//����Ҫ���Ƶ�URLֱ�ӷ���
		List<String> allPrivilegeUrls = (List<String>)ActionContext
						.getContext().getApplication().get("allPrivilegeUrls");
		if( !allPrivilegeUrls.contains(url)){
			return invocation.invoke();
		}
		//��ǰ��¼�û�
		User user = (User)ActionContext.getContext().getSession().get("user");
		//δ��½
		if(user == null){
			return "login";
		}
		if(user.hasPrivilegeByUrl(url)){
			return invocation.invoke();
		}
		else{
			return "error_no_right";
		}
	}

}
