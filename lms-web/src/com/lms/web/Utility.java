package com.lms.web;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Utility {

	public static InitialContext getInitialContext() {
		InitialContext ctx = null;
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
		ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
		try {
			ctx = new InitialContext(ht);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ctx;
	}
	
}
