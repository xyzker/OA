package oa.service;

import java.util.List;

import oa.model.LeaveBill;
import oa.model.User;

public interface ILeaveBillService extends IService<LeaveBill> {

	public List<LeaveBill> findAllEntities(User user);

	public void update(Integer id, Integer leaveBillDays,
			String leaveBillContent, String leaveBillRemark);

}
