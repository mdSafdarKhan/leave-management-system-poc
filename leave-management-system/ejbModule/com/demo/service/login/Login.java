package com.demo.service.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class Login implements LoginRemote, LoginLocal {

	@Resource(name = "loginDataSource")
	private DataSource loginDataSource;

	public Login() {
	}

	public HashMap<String, String> validateLogin(String username, String password) throws SQLException {
		HashMap<String, String> user = null;
		Connection conn = loginDataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select * from users where name=? and password=?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String uName = rs.getString("name");
			String pwd = rs.getString("password");
			String role = rs.getString("role");

			user = new HashMap<String, String>();
			user.put("username", uName);
			user.put("password", pwd);
			user.put("role", role);

			System.out.println("username: " + username);
			System.out.println("password: " + pwd);
			System.out.println("role: " + role);

			return user;
		}
		return user;
	}
}
