package com.yodlee.agentpush.AgentMigration.daoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.IFileRevisionData;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.AccessException;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.RequestException;
import com.perforce.p4java.server.IOptionsServer;
import com.yodlee.agentpush.AgentMigration.AgentMigrationApplication;
import com.yodlee.agentpush.AgentMigration.adtutility.ADTRequest;
import com.yodlee.agentpush.AgentMigration.adtutility.ADTResponse;
import com.yodlee.agentpush.AgentMigration.adtutility.AgentDetail;
import com.yodlee.agentpush.AgentMigration.dao.MigrationOperations;
import com.yodlee.agentpush.AgentMigration.model.Agent;
import com.yodlee.agentpush.AgentMigration.perforceutility.PerforceModel;
import com.yodlee.agentpush.AgentMigration.perforceutility.PerforceUtility;
import com.yodlee.agentpush.AgentMigration.restoperation.RestOperation;
import com.yodlee.agentpush.AgentMigration.restoperation.TokenGenerator;

@Repository
public class AgentPushOperation implements MigrationOperations{
	
	public PerforceModel perforceModel=new PerforceModel();
	@Value("${perforce.username}")
	private String userName;
	
	@Value("${perforce.password}")
	private String password;
	
	@Value("${perforce.server}")
	private String p4Server;
	
	@Value("${perforce.workspaceName}")
	private String workSpaceName;
	
	@Value("${perforce.port}")
	private String port;
	
	@Value("${perforce.depotPath}")
	private String path;
	
	@Value("${perforce.localPath}")
	private String localPath;

	@Value("${perforce.jDapPath}")
	private String jDapPath;
	
	@Value("${agents.filename}")
	private String fileName;
	
	
	
	

	@Override
	public String agentMigration(String agent, int version, String environment) throws Exception {
		ADTRequest adtRequest = null;

		HttpHeaders headers = new HttpHeaders();
		adtRequest = new ADTRequest();
		adtRequest.setEnvironment(environment);
		adtRequest.setMigrationType("1");

		AgentDetail agentdetail = new AgentDetail();
		agentdetail.setAgentName(agent);
		agentdetail.setVersion(version);
		agentdetail.setArtifact("0");
		List<AgentDetail> listOfagentdetail = new ArrayList<>();
		listOfagentdetail.add(agentdetail);
		adtRequest.setAgentDetail(listOfagentdetail);
		//Gson gson = new Gson();
		String token = TokenGenerator.getToken();
		System.out.println("++++++++++++token="+token);
	    //String requestBody = "";
		/*headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);*/
		String url = "https://agent-migration.tools.yodlee.com/site-agent/deployment";
		System.out.println("here in");
		//HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		ADTResponse response=TokenGenerator.ADTPushService(url, adtRequest);
		System.out.println("response body="+response.getMigrationID());
		
		return "";
	}

	@Override
	public IOptionsServer fetchRevision(List<String> agentNam,
			IOptionsServer server) throws Exception{
		System.out.println("+++++++++filename="+fileName);
		List<Agent> agentList=AgentMigrationApplication.readAgentFile(fileName);
		perforceModel.setUserName(userName);
		perforceModel.setPassword(password);
		perforceModel.setjDapPath(jDapPath);
		perforceModel.setLocalPath(localPath);
		perforceModel.setP4Server(p4Server);
		perforceModel.setPath(path);
		perforceModel.setPort(port);
		perforceModel.setWorkSpaceName(workSpaceName);
		PerforceUtility perforceUtility=new PerforceUtility();
		//System.out.println("connection="+perforceUtility.connectToPerforce(null,perforceModel));
		perforceUtility.fetchRevision(agentList, perforceUtility.connectToPerforce(null,perforceModel), perforceModel);
		return server;
	
	}
	

}
