package oa.service.impl;

import oa.model.LeaveBill;
import oa.service.ILeaveBillService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("leaveBillService")
@Transactional
public class LeaveBillServiceImpl extends ServiceImpl<LeaveBill> implements ILeaveBillService {

}
