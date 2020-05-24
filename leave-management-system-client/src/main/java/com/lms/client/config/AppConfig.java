package com.lms.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Value("${loginJndi}")
	private String loginJndi;
	
	@Value("${leaveServiceJndi}")
	private String leaveServiceJndi;
	
	@Value("${webLogicInitialContextFactory}")
	private String webLogicInitialContextFactory;
	
	@Value("${providerUrlIp}")
	private String providerUrlIp;
	
	@Value("${providerUrlDns}")
	private String providerUrlDns;

	public String getLoginJndi() {
		return loginJndi;
	}

	public void setLoginJndi(String loginJndi) {
		this.loginJndi = loginJndi;
	}

	public String getLeaveServiceJndi() {
		return leaveServiceJndi;
	}

	public void setLeaveServiceJndi(String leaveServiceJndi) {
		this.leaveServiceJndi = leaveServiceJndi;
	}

	public String getWebLogicInitialContextFactory() {
		return webLogicInitialContextFactory;
	}

	public void setWebLogicInitialContextFactory(String webLogicInitialContextFactory) {
		this.webLogicInitialContextFactory = webLogicInitialContextFactory;
	}

	public String getProviderUrlIp() {
		return providerUrlIp;
	}

	public void setProviderUrlIp(String providerUrlIp) {
		this.providerUrlIp = providerUrlIp;
	}

	public String getProviderUrlDns() {
		return providerUrlDns;
	}

	public void setProviderUrlDns(String providerUrlDns) {
		this.providerUrlDns = providerUrlDns;
	}

}
