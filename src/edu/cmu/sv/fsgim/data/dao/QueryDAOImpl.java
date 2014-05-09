package edu.cmu.sv.fsgim.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.QueryPO;

public class QueryDAOImpl extends BaseDAOImpl<QueryPO> implements
		IQueryDAO<QueryPO> {
	private static final Logger LOG = Logger.getLogger(QueryDAOImpl.class);

	public QueryDAOImpl() {
		super(QueryPO.class);
	}

	@Override
	public List<QueryPO> find(QueryPO po) {
		LOG.trace("Inside find method: po=" + po);
		
		if (po == null)
			return new ArrayList<QueryPO>();

		final String findByNameQuery = "select po from QueryPO po "
				+ "where modelName = :modelName "
				+ "and modelVersion = :modelVersion "
				+ "and queryClassification = :queryClassification "
				+ "and queryName = :queryName ";
		Query q = getEntityManager().createQuery(findByNameQuery);
		q.setParameter("modelName", po.getModelName());
		q.setParameter("modelVersion", po.getModelVersion());
		q.setParameter("queryClassification", po.getQueryClassification());
		q.setParameter("queryName", po.getQueryName());

		return executeQuery(q);
	}
}
