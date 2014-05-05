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

import edu.cmu.sv.fsgim.business.dto.Query;
import edu.cmu.sv.fsgim.common.ConverterUtils;
import edu.cmu.sv.fsgim.data.dao.IQueryDAO;
import edu.cmu.sv.fsgim.data.dao.QueryDAOImpl;
import edu.cmu.sv.fsgim.data.po.QueryPO;

@Path("/queries")
public class QueryManager {
	private static final Logger LOG = Logger.getLogger(QueryManager.class);
	private IQueryDAO dao = new QueryDAOImpl();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Query addQuery(Query query) {
		LOG.trace(query);

		QueryPO po = ConverterUtils.convert(query);
		
		List<QueryPO> existingPOs = dao.find(po);
		System.out.println("Existing POs = " + existingPOs);
		QueryPO existingPO = null;
		
		// Log a warning to the log file saying we are using the first object to update
		if(existingPOs.size() > 1) {
			LOG.warn("Expected 0 or 1 query with criteria: " + po + ", but got: " + existingPOs.size());
			LOG.warn("Using the first one of the list to update: " + existingPOs);
		}
		
		if(existingPOs.size() > 0) {
			existingPO = existingPOs.get(0);
		}
		
		if(existingPO != null) {
			// We are doing this statement to remove the dependency of entity manager
			// that loaded the existing PO, so that we can merge during update.
			existingPO = ConverterUtils.convert(ConverterUtils.convert(existingPO));
			
			existingPO.setQueryString(po.getQueryString());
			existingPO.setModifiedTime(new Date());
			po = existingPO;
			
			System.out.println("Existing ID = " + po.getId());
		} else {
			po.setCreatedTime(new Date());
		}
		
		po = dao.save(po);
		query = ConverterUtils.convert(po);

		LOG.trace(query);
		return query;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "queries")
	public List<Query> findAll() {
		LOG.trace("Inside findAll");

		List<Query> queries = new ArrayList<Query>();

		List<QueryPO> pos = dao.findAll();
		for (QueryPO po : pos) {
			queries.add(ConverterUtils.convert(po));
		}

		LOG.trace("Exiting findAll method with: " + queries);

		return queries;
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteQuery(@PathParam("id") String id) {
		LOG.trace("Inside delete method, ID = " + id);
		long queryId = 0L;
		try {
			queryId = Long.parseLong(id);
		} catch (Exception e) {
			throw new RuntimeException("Error when parsing query ID provided.", e);
		}
		
		boolean isDeleted = dao.delete(queryId);
		
		if(!isDeleted) {
			throw new RuntimeException("There was a problem when deleting the query. "
					+ "Please check GAE server log.");
		}
		
		LOG.trace("Exiting delete method");
	}
}
