package com.yodlee.agentpush.AgentMigration.perforceutility;

import org.springframework.beans.factory.annotation.Value;

public class PerforceModel {
	
	private String userName;
	
	private String password;
	
	private String p4Server;
	
	private String workSpaceName;
	
	private String port;
	
	private String path;
	
	private String localPath;

	private String jDapPath;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getP4Server() {
		return p4Server;
	}

	public void setP4Server(String p4Server) {
		this.p4Server = p4Server;
	}

	public String getWorkSpaceName() {
		return workSpaceName;
	}

	public void setWorkSpaceName(String workSpaceName) {
		this.workSpaceName = workSpaceName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getjDapPath() {
		return jDapPath;
	}

	public void setjDapPath(String jDapPath) {
		this.jDapPath = jDapPath;
	}

	public PerforceModel() {
		super();
	}
	
	

}
