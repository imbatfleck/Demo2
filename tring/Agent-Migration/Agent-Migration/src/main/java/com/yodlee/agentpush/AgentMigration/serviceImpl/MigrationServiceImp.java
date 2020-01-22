package com.yodlee.agentpush.AgentMigration.serviceImpl;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.IFileRevisionData;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.AccessException;
import com.perforce.p4java.exception.ConfigException;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.NoSuchObjectException;
import com.perforce.p4java.exception.ResourceException;
import com.perforce.p4java.server.IOptionsServer;
import com.perforce.p4java.server.ServerFactory;
import com.yodlee.agentpush.AgentMigration.dao.MigrationOperations;
import com.yodlee.agentpush.AgentMigration.service.MigrationOperationService;

@Service
public class MigrationServiceImp implements MigrationOperationService{
	
	
	@Autowired
	private MigrationOperations migrationOperation;
	
	@Override
	public String agentMigration(String agent, int version, String environment) throws Exception {
		// TODO Auto-generated method stub
		return migrationOperation.agentMigration(agent,version,environment);
	}

	@Override
	public IOptionsServer fetchRevision(List<String> agentNam, IOptionsServer server) throws Exception {
		// TODO Auto-generated method stub	
		return migrationOperation.fetchRevision(null, null);
	}

	
}
