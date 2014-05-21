package edu.cmu.sv.fsgim.data.po;

import javax.persistence.Entity;

@Entity
public class ModelPO extends BasePO {
	private String modelName;
	private String description;

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

	@Override
	public String toString() {
		return "ModelPO [modelName=" + modelName + ", description="
				+ description + ", getId()=" + getId() + ", getModifiedBy()="
				+ getModifiedBy() + ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
