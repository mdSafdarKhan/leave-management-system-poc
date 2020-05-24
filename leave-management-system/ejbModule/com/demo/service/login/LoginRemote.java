package com.demo.service.login;

import java.sql.SQLException;
import java.util.HashMap;

import javax.ejb.Remote;

@Remote
public interface LoginRemote {

	public HashMap<String, String> validateLogin(String username, String password) throws SQLException;
}
