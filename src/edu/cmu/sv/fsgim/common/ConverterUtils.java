package edu.cmu.sv.fsgim.common;

import com.google.appengine.api.datastore.Text;

import edu.cmu.sv.fsgim.business.dto.BaseDTO;
import edu.cmu.sv.fsgim.business.dto.Model;
import edu.cmu.sv.fsgim.business.dto.Query;
import edu.cmu.sv.fsgim.business.dto.Version;
import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.po.ModelPO;
import edu.cmu.sv.fsgim.data.po.QueryPO;
import edu.cmu.sv.fsgim.data.po.VersionPO;

public class ConverterUtils {
	public static final QueryPO convert(Query dto) {
		if (dto == null)
			return null;

		QueryPO po = new QueryPO();
		po.setQueryClassification(dto.getQueryClassification());
		po.setQueryName(dto.getQueryName());
		po.setQueryString(new Text(dto.getQueryString()));
		po.setModelName(dto.getModelName());
		po.setModelVersion(dto.getModelVersion());
		po.setQueryPrefix(dto.getQueryPrefix());

		updateWhoColumns(dto, po);

		return po;
	}

	public static final Query convert(QueryPO po) {
		if (po == null)
			return null;

		Query dto = new Query();
		dto.setQueryClassification(po.getQueryClassification());
		dto.setQueryName(po.getQueryName());
		dto.setQueryString(po.getQueryString().getValue());
		dto.setModelName(po.getModelName());
		dto.setModelVersion(po.getModelVersion());
		dto.setQueryPrefix(po.getQueryPrefix());

		updateWhoColumns(po, dto);

		return dto;
	}

	public static final Version convert(VersionPO po) {
		if (po == null) {
			return null;
		}

		Version dto = new Version();
		dto.setVersionNumber(po.getVersionNumber());
		dto.setDescription(po.getDescription());
		dto.setModelName(po.getModelName());

		updateWhoColumns(po, dto);

		return dto;
	}

	public static final VersionPO convert(Version dto) {
		if (dto == null) {
			return null;
		}

		VersionPO po = new VersionPO();
		po.setVersionNumber(dto.getVersionNumber());
		po.setDescription(dto.getDescription());
		po.setModelName(dto.getModelName());

		updateWhoColumns(dto, po);

		return po;
	}

	public static final Model convert(ModelPO po) {
		if (po == null) {
			return null;
		}

		Model dto = new Model();
		dto.setDescription(po.getDescription());
		dto.setModelName(po.getModelName());
		dto.setQueriesURI(po.getQueriesURI());

		updateWhoColumns(po, dto);

		return dto;
	}

	public static final ModelPO convert(Model dto) {
		if (dto == null) {
			return null;
		}

		ModelPO po = new ModelPO();
		po.setDescription(dto.getDescription());
		po.setModelName(dto.getModelName());
		po.setQueriesURI(dto.getQueriesURI());

		updateWhoColumns(dto, po);

		return po;
	}

	public static final void updateWhoColumns(BasePO source, BaseDTO dest) {
		if (source.getId() != null)
			dest.setId(source.getId());
		dest.setCreatedBy(source.getCreatedBy());
		dest.setCreatedTime(source.getCreatedTime());
		dest.setModifiedBy(source.getModifiedBy());
		dest.setModifiedTime(source.getModifiedTime());
	}

	public static final void updateWhoColumns(BaseDTO source, BasePO dest) {
		if (source.getId() != 0)
			dest.setId(source.getId());
		dest.setCreatedBy(source.getCreatedBy());
		dest.setCreatedTime(source.getCreatedTime());
		dest.setModifiedBy(source.getModifiedBy());
		dest.setModifiedTime(source.getModifiedTime());
	}
}
