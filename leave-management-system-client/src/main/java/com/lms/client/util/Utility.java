package com.lms.client.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.client.config.AppConfig;

@Service
public class Utility {

	@Autowired
	private AppConfig appConfig;
	
	public InitialContext getInitialContext() {
		System.out.println("app " + appConfig);
		InitialContext ctx = null;
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, appConfig.getWebLogicInitialContextFactory());
		ht.put(Context.PROVIDER_URL, appConfig.getProviderUrlIp());
		ht.put(Context.PROVIDER_URL, appConfig.getProviderUrlDns());
		try {
			ctx = new InitialContext(ht);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ctx;
	}
}
