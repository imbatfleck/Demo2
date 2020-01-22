package com.yodlee.agentpush.AgentMigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.yodlee.agentpush.AgentMigration.service.MigrationOperationService;

@SpringBootApplication
public class AgentMigrationApplication implements CommandLineRunner{

	@Autowired
	private MigrationOperationService migrationOperationService;
	
	public static void main(String[] args) throws IOException{
		SpringApplication.run(AgentMigrationApplication.class, args);
		//readAgentFile("agentlist.xlsx");
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//migrationOperationService.fetchRevision(null, null);
		
		migrationOperationService.fetchRevision(null, null);
		
	}
	
	public static List<Agent> readAgentFile(String fileName) throws IOException
	{
		CopyOnWriteArrayList<Agent> agentList=new CopyOnWriteArrayList<>();
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
			// System.out.println("Agent Name="+agent.getcontainerClass()+"  Base="+agent.getBaseClass());
		 }
		return agentList;

	}
	

}
