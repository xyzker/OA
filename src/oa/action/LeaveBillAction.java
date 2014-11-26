package oa.action;

import javax.annotation.Resource;

import oa.model.LeaveBill;
import oa.service.ILeaveBillService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class LeaveBillAction extends ActionSupport {
	private static final long serialVersionUID = 2961160307212082285L;
	
	@Resource
	private ILeaveBillService leaveBillService;
	
	private LeaveBill leaveBill;
	
	@Action(value="/leaveBill/list")
	public String list(){
		return SUCCESS;
	}
	
	@Action(value="/leaveBill/addUI", results={@Result(location="/WEB-INF/content/leaveBill/saveUI.jsp")})
	public String addUI(){
		return SUCCESS;
	}
	
	@Action(value="/leaveBill/editUI", results={@Result(location="/WEB-INF/content/leaveBill/saveUI.jsp")})
	public String editUI(){
		leaveBill = leaveBillService.get(leaveBill.getId());
		return SUCCESS;
	}
	
	@Action(value="/leaveBill/saveOrUpdate", results={@Result(type="redirectAction", location="list")})
	public String saveOrUpdate(){
		return SUCCESS;
	}
	
	@Action(value="/role/delete", results={@Result(type="redirectAction", location="list")})
	public String delete(){
		leaveBill = leaveBillService.get(leaveBill.getId());
		leaveBillService.delete(leaveBill);
		return SUCCESS;
	}

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public ILeaveBillService getLeaveBillService() {
		return leaveBillService;
	}

	public void setLeaveBill(LeaveBill leaveBill) {
		this.leaveBill = leaveBill;
	}

	public LeaveBill getLeaveBill() {
		return leaveBill;
	}

}
