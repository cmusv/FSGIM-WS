package edu.cmu.sv.fsgim.data.po;

import javax.persistence.Entity;

@Entity
public class UserPO extends BasePO {
	private String userName;
	private byte[] password;
	private byte[] salt;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "UserPO [userName=" + userName + ", getId()=" + getId()
				+ ", getModifiedBy()=" + getModifiedBy()
				+ ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
