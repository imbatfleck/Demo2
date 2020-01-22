package com.yodlee.agentpush.AgentMigration.service;

import java.util.List;

import com.perforce.p4java.server.IOptionsServer;

public interface MigrationOperationService {
	public String agentMigration(String agent, int version, String environment) throws Exception;
	public IOptionsServer fetchRevision(List<String> agentNam,
			IOptionsServer server) throws Exception; 
}

