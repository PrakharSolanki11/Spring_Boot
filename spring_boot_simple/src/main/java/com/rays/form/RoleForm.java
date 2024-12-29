package com.rays.form;

import javax.validation.constraints.NotEmpty;

public class RoleForm {

	@NotEmpty(message = "Role Name is required")
	private String name;

	@NotEmpty(message = "Discription is required")
	private String discription;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

}
