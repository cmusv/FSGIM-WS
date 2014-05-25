package edu.cmu.sv.fsgim.business.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
public class Query extends BaseDTO {
	private String queryClassification;
	private String queryName;
	private String queryString;
	private String modelVersion;
	private String modelName;
	private String queryPrefix;

	public String getQueryPrefix() {
		return queryPrefix;
	}

	public void setQueryPrefix(String queryPrefix) {
		this.queryPrefix = queryPrefix;
	}

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

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
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
		return "Query [queryClassification=" + queryClassification
				+ ", queryName=" + queryName + ", queryString=" + queryString
				+ ", modelVersion=" + modelVersion + ", modelName=" + modelName
				+ ", queryPrefix=" + queryPrefix + ", getId()=" + getId()
				+ ", getModifiedBy()=" + getModifiedBy()
				+ ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
