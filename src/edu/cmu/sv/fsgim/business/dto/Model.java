package edu.cmu.sv.fsgim.business.dto;

public class Model extends BaseDTO {
	private String modelName;
	private String description;
	private String queriesURI;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQueriesURI() {
		return queriesURI;
	}

	public void setQueriesURI(String queriesURI) {
		this.queriesURI = queriesURI;
	}

	@Override
	public String toString() {
		return "Model [modelName=" + modelName + ", description="
				+ description + ", queriesURI=" + queriesURI + ", getId()="
				+ getId() + ", getModifiedBy()=" + getModifiedBy()
				+ ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
