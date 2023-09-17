package com.mukss.eventweb.entities;

public class Membership {
	private String userName;
	private String firstName;
	private String lastName;
	
	private boolean membershipCheckbox;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isMembershipCheckbox() {
		return membershipCheckbox;
	}

	public void setMembershipCheckbox(boolean membershipCheckbox) {
		this.membershipCheckbox = membershipCheckbox;
	}
}
