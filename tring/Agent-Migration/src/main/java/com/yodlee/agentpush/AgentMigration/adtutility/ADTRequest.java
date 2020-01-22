package com.yodlee.agentpush.AgentMigration.adtutility;

import java.util.List;

public class ADTRequest {
	
	List<AgentDetail> agentDetail;
	String migrationType;
	String environment;
	
	public List<AgentDetail> getAgentDetail() {
		return agentDetail;
	}
	public void setAgentDetail(List<AgentDetail> agentDetail) {
		this.agentDetail = agentDetail;
	}
	public String getMigrationType() {
		return migrationType;
	}
	public void setMigrationType(String migrationType) {
		this.migrationType = migrationType;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	

}

