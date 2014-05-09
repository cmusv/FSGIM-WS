package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.BasePO;

public interface IBaseDAO<PO extends BasePO> {
	PO save(PO po);

	PO findById(long id);

	List<PO> findAll();
	
	boolean delete(long id);
}
