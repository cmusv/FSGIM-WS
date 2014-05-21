package edu.cmu.sv.fsgim.data.po;

import javax.persistence.Entity;

@Entity
public class VersionPO extends BasePO {
	private String versionNumber;
	private String description;
	private String modelName;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "VersionPO [versionNumber=" + versionNumber + ", description="
				+ description + ", modelName=" + modelName + ", getId()="
				+ getId() + ", getModifiedBy()=" + getModifiedBy()
				+ ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
