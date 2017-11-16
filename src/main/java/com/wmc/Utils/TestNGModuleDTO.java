package com.wmc.Utils;

public class TestNGModuleDTO {
	
	private String TCID;
	private String Test_Scenario_Name;
	private String Method;
	private String TESTCLASS;
	
	
	public void setTCID(String tCID){
		TCID=tCID;
	}
	public String getTCID(){
		return TCID;
	}
	public void setTest_Scenario_Name(String test_Scenario_Name){
		Test_Scenario_Name=test_Scenario_Name;
	}
	public String getTest_Scenario_Name(){
		return Test_Scenario_Name;	
	}
	public void setMethod(String method){
		Method=method;
	}
	public String getMethod(){
		return Method;
		
	}
    public void setTESTCLASS(String tESTCLASS)	{
    	TESTCLASS=tESTCLASS;
    }
    public String getTESTCLASS(){
		return TESTCLASS;
    	
    }
	

}
