package com.wmc.Utils;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class SeleniumExecutor {
	
	public List<TestNGModuleDTO> getRunnableTestCases(){
		ArrayList<TestNGModuleDTO> list=new ArrayList<TestNGModuleDTO>();
		
		//String filePath="C:\\Users\\user\\Desktop\\javaPractice\\SeleniumFrameworkLearningPurpose\\Data.xlsx";
		String path=System.getProperty("user.dir");
		String sQuery="select * from data where RUN = 'Y'";
		Fillo fillo=new Fillo();
		Connection con=null;
		Recordset rs=null;
		try {
			con=fillo.getConnection(path+"\\Data.xlsx");
			rs=con.executeQuery(sQuery);
			
			while(rs.next()){
				
				TestNGModuleDTO oTestNGModuleDTO=new TestNGModuleDTO();
				String TCID=rs.getField("TCID");
				String Method=rs.getField("TCNAME");
				String Scenario=rs.getField("Test_Scenario_Name");
				String ClassName=rs.getField("TESTCLASS");
				
				oTestNGModuleDTO.setTCID(TCID);
				oTestNGModuleDTO.setMethod(Method);
				oTestNGModuleDTO.setTest_Scenario_Name(Scenario);
				oTestNGModuleDTO.setTESTCLASS(ClassName);
				
				list.add(oTestNGModuleDTO);
				
			}
			
		} catch (FilloException e) {
			
			e.printStackTrace();
		}
		
		finally{
			rs.close();
			con.close();
		}
		return list;
		
	}

}
