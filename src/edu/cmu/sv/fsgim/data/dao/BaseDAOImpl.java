package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.util.EMF;

public class BaseDAOImpl<PO extends BasePO> implements IBaseDAO<PO> {
	private static final Logger LOG = Logger.getLogger(BaseDAOImpl.class);
	private Class<PO> poClass;

	public BaseDAOImpl(Class<PO> po) {
		this.poClass = po;
	}

	@Override
	public PO save(PO po) {
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
	public PO findById(long id) {
		return findById(id, getEntityManager());
	}

	public PO findById(long id, EntityManager em) {
		LOG.trace(id);

		final String findByIdQuery = "select po from " + getPOClassName()
				+ " po where id = :id";
		Query q = em.createQuery(findByIdQuery);
		q.setParameter("id", id);
		List<PO> pos = executeQuery(q);

		PO po = null;
		if (pos != null && pos.size() > 0) {
			po = pos.get(0);
		}

		return po;
	}

	@Override
	public List<PO> findAll() {
		final String findAllQuery = "select po from " + getPOClassName()
				+ " po";

		return executeQuery(findAllQuery);
	}

	@Override
	public boolean delete(long id) {
		boolean deleted = false;
		EntityManager em = getEntityManager();
		try {
			PO po = findById(id, em);
			// This is just a fail safe check.
			// Ideally this is not NULL at all times.
			if (po != null) {
				em.getTransaction().begin();
				em.remove(po);
				em.getTransaction().commit();
			}
			deleted = true;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}

		return deleted;
	}

	protected List<PO> executeQuery(String queryStr) {
		Query q = getEntityManager().createQuery(queryStr);
		@SuppressWarnings("unchecked")
		List<PO> pos = q.getResultList();

		return pos;
	}

	protected List<PO> executeQuery(Query q) {
		@SuppressWarnings("unchecked")
		List<PO> pos = q.getResultList();

		return pos;
	}

	protected final EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

	protected final String getPOClassName() {
		return poClass.getSimpleName();
	}
}
