package edu.cmu.sv.fsgim.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.QueryPO;
import edu.cmu.sv.fsgim.data.util.EMF;

public class QueryDAOImpl implements IQueryDAO {
	private static final Logger LOG = Logger.getLogger(QueryDAOImpl.class);

	@Override
	public QueryPO save(QueryPO po) {
		LOG.trace(po);

		if (po == null) {
			LOG.warn("Nothing to save as PO is NULL.");
			return null;
		}

		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// If the object already exists in the system
			// merge changes
			if (po.getId() != null && po.getId() != 0) {
				em.merge(po);
			} else { // Else, create a new object
				em.persist(po);
			}
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}

		LOG.trace(po);

		return po;
	}

	@Override
	public QueryPO findById(long id) {
		LOG.trace(id);

		final String findByIdQuery = "select po from QueryPO po where id = ?";

		Query q = getEntityManager().createQuery(findByIdQuery);
		q.setParameter(1, id);
		List<QueryPO> pos = executeQuery(q);

		QueryPO po = null;
		if (pos != null && pos.size() > 0) {
			po = pos.get(0);
		}

		return po;
	}

	@Override
	public List<QueryPO> findAll() {
		final String findAllQuery = "select po from QueryPO po";

		return executeQuery(findAllQuery);
	}

	@Override
	public List<QueryPO> find(QueryPO po) {
		if(po == null) return new ArrayList<QueryPO>();
		
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

	protected List<QueryPO> executeQuery(String queryStr) {
		Query q = getEntityManager().createQuery(queryStr);
		@SuppressWarnings("unchecked")
		List<QueryPO> pos = q.getResultList();

		return pos;
	}

	protected List<QueryPO> executeQuery(Query q) {
		@SuppressWarnings("unchecked")
		List<QueryPO> pos = q.getResultList();

		return pos;
	}

	protected final EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}
}
