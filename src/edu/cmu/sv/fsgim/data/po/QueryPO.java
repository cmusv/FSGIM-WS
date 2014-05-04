package edu.cmu.sv.fsgim.data.po;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;

@Entity
public class QueryPO extends BasePO {
	private String queryClassification;
	private String queryName;
	private Text queryString;
	private String modelVersion;
	private String modelName;

	public String getQueryClassification() {
		return queryClassification;
	}

	public void setQueryClassification(String queryClassification) {
		this.queryClassification = queryClassification;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Text getQueryString() {
		return queryString;
	}

	public void setQueryString(Text queryString) {
		this.queryString = queryString;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Override
	public String toString() {
		return "QueryPO [queryClassification=" + queryClassification
				+ ", queryName=" + queryName + ", queryString=" + queryString
				+ ", modelVersion=" + modelVersion + ", modelName=" + modelName
				+ ", getId()=" + getId() + ", getModifiedBy()="
				+ getModifiedBy() + ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
