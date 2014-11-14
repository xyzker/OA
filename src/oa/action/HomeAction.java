package oa.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class HomeAction extends ActionSupport  {
	private static final long serialVersionUID = -6486564633921033953L;
	
	@Action(value="/home/index")
	public String index(){
		return SUCCESS;
	}
	
	@Action(value="/home/top")
	public String top(){
		return SUCCESS;
	}
	
	@Action(value="/home/left")
	public String left(){
		return SUCCESS;
	}
	
	@Action(value="/home/right")
	public String right(){
		return SUCCESS;
	}
	
	@Action(value="/home/bottom")
	public String bottom(){
		return SUCCESS;
	}

}
