package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.VersionPO;

public class VersionDAOImpl extends BaseDAOImpl<VersionPO> implements IVersionDAO<VersionPO> {
	private static final Logger LOG = Logger.getLogger(VersionDAOImpl.class);
	public VersionDAOImpl() {
		super(VersionPO.class);
	}
	
	@Override
	public List<VersionPO> findByVersionNumber(String versionNumber) {
		LOG.trace("Inside findByVersionNumber. versionNumber = " + versionNumber);
		
		final String findByVersionNumberQuery = "select po from VersionPO po "
				+ "where versionNumber = :versionNumber ";
		Query q = getEntityManager().createQuery(findByVersionNumberQuery);
		q.setParameter("versionNumber", versionNumber);

		return executeQuery(q);
	}
}
