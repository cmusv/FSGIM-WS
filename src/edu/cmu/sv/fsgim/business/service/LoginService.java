package edu.cmu.sv.fsgim.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.business.dto.User;
import edu.cmu.sv.fsgim.data.dao.IUserDAO;
import edu.cmu.sv.fsgim.data.dao.UserDAOImpl;
import edu.cmu.sv.fsgim.data.po.UserPO;

@Path("/login")
public class LoginService {
	private static final Logger LOG = Logger.getLogger(LoginService.class);
	private IUserDAO<UserPO> dao = new UserDAOImpl();
	private static final String INVALID_CREDS = "Invalid Credentials. Please try again.";

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User login(User user) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		LOG.trace("Inside login for user: " + user);
		if (user == null) {
			return null;
		}

		List<UserPO> users = dao.findByUserName(user.getUserName());
		if (users == null || users.size() == 0) {
			LOG.error("Invalid user. There is no account with that user name.");
			throw new RuntimeException(INVALID_CREDS);
		}

		UserPO po = users.get(0);
		boolean isValidUser = PasswordEncryptionService.authenticate(
				user.getPassword(), po.getPassword(), po.getSalt());

		if (isValidUser) {
			user.setId(po.getId());
			LOG.info("User: " + user.getUserName() + " logged in successfully.");
		} else {
			LOG.error("Invalid user. Wrong password provided for user name.");
			throw new RuntimeException(INVALID_CREDS);
		}

		return user;
	}
}
