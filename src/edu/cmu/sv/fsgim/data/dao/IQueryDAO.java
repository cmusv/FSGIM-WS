package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.QueryPO;

public interface IQueryDAO {
	QueryPO save(QueryPO po);

	QueryPO findById(long id);

	List<QueryPO> findAll();
	
	List<QueryPO> find(QueryPO po);
	
	boolean delete(long id);
}
