package com.yodlee.agentpush.AgentMigration.perforceutility;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.IFileRevisionData;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.AccessException;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.RequestException;
import com.perforce.p4java.server.IOptionsServer;
import com.perforce.p4java.server.ServerFactory;
import com.yodlee.agentpush.AgentMigration.constants.Constants;
import com.yodlee.agentpush.AgentMigration.daoImpl.AgentPushOperation;
import com.yodlee.agentpush.AgentMigration.model.Agent;


public class PerforceUtility {
	
	
	public IOptionsServer connectToPerforce(List<String> agentName,PerforceModel perforceModel) throws Exception{
		// TODO Auto-generated method stub
		//boolean isConnect=false;
		IOptionsServer server = null;
		System.out.println("++++++++username="+perforceModel.getUserName());
		try {
			String serverUri = "p4java://" + perforceModel.getP4Server() + ":" + perforceModel.getPort();
			server = ServerFactory.getOptionsServer(serverUri, null);
			server.setUserName(perforceModel.getUserName());
			server.connect();
			server.login(perforceModel.getPassword());
			server.setWorkingDirectory(perforceModel.getWorkSpaceName());
			//isConnect=true;
		} catch (Exception e) {
			//logger.error("Exception Message : {}", e);
			//throw new AppException(400, "Perforce connection issue, Please Contact Administrator");
			
		} 
		//perforceUtil.fetchRevision(agentName,server);
		
		return server;
		
		
	}
	private String getDepotPath(String fileName,PerforceModel perforceModel) {
	//	System.out.println("Inside getDepotPath");
		StringBuilder builder = new StringBuilder();
		//System.out.println("^^^" +perforceModel.getPath());
		builder.append(perforceModel.getPath()).append(fileName)
				.append(".java");
		String filePath = builder.toString();
		// System.out.println("perforce file Path for"+ fileName+" "+filePath);
		//System.out.println("Perforce Depot Path " + filePath);
		return filePath;
	}
	public IOptionsServer fetchRevision(List<Agent> agentNam,
			IOptionsServer server,PerforceModel perforceModel) throws ConnectionException,
			RequestException, AccessException, IOException {

		
		for (Agent agentName : agentNam) {
			//System.out.println("+++++++++++agent name="+agentName.getcontainerClass());
			if(agentName.getcontainerClass()!=null)
			{
				/*System.out.println(
						"^^^^^fetching revison" + agentName.getcontainerClass() + "   " + agentName.getBaseClass());
				System.out.println("^^I m here" + agentNam);*/
				// System.out.println("^^^^"+FileSpecBuilder.makeFileSpecList(getDepotPath(agentName,
				// null)));
				//System.out.println("++++++++class="+agentName.getcontainerClass());
				if (agentName.getcontainerClass() != null && !agentName.getcontainerClass().equals("N/A")) {
					List<IFileSpec> p4FilesContainer = server.getDepotFiles(FileSpecBuilder
							.makeFileSpecList(getDepotPath(agentName.getcontainerClass(), perforceModel)), false);
					System.out.println("agent=" + agentName.getcontainerClass() + "   Revison="
							+ getRevison(p4FilesContainer, server));
				}

				if (agentName.getBaseClass() != null && !agentName.getBaseClass().equals("N/A")) {
					List<IFileSpec> p4FilesBase = server.getDepotFiles(
							FileSpecBuilder.makeFileSpecList(getDepotPath(agentName.getBaseClass(), perforceModel)),
							false);
					System.out.println("agent base=" + agentName.getBaseClass() + "   Revison="
							+ getRevison(p4FilesBase, server));
				}
			}
			
		}
		return server;
	}
	
	private int getRevison(List<IFileSpec> p4Files,IOptionsServer server)
	{
		Map<IFileSpec, List<IFileRevisionData>> fileRevisionData;
		int ycomversion=0;
		// p4Files.forEach(s->{System.out.println("@@ "+s);});
		if (!p4Files.isEmpty()) {
			System.out.println("Inside If");
			try {
				fileRevisionData = server.getRevisionHistory(p4Files, 200,
						true, false, false, false);
				//System.out.println("%%%%" + fileRevisionData);
				for (List<IFileRevisionData> revisionData : fileRevisionData
						.values()) {
					
					for (IFileRevisionData revData : revisionData) {
						
					String desc = revData.getDescription();
				

						//System.out.println("description here :::"+desc);
						
						
						if(desc.toUpperCase().contains(Constants.ProdCertified) && !(desc.toUpperCase().contains(Constants.ycom))){
								//System.out.println("checking exception2:"+revData.getRevision());
								//adtmigration.agentMigration(agentName,revData.getRevision(),"");
								ycomversion=revData.getRevision();
								break;
						}
						
					}
					
					/*if(ycomversion!=0){
						adtmigration.agentMigration(agentName,ycomversion,"3");
					}*/
				}
			} catch (Exception e) {

				//logger.error("Exception Message : {}", e);
				// throw new AppException(400,
				// "Perforce connection issue, Please Contact Administrator");
			}
		}
		return ycomversion;
	}
}
