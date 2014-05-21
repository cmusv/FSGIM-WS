package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.VersionPO;

public class VersionDAOImpl extends BaseDAOImpl<VersionPO> implements
		IVersionDAO<VersionPO> {
	private static final Logger LOG = Logger.getLogger(VersionDAOImpl.class);

	public VersionDAOImpl() {
		super(VersionPO.class);
	}

	@Override
	public List<VersionPO> findByVersionNumber(String modelName,
			String versionNumber) {
		LOG.trace("Inside findByVersionNumber. modelName = " + modelName
				+ ", versionNumber = " + versionNumber);

		final String findByVersionNumberQuery = "select po from VersionPO po "
				+ "where modelName = :modelName and versionNumber = :versionNumber ";
		Query q = getEntityManager().createQuery(findByVersionNumberQuery);
		q.setParameter("modelName", modelName);
		q.setParameter("versionNumber", versionNumber);

		return executeQuery(q);
	}
}
