package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.data.po.UserPO;

public class UserDAOImpl extends BaseDAOImpl<UserPO> implements
		IUserDAO<UserPO> {
	private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);
	public UserDAOImpl() {
		super(UserPO.class);
	}

	@Override
	public List<UserPO> findByUserName(String userName) {
		LOG.trace("Inside findByUserName; userName = " + userName); 
		
		final String findByNameQuery = "select po from UserPO po "
				+ "where userName = :userName ";
		Query q = getEntityManager().createQuery(findByNameQuery);
		q.setParameter("userName", userName);

		return executeQuery(q);
	}
}
