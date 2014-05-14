package edu.cmu.sv.fsgim.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.log4j.Logger;

import edu.cmu.sv.fsgim.business.dto.User;
import edu.cmu.sv.fsgim.data.dao.IUserDAO;
import edu.cmu.sv.fsgim.data.dao.UserDAOImpl;
import edu.cmu.sv.fsgim.data.po.UserPO;

@Path("/users")
public class UserManager {
	private static final Logger LOG = Logger.getLogger(UserManager.class);
	private IUserDAO<UserPO> dao = new UserDAOImpl();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User addUser(User user) {
		LOG.trace(user);

		List<UserPO> existingPOs = dao.findByUserName(user.getUserName());
		LOG.trace("Existing POs = " + existingPOs);

		// If the user name is already taken, raise an exception.
		if (existingPOs != null && existingPOs.size() > 0) {
			LOG.warn("Username is already taken.");
			throw new RuntimeException(
					"Username already taken. Please pick a different name");
		}

		// Prepare the PO to be saved
		UserPO po = new UserPO();
		po.setUserName(user.getUserName());
		po.setCreatedTime(new Date());
		po.setCreatedBy("admin");

		try {
			byte[] salt = PasswordEncryptionService.generateSalt();
			byte[] encPassword = PasswordEncryptionService
					.getEncryptedPassword(user.getPassword(), salt);
			po.setSalt(salt);
			po.setPassword(encPassword);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOG.error("Problem when getting salt or when encrypting password.",
					e);
			throw new RuntimeException(e);
		}

		// Persist the User
		po = dao.save(po);
		user.setId(po.getId());

		LOG.trace(user);
		return user;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "users")
	public List<User> findAll() {
		LOG.trace("Inside findAll");

		List<User> users = new ArrayList<User>();

		List<UserPO> pos = dao.findAll();
		for (UserPO po : pos) {
			User user = new User();
			user.setId(po.getId());
			user.setUserName(po.getUserName());

			users.add(user);
		}

		LOG.trace("Exiting findAll method with: " + users);

		return users;
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteUser(@PathParam("id") String id) {
		LOG.trace("Inside delete method, ID = " + id);
		long userId = 0L;
		try {
			userId = Long.parseLong(id);
		} catch (Exception e) {
			throw new RuntimeException("Error when parsing user ID provided.",
					e);
		}

		boolean isDeleted = dao.delete(userId);

		// If there is something wrong, and the query did not get deleted,
		// raise an exception and report it.
		if (!isDeleted) {
			throw new RuntimeException(
					"There was a problem when deleting the user. "
							+ "Please check GAE server log.");
		}

		LOG.trace("Exiting delete method");
	}
}
