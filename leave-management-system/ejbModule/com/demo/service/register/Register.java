package com.demo.service.register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class Register implements RegisterRemote, RegisterLocal {

	@Resource(name = "loginDataSource")
	private DataSource loginDataSource;
	
    public Register() {}
    
    public int addUser(String username, String email, String password, String role) throws SQLException {
		Connection conn = loginDataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("insert into users values (?,?,?,?,?,?)");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, username);
		stmt.setString(3, email);
		stmt.setString(4, password);
		stmt.setString(5, new java.util.Date().toString());
		stmt.setString(6, role);
		return stmt.executeUpdate();
	}
}
