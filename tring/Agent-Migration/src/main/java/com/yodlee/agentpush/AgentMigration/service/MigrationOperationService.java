package com.yodlee.agentpush.AgentMigration.service;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.http.ResponseEntity;

import com.perforce.p4java.server.IOptionsServer;
import com.yodlee.agentpush.AgentMigration.model.Agent;

public interface MigrationOperationService {
	public String agentMigration(String agent, int version, String environment) throws Exception;
	public HashMap<String, String> fetchRevision(String agent,
			IOptionsServer server) throws Exception; 
	public String getFileName();
	public String agentMigrationThroughSelenium(String agent, int version, String environment);
	public List<HashMap<String, String>> agentMigrationThroughSelenium(
			HashMap<String, String> agentListHashMap,WebDriver driver);
}

