package oa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * ��ٵ�
 */
@Entity
public class LeaveBill {
	@Id
	@GeneratedValue
	private Integer id;//����ID
	private Integer days;// �������
	private String content;// �������
	private Date leaveDate = new Date();// ���ʱ��
	private String remark;// ��ע
	
	@ManyToOne
	private User user;// �����
	
	private Integer state=0;// ��ٵ�״̬ 0��ʼ¼��,1.��ʼ����,2Ϊ�������

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
