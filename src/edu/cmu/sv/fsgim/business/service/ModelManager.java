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

import edu.cmu.sv.fsgim.business.dto.Model;
import edu.cmu.sv.fsgim.common.ConverterUtils;
import edu.cmu.sv.fsgim.data.dao.IModelDAO;
import edu.cmu.sv.fsgim.data.dao.ModelDAOImpl;
import edu.cmu.sv.fsgim.data.po.ModelPO;

@Path("/models")
public class ModelManager {
	private static final Logger LOG = Logger.getLogger(ModelManager.class);
	private IModelDAO<ModelPO> dao = new ModelDAOImpl();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Model addModel(Model model) {
		LOG.trace("Inside addModel. model = " + model);

		List<ModelPO> models = dao.findByName(model.getModelName());
		LOG.trace("Existing models = " + models);

		ModelPO po = null;

		// If the model number is already taken, update the description.
		if (models != null && models.size() > 0) {
			LOG.warn("Model specified already exists. Updating description ...");
			Model tempDTO = ConverterUtils.convert(models.get(0));
			tempDTO.setModifiedBy("admin");
			tempDTO.setModifiedTime(new Date());

			po = ConverterUtils.convert(tempDTO);
			po.setDescription(model.getDescription());
		} else {
			po = ConverterUtils.convert(model);
		}

		// Persist the User
		po = dao.save(po);
		model.setId(po.getId());

		LOG.trace("Exiting addModel with: model = " + model);
		return model;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "models")
	public List<Model> findAll() {
		LOG.trace("Inside findAll");

		List<Model> models = new ArrayList<Model>();

		List<ModelPO> pos = dao.findAll();
		for (ModelPO po : pos) {
			models.add(ConverterUtils.convert(po));
		}

		LOG.trace("Exiting findAll method with: " + models);

		return models;
	}

	@DELETE
	@Path("/{id}")
	public void deleteModel(@PathParam("id") String id) {
		LOG.trace("Inside delete method, ID = " + id);
		long modelId = 0L;
		try {
			modelId = Long.parseLong(id);
		} catch (Exception e) {
			throw new RuntimeException("Error when parsing model ID provided.",
					e);
		}

		boolean isDeleted = dao.delete(modelId);

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
