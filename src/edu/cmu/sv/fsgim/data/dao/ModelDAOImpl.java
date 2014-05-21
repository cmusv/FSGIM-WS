package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.ModelPO;

public class ModelDAOImpl extends BaseDAOImpl<ModelPO> implements
		IModelDAO<ModelPO> {
	private static final Logger LOG = Logger.getLogger(ModelDAOImpl.class);

	public ModelDAOImpl() {
		super(ModelPO.class);
	}
	
	@Override
	public List<ModelPO> findByName(String modelName) {
		LOG.trace("Inside findByName. modelName = " + modelName);

		final String findByNameQuery = "select po from ModelPO po "
				+ "where modelName = :modelName ";
		Query q = getEntityManager().createQuery(findByNameQuery);
		q.setParameter("modelName", modelName);

		return executeQuery(q);
	}
}
