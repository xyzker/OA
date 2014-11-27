package oa.service.impl;

import java.util.List;

import oa.model.LeaveBill;
import oa.model.User;
import oa.service.ILeaveBillService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("leaveBillService")
@Transactional
public class LeaveBillServiceImpl extends ServiceImpl<LeaveBill> implements ILeaveBillService {

	public List<LeaveBill> findAllEntities(User user) {
		return dao.list("from LeaveBill l where l.user = ?", user);
	}

	public void update(Integer id, Integer leaveBillDays,
			String leaveBillContent, String leaveBillRemark) {
		dao.executeUpdateByHQL("update LeaveBill l set l.days = ?, l.content = ?, l.remark = ? " +
				"where l.id = ?", 
				leaveBillDays, leaveBillContent, leaveBillRemark, id);
	}

}
