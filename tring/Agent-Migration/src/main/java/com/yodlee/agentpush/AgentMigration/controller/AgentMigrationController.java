package com.yodlee.agentpush.AgentMigration.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yodlee.agentpush.AgentMigration.AgentMigrationApplication;
import com.yodlee.agentpush.AgentMigration.adtutility.ADTResponse;
import com.yodlee.agentpush.AgentMigration.adtutility.AgentDetail;
import com.yodlee.agentpush.AgentMigration.model.Agent;
import com.yodlee.agentpush.AgentMigration.perforceutility.PerforceUtility;
import com.yodlee.agentpush.AgentMigration.service.MigrationOperationService;

@Controller
@RequestMapping(value = "/am", method = RequestMethod.GET)
public class AgentMigrationController {

	@Autowired
	private MigrationOperationService migrationOperationService;
	HashMap<String, List<HashMap<String, String>>> finalResp=new HashMap<>();
	
	@RequestMapping(value = "/as", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, List<HashMap<String, String>>>> getStatus() throws Exception {
		return new ResponseEntity<HashMap<String, List<HashMap<String, String>>>>(finalResp, HttpStatus.OK);
	}

	@RequestMapping(value = "/ap", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, List<HashMap<String, String>>>> getAgentsRevison() throws Exception {
		List<HashMap<String, String>> statusList = new ArrayList<>();
		HashMap<String, String> pushedStatus = new HashMap<>();
		List<HashMap<String, String>> responseList=null;
		String OutputfileName = "Result2.txt";
		String fileName = migrationOperationService.getFileName();
		System.out.println("+++++++++filename=" + fileName);
		HashMap<String, List<String>> agentList = AgentMigrationApplication.readAgentFile(fileName);

		int no_of_agents=agentList.size();
		System.out.println("++++++++++class=" + agentList.toString());
		
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "D://ChromeUpgrade//chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to(
				"https://toolcenter.yodlee.com/toolcenter/signin.do");
		WebElement eleuser = driver.findElement(By.id("txtUsername"));
		WebElement elepass = driver.findElement(By.id("txtPassword"));
		eleuser.sendKeys("vkumarsm");
		elepass.sendKeys("*MohanJaya93");
		WebElement elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td/form/table/tbody/tr[4]/td[2]/input[1]"));
		elesubmit.click();
		WebElement adtClick = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td[1]/table/tbody/tr[2]/td/form/table/tbody/tr[8]/td[2]/a[2]"));
		adtClick.click();
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			
			driver.get("https://update.yodlee.com:1443/DeployToolWeb/migration/agent_migration?action=migration_request");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		for(int i=1;i<=agentList.size();i++) {
			HashMap<String, String> statusMap = new HashMap<>();
			HashMap<String, String> agentListHashMap = new HashMap<>();
			List<String> agents=agentList.get("set"+i);
			System.out.println("+++++++++++++++++list"+i+"="+agents);
			for(String agent:agents)
			{
				HashMap<String, String> agentDetail = migrationOperationService.fetchRevision(agent, null);
				System.out.println("Agent Name:"+agent+"++++Version:"+agentDetail.get("version"));
				if(!agentDetail.get("version").equals("0")) {
				agentListHashMap.put(agent, agentDetail.get("version"));
				
				}else {
					System.out.println("^^^^^^^^^^version is zero for:::"+agent);
				}
			}
			try {
			 responseList=migrationOperationService.agentMigrationThroughSelenium(
					  agentListHashMap,driver);
			
			 finalResp.put("set"+i, responseList);
			 
			}catch(Exception e) {
				System.out.println("^^^printing stacktrace");
				e.printStackTrace();
				 responseList=migrationOperationService.agentMigrationThroughSelenium(
						  agentListHashMap,driver);
				
				 finalResp.put("set"+i, responseList);
				
			}
				// Let us append given str to above
				// created file.
				
				try {

					// Open given file in append mode.
					BufferedWriter out = new BufferedWriter(new FileWriter(OutputfileName, true));
					out.write("\n"+responseList.toString());
					out.close();
				} catch (IOException e) {
					System.out.println("exception occoured" + e);
				}
			/*if(i<5)
			{	
				String agentMigrationID = null;
				String baseAgentMigrationID = null;
				HashMap<String, String> agentDetail = migrationOperationService.fetchRevision(agent, null);
				System.out.println("Agent Name:"+agent+"++++Version:"+agentDetail.get("version"));
				agentListHashMap.put(agent, agentDetail.get("version"));
				String containerClass = agentDetail.get(PerforceUtility.CLASS_NAME);
				String baseClass = agentDetail.get(PerforceUtility.BASE_CLASS_NAME);
				String version = agentDetail.get(PerforceUtility.VERSION);
				String baseVersion = agentDetail.get(PerforceUtility.BASE_VERSION);
				System.out.println("++++++++++class=" + containerClass);
				System.out.println("++++++++++class version=" + version);
				System.out.println("++++++++++base class=" + baseClass);
				System.out.println("++++++++++class=" + baseVersion);
				//agentListHashMap.put(containerClass, version);
				//agentListHashMap.put(baseClass, baseVersion);
				if(i==4)
				{
					 while(agentListHashMap.values().remove(null)); 
					 System.out.println("agentListHashMap++++++++++++"+agentListHashMap);
					 i=0;
						agentListHashMap.clear();
						
					  List<HashMap<String, String>> responseList=null;migrationOperationService.agentMigrationThroughSelenium(
					  agentListHashMap);
					 
					  
						String OutputfileName = "Result.txt";
						
						// Let us append given str to above
						// created file.
						
						try {

							// Open given file in append mode.
							BufferedWriter out = new BufferedWriter(new FileWriter(OutputfileName, true));
							out.write("\n"+responseList.toString());
							out.close();
						} catch (IOException e) {
							System.out.println("exception occoured" + e);
						}
				}
			}
			
			
			i++;*/

		}
		//System.out.println("++++++++++agentListHashMap=" + agentListHashMap);
		/*List<HashMap<String, String>> responseList = null;*/
		

		/*
		 * if(baseClass!=null)
		 * 
		 * { if (pushedStatus.get(baseClass) == null) { try { baseAgentMigrationID =
		 * migrationOperationService.agentMigration(baseClass,
		 * Integer.valueOf(baseVersion), "2");
		 * baseAgentMigrationID=migrationOperationService.agentMigrationThroughSelenium(
		 * baseClass, Integer.valueOf(baseVersion), "2"); pushedStatus.put(baseClass,
		 * baseAgentMigrationID); } catch (Exception e) {
		 * 
		 * }
		 * 
		 * } else { baseAgentMigrationID = pushedStatus.get(baseClass); } }
		 * 
		 * if(containerClass!=null)
		 * 
		 * { if(pushedStatus.get(containerClass)==null) { try { agentMigrationID =
		 * migrationOperationService.agentMigration(containerClass,
		 * Integer.valueOf(version), "2");
		 * agentMigrationID=migrationOperationService.agentMigrationThroughSelenium(
		 * containerClass, Integer.valueOf(version), "2");
		 * pushedStatus.put(containerClass, agentMigrationID); } catch (Exception e) {
		 * 
		 * }
		 * 
		 * } else { agentMigrationID=pushedStatus.get(containerClass); } }
		 * 
		 * statusMap.put("className", agent.getcontainerClass());
		 * statusMap.put("version", version); statusMap.put("migrationID",
		 * agentMigrationID); statusMap.put("baseClassName", agent.getBaseClass());
		 * statusMap.put("baseVersion", baseVersion);
		 * statusMap.put("baseMigrationID",baseAgentMigrationID);
		 * statusMap.put("execTime", String.valueOf(System.currentTimeMillis()));
		 * statusList.add(statusMap); }
		 */
		

		return new ResponseEntity<HashMap<String, List<HashMap<String, String>>>>(finalResp, HttpStatus.OK);
	}

	

}
