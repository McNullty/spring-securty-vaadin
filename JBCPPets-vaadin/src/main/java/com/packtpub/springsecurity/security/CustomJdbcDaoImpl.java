package com.packtpub.springsecurity.security;

import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomJdbcDaoImpl extends JdbcDaoImpl implements IChangePassword {

	@Override
	public void changePassword(String username, String password) {
		getJdbcTemplate().update(
				"UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?", password,
				username);

	}

}
