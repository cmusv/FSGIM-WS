package edu.cmu.sv.fsgim.business.service;

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

import edu.cmu.sv.fsgim.business.dto.Version;
import edu.cmu.sv.fsgim.common.ConverterUtils;
import edu.cmu.sv.fsgim.data.dao.IVersionDAO;
import edu.cmu.sv.fsgim.data.dao.VersionDAOImpl;
import edu.cmu.sv.fsgim.data.po.VersionPO;

@Path("/versions")
public class VersionManager {
	private static final Logger LOG = Logger.getLogger(VersionManager.class);
	private IVersionDAO<VersionPO> dao = new VersionDAOImpl();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Version addVersion(Version version) {
		LOG.trace("Inside addVersion method with; version = " + version);

		List<VersionPO> versions = dao.findByVersionNumber(
				version.getModelName(), version.getVersionNumber());
		LOG.trace("Existing versions = " + versions);

		VersionPO po = null;

		// If the version number is already taken, update the description.
		if (versions != null && versions.size() > 0) {
			LOG.warn("Version specified already exists. Updating description ...");
			Version tempDTO = ConverterUtils.convert(versions.get(0));
			tempDTO.setModifiedBy("admin");
			tempDTO.setModifiedTime(new Date());

			po = ConverterUtils.convert(tempDTO);
			po.setDescription(version.getDescription());
		} else {
			po = ConverterUtils.convert(version);
		}

		// Persist the User
		po = dao.save(po);
		version.setId(po.getId());

		LOG.trace("Exiting addVersion method with; version = " + version);
		return version;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "versions")
	public List<Version> findAll() {
		LOG.trace("Inside findAll");

		List<Version> versions = new ArrayList<Version>();

		List<VersionPO> pos = dao.findAll();
		for (VersionPO po : pos) {
			versions.add(ConverterUtils.convert(po));
		}

		LOG.trace("Exiting findAll method with: " + versions);

		return versions;
	}

	@DELETE
	@Path("/{id}")
	public void deleteVersion(@PathParam("id") String id) {
		LOG.trace("Inside delete method, ID = " + id);
		long versionId = 0L;
		try {
			versionId = Long.parseLong(id);
		} catch (Exception e) {
			throw new RuntimeException("Error when parsing version ID provided.",
					e);
		}

		boolean isDeleted = dao.delete(versionId);

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
