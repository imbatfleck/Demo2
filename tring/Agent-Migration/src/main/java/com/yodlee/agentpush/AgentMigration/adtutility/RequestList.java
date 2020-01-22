package com.yodlee.agentpush.AgentMigration.adtutility;

import java.util.List;

public class RequestList {
	
	List<String> agentList;

	public List<String> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<String> agentList) {
		this.agentList = agentList;
	}

	@Override
	public String toString() {
		return "RequestList [agentList=" + agentList + "]";
	}
	

}
