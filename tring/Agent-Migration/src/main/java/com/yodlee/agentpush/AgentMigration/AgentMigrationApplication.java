package com.yodlee.agentpush.AgentMigration;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yodlee.agentpush.AgentMigration.model.Agent;
import com.yodlee.agentpush.AgentMigration.perforceutility.PerforceUtility;
import com.yodlee.agentpush.AgentMigration.service.MigrationOperationService;

@SpringBootApplication
public class AgentMigrationApplication{

	
	
	public static void main(String[] args) throws IOException{
		SpringApplication.run(AgentMigrationApplication.class, args);
		//readAgentFile("agentlist1.xlsx");
		}

	/*@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//migrationOperationService.fetchRevision(null, null);
		
		List<HashMap<String, String>> agentMap=migrationOperationService.fetchRevision(null, null);
		for(HashMap<String,String> agentMap1:agentMap)
		{
			if(agentMap1.get(PerforceUtility.CLASS_NAME)!=null || agentMap1.get(PerforceUtility.BASE_CLASS_NAME)!=null)
			{
				System.out.println("CLASS NAME="+agentMap1.get(PerforceUtility.CLASS_NAME));
				System.out.println("VERSION="+agentMap1.get(PerforceUtility.VERSION));
				System.out.println("BASE CLASS NAME="+agentMap1.get(PerforceUtility.BASE_CLASS_NAME));
				System.out.println("BASE CLASS VERSION="+agentMap1.get(PerforceUtility.BASE_VERSION));
			}
		}
		
	
		
	}*/
	
	public static HashMap<String, List<String>> readAgentFile(String fileName) throws IOException
	{
		CopyOnWriteArrayList<Agent> agentList=new CopyOnWriteArrayList<>();
        LinkedHashSet<String> agentListFinal=new LinkedHashSet<>();
        List<String> agentListFinalList=new ArrayList<>();
        try {
                            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
                            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                            XSSFSheet sheet = workbook.getSheetAt(0);

                            // Iterate through each rows one by one
                            Iterator<Row> rowIterator = sheet.iterator();
                            int j = 0;
                                  while (rowIterator.hasNext()) {
                                         Row row = rowIterator.next();
                                         Agent agent = new Agent();
                                         // For each row, iterate through all the columns
                                         Iterator<Cell> cellIterator = row.cellIterator();
                                         int i = 0;
                                         while (cellIterator.hasNext()) {
                                               Cell cell = cellIterator.next();
                                               // Check the cell type and format accordingly
                                               switch (cell.getCellType()) {
                                               
                                               case Cell.CELL_TYPE_NUMERIC:
                                                      break;
                                               case Cell.CELL_TYPE_STRING:
                                                      String cellValue = cell.getStringCellValue();
                                                      if (j != 0) {
                                                             if (i == 0) {
                                                                   agent.setcontainerClass(cell.getStringCellValue());
                                                             } else if (i == 1) {
                                                                   //System.out.println("+++++++enteringbaseclass");
                                                                   agent.setBaseClass(cell.getStringCellValue());
                                                             } 
                                                      }
                                               
                                                      break;
                                               }
                                               i++;
                                         }
  
                                         j++;
                                         agentList.add(agent);
                                  }
                            fileInputStream.close();

                     } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                     } 
         for(Agent agent:agentList)
        {
               if(agent.getcontainerClass()==null && agent.getBaseClass()==null)
               {
                     agentList.remove(agent);
                     // System.out.println("++++++++++inside null");
               }
               else
               {
                     if(!agent.getBaseClass().equals("N/A"))
                     {
                            agentListFinal.add(agent.getBaseClass());
                     }
                     if(!agent.getcontainerClass().equals("N/A"))
                     {
                            agentListFinal.add(agent.getcontainerClass());
                     }
               }
               System.out.println("Agent Name="+agent.getcontainerClass()+"  Base="+agent.getBaseClass());
        }
        HashMap<String,List<Agent>> listAgentMap=new HashMap<>();
        int no_of_sets=0;
        int listSize=agentListFinal.size();
        if(listSize%5==0)
        {
               no_of_sets=listSize/5;
        }
        else
        {
               no_of_sets=(listSize/5)+1;
        }
        System.out.println("sets="+no_of_sets);
        for(String ag: agentListFinal)
        {
               agentListFinalList.add(ag);
        }
        HashMap<String, List<String>> setList=new HashMap<>();
        int m=1;
        for(int j=1;j<=no_of_sets;j++)
        {
               List<String> agList=new ArrayList<>();
               for(int k=m;k<=agentListFinalList.size();k++)
               {
                     if(k%5!=0)
                     {
                            agList.add(agentListFinalList.get(k-1));
                            System.out.println("+++++++++m value="+m);
                            m++;
                            
                      }
                     else
                     {
                            agList.add(agentListFinalList.get(k-1));
                            System.out.println("+++++++++final m value="+m);
                            m++;
                            break;
                     }
                     
                }
               setList.put("set"+j, agList);
        }
        for(int l=1;l<=setList.size();l++)
        {
               System.out.println("List="+setList.get("set"+l));
        }
        
        return setList;

		
		
		
		
		
		
		/*
		CopyOnWriteArrayList<Agent> agentList=new CopyOnWriteArrayList<>();
		LinkedHashSet<String> agentListFinal=new LinkedHashSet<>();
		try {
					FileInputStream fileInputStream = new FileInputStream(new File(fileName));
					XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
					XSSFSheet sheet = workbook.getSheetAt(0);

					// Iterate through each rows one by one
					Iterator<Row> rowIterator = sheet.iterator();
					int j = 0;
						while (rowIterator.hasNext()) {
							Row row = rowIterator.next();
							Agent agent = new Agent();
							// For each row, iterate through all the columns
							Iterator<Cell> cellIterator = row.cellIterator();
							int i = 0;
							while (cellIterator.hasNext()) {
								Cell cell = cellIterator.next();
								// Check the cell type and format accordingly
								switch (cell.getCellType()) {
								
								case Cell.CELL_TYPE_NUMERIC:
									break;
								case Cell.CELL_TYPE_STRING:
									String cellValue = cell.getStringCellValue();
									if (j != 0) {
										if (i == 0) {
											agent.setcontainerClass(cell.getStringCellValue());
										} else if (i == 1) {
											//System.out.println("+++++++enteringbaseclass");
											agent.setBaseClass(cell.getStringCellValue());
										} 
									}
								
									break;
								}
								i++;
							}
	
							j++;
							agentList.add(agent);
						}
					fileInputStream.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		 for(Agent agent:agentList)
		 {
			 if(agent.getcontainerClass()==null && agent.getBaseClass()==null)
			 {
				 agentList.remove(agent);
				// System.out.println("++++++++++inside null");
			 }
			 else
			 {
				 if(!agent.getBaseClass().equals("N/A"))
				 {
					 agentListFinal.add(agent.getBaseClass());
				 }
				 if(!agent.getcontainerClass().equals("N/A"))
				 {
					 agentListFinal.add(agent.getcontainerClass());
				 }
			 }
			 System.out.println("Agent Name="+agent.getcontainerClass()+"  Base="+agent.getBaseClass());
		 }
		 System.out.println("================================");
		 for(String agent: agentListFinal)
		 {
			 System.out.println(agent);
		 }
		return agentListFinal;

	 
 
*/}
	

}
