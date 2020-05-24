package com.demo.service.register;

import java.sql.SQLException;

import javax.ejb.Remote;

@Remote
public interface RegisterRemote {

	public int addUser(String username, String email, String password, String role) throws SQLException;

}
