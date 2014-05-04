package edu.cmu.sv.fsgim.business.dto;

import java.util.Date;

public class BaseDTO {
	private long id;
	private String modifiedBy;
	private Date modifiedTime;
	private String createdBy;
	private Date createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "BaseDTO [id=" + id + ", modifiedBy=" + modifiedBy
				+ ", modifiedTime=" + modifiedTime + ", createdBy=" + createdBy
				+ ", createdTime=" + createdTime + "]";
	}
}
