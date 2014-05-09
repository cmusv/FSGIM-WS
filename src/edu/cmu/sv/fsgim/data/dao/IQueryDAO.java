package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.po.QueryPO;

public interface IQueryDAO<PO extends BasePO> extends IBaseDAO<PO> {
	List<QueryPO> find(QueryPO po);
}
