package ru.spbstu.telematics.dto;

import ru.spbstu.telematics.annotations.Permission;

public class User {
	private Permission role;
	private String name;
	public User(Permission role, String name) {
		super();
		this.role = role;
		this.name = name;
	}
	public Permission getRole() {
		return role;
	}
	public void setRole(Permission role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
