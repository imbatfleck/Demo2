package com.yodlee.agentpush.AgentMigration.daoImpl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AgentMigrationThroughSelenium {
	public List<HashMap<String, String>> doAgentMigrationThroughSelenium(HashMap<String, String> agentListHashMap,WebDriver driver) {
		
		System.setProperty("java.awt.headless", "false");
	//	System.out.println("agent:::::::::+++++version:::::");
		List<HashMap<String,String>> statusMapList=new ArrayList<>();
		HashMap<String,String> statusMap=new HashMap<>();
		/*WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "D://ChromeUpgrade//chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to(
				"https://toolcenter.yodlee.com/toolcenter/signin.do");
		WebElement eleuser = driver.findElement(By.id("txtUsername"));
		WebElement elepass = driver.findElement(By.id("txtPassword"));
		eleuser.sendKeys("vkumarsm");
		elepass.sendKeys("*MohanJaya93");*/
		/*WebElement elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td/form/table/tbody/tr[4]/td[2]/input[1]"));
		elesubmit.click();
		
		elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td[1]/table/tbody/tr[2]/td/form/table/tbody/tr[8]/td[2]/a[2]"));
		elesubmit.click();*/
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			
			driver.get("https://update.yodlee.com:1443/DeployToolWeb/migration/agent_migration?action=migration_request");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Agent push radio button
			/*elesubmit = driver.findElement(By.id("cmbAgentpushradio"));
			elesubmit.click();*/
			//Thread.sleep(3000);
			//site id text box
			
			//Click on mass agent push
			//System.out.println("+++++++++print");
			WebElement elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td/form/table/tbody/tr[8]/td/a"));
			elesubmit.click();
			// Click on Dap radio button
			
			elesubmit = driver.findElement(By.id("dapPlatform"));
			elesubmit.click();
			
			//enter agents list into text mass push area
			
			WebElement txtMassPushString=driver.findElement(By.id("txtMassPushString"));
			String str =null;
			Boolean firstElementFlag= true;
			 Iterator it = agentListHashMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			       // System.out.println(pair.getKey() + " = " + pair.getValue());
			        if(firstElementFlag) {
			         str =pair.getKey()+"\t1\ty\tn\tn\t"+pair.getValue()+"\t4";
			        // System.out.println("+++++++++print str first time"+str);
			         firstElementFlag=false;
			        }else{
			        	str=str+"\n"+pair.getKey()+"\t1\ty\tn\tn\t"+pair.getValue()+"\t4";
			        	//System.out.println("+++++++++print str in loop"+str);
			        }
			       // it.remove(); // avoids a ConcurrentModificationException
			    }
			
			
			   // System.out.println("+++++++++print"+str);
			
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			StringSelection strSel = new StringSelection(str);
			clipboard.setContents(strSel, null);
		//	System.out.println("+++clicpboard contents1111111="+clipboard.getContents(strSel));
			
			//String agentMassPushText="AlaskaUSAFCULoan\t1\ty\tn\tn\t28\t4";
		//	System.out.println("+++++++++print"+str);
			txtMassPushString.sendKeys(Keys.CONTROL,"v");	
				//	Thread.sleep(5000);
					
			
			
					/*WebElement envCheckBo = driver.findElement(By.id("img_chk_DAP_-Developer Firemem"));
					System.out.println("^^^^^^^^^print"+dropele.getText());
					Select dropdown1 = new Select(dropele);
					dropdown1.selectByIndex(2);
					dropele.click();*/
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
			//all prod check box
					//Thread.sleep(2000);
	elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-All Prod']"));
			elesubmit.click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//click on submit
			elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td/form/table/tbody/tr[14]/td/input[1]"));
			elesubmit.click();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//all prod check box
			//Thread.sleep(2000);
			elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-All Prod']"));
			elesubmit.click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//unclick on Platform again
			elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-Platform Firemem']"));
			elesubmit.click();
			
			//unclick on Developer again
			elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-Developer Firemem']"));
			elesubmit.click();
			
			//unclick on Stage frmem again
			elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-Stage Firemem']"));
			elesubmit.click();
			
			//unclick on Stage again
			elesubmit = driver.findElement(By.xpath("//*[@id='img_chk_DAP_-Stage']"));
			elesubmit.click();
			
			
			//click on submit again
			elesubmit = driver.findElement(By.xpath("//*[@id='nonFooter']/table[2]/tbody/tr[2]/td/form/table/tbody/tr[14]/td/input[1]"));
			elesubmit.click();
			
			
			WebElement messageEle=driver.findElement(By.xpath("//*[@id='nonFooter']/div[3]/div/span/a"));
			System.out.println("Get Text="+messageEle.getText());
			String message=messageEle.getText();
			System.out.println("++++++++++++message="+message);
			if(message.contains("Migration Status"))
			{
				System.out.println(messageEle+"-"+"DB PUSH DONE");
			}
			else
			{
				System.out.println(messageEle+"-"+"DB PUSH Failed");
			}
			//*[@id="nonFooter"]/div[3]/div/span/a[4]
			HashMap<String, String> tempAgentList=new HashMap<>();
			 List<WebElement> elements = driver.findElements(By.xpath("//*[@id='nonFooter']/div[3]/div/span/a")); 
			  for (int i = 0; i < elements.size(); i++) {
				  
			   System.out.println(elements.get(i).getAttribute("href"));
			   HashMap<String,String> responseSubstingOperation= getResponseSubstring(elements.get(i).getAttribute("href"));
				responseSubstingOperation.get("filename");
				tempAgentList.put(responseSubstingOperation.get("filename"), "null");
			   statusMap.put("execTime", String.valueOf(System.currentTimeMillis()));
				statusMapList.add(responseSubstingOperation);
				
			  }
			
			  HashSet<String> unionKeys = new HashSet<>(tempAgentList.keySet());
			  unionKeys.addAll(agentListHashMap.keySet());
			   
			  unionKeys.removeAll(tempAgentList.keySet());
			   
			  System.out.println(unionKeys);
			  
			  // Displaying the HashSet 
		        System.out.println("HashSet: " + unionKeys); 
		  
		        // Creating an iterator 
		        Iterator value = unionKeys.iterator(); 
		  
		        // Displaying the values after iterating through the set 
		        System.out.println("The iterator values are: "); 
		        while (value.hasNext()) { 
		        	HashMap<String,String> UnpushedAgentsStatusMap=new HashMap<>();
		        	String unPushedAgent=(String) value.next();
		            System.out.println("unPushedAgent++++++++"+unPushedAgent); 
		            UnpushedAgentsStatusMap.put("filename", unPushedAgent);
		            UnpushedAgentsStatusMap.put("Migration_Status", "Not Pushed");
		            statusMapList.add(UnpushedAgentsStatusMap);
		        } 
			
			//driver.close();	
		        System.out.println("statusMapList++++++"+statusMapList);
		return statusMapList ;
		
	}

	private HashMap<String, String> getResponseSubstring(String responseString) {
		String AccountName=responseString;

		String result = AccountName.substring(AccountName.indexOf("migration_id=") + 13, AccountName.indexOf(",sub_mig"));
		HashMap<String,String> statusMap=new HashMap<>();
       System.out.println("result::::::::"+result);
       statusMap.put("migration_id", result);
       
       result = AccountName.substring(AccountName.indexOf("agent_version=") + 14, AccountName.indexOf(",filename"));
       statusMap.put("agent_version", result);
       System.out.println("result1::::::::"+result);
       
       result = AccountName.substring(AccountName.indexOf(",filename=") + 10, AccountName.indexOf(",environment_id"));
       statusMap.put("filename", result);
       System.out.println("result1::::::::"+result);
       
       result = AccountName.substring(AccountName.indexOf(",environment_id=") + 16, AccountName.indexOf(",mig_folder"));
       statusMap.put("environment_id", result);
       System.out.println("result1::::::::"+result);
       
       result = AccountName.substring(AccountName.indexOf(",mig_folder") + 12, AccountName.indexOf("')"));
       statusMap.put("mig_folder", result);
       System.out.println("result1::::::::"+result);
       
		return statusMap;
	}
}
