package edu.cmu.sv.fsgim.business.dto;

public class Version extends BaseDTO {
	private String versionNumber;
	private String description;

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
		return "Version [versionNumber=" + versionNumber + ", description="
				+ description + ", getId()=" + getId() + ", getModifiedBy()="
				+ getModifiedBy() + ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
