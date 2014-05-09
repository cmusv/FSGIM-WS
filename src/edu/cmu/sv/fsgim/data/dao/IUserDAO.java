package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.po.UserPO;

public interface IUserDAO<PO extends BasePO> extends IBaseDAO<PO> {
	List<UserPO> findByUserName(String userName);
}
