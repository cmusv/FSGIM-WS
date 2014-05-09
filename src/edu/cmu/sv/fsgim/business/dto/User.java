package edu.cmu.sv.fsgim.business.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User extends BaseDTO {
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPassword(byte[] password) {
		this.password = new String(password);
	}

	@Override
	public String toString() {
		return "User [userName=" + userName 
				+ ", getId()=" + getId() + ", getModifiedBy()="
				+ getModifiedBy() + ", getModifiedTime()=" + getModifiedTime()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTime()="
				+ getCreatedTime() + "]";
	}

}
