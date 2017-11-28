package com.jblesa.demo.authprovider;

import java.util.Arrays;
import java.util.List;

public class User {
	private final String name;
	private final String password;
	private final List<String> grants;

	public User(String name, String password, String... grants) {
		this.name = name;
		this.password = password;
		this.grants = Arrays.asList(grants);
	}
	
	public List<String> getGrants() {
		return grants;
	}

	public boolean match(String name, String password) {
		return this.name.equals(name) && this.password.equals(password);
	}
}