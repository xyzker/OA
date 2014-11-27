package oa.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import oa.model.LeaveBill;
import oa.model.User;
import oa.service.ILeaveBillService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class LeaveBillAction extends ActionSupport {
	private static final long serialVersionUID = 2961160307212082285L;
	
	@Resource
	private ILeaveBillService leaveBillService;
	
	private LeaveBill leaveBill;
	private List<LeaveBill> leaveBillList = new ArrayList<LeaveBill>();
	
	private Integer leaveBillDays;
	private String leaveBillContent;
	private String leaveBillRemark;
	
	/**
	 * 查询自己的请假单的信息
	 */
	@Action(value="/leaveBill/list")
	public String list(){
		User user = (User)ActionContext.getContext().getSession().get("user");
		leaveBillList = leaveBillService.findAllEntities(user);
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
		if(leaveBill.getId() == null){		//此时为添加新用户, 由于id为Integer类型，所以判断为null，而不是0
			User user = (User)ActionContext.getContext().getSession().get("user");
			leaveBill.setUser(user);
			leaveBillService.saveOrUpdate(leaveBill);
		}
		leaveBillService.update(leaveBill.getId(), leaveBillDays, leaveBillContent, leaveBillRemark);
		return SUCCESS;
	}
	
	@Action(value="/leaveBill/delete", results={@Result(type="redirectAction", location="list")})
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

	public void setLeaveBillList(List<LeaveBill> leaveBillList) {
		this.leaveBillList = leaveBillList;
	}

	public List<LeaveBill> getLeaveBillList() {
		return leaveBillList;
	}

	public void setLeaveBillDays(Integer leaveBillDays) {
		this.leaveBillDays = leaveBillDays;
	}

	public Integer getLeaveBillDays() {
		return leaveBillDays;
	}

	public void setLeaveBillContent(String leaveBillContent) {
		this.leaveBillContent = leaveBillContent;
	}

	public String getLeaveBillContent() {
		return leaveBillContent;
	}

	public void setLeaveBillRemark(String leaveBillRemark) {
		this.leaveBillRemark = leaveBillRemark;
	}

	public String getLeaveBillRemark() {
		return leaveBillRemark;
	}

}
