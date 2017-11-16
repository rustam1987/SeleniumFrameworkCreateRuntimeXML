package com.wmc.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestNGExecutor {
	
	public void buildXML(HashMap<String,List<TestNGModuleDTO>> map_Suite){
		int iMethodOrder=1;
		System.out.println(map_Suite.size());
		List<TestNGModuleDTO> list;
		for(String key:map_Suite.keySet()){
			System.out.println("---------------------------------------------------------------------------->");
			
			list=map_Suite.get(key);
			
			XmlSuite suite=null;
			List<XmlSuite> suites=null;
			
			List<XmlClass> classes=new ArrayList<XmlClass>();
			XmlClass xmClass=new XmlClass();
			List<XmlInclude> includeMethods=new ArrayList<XmlInclude>();
			
			for(TestNGModuleDTO o:list){
				suite=new XmlSuite();
				suite.setName(key);
				suite.setThreadCount(1);
				suite.setPreserveOrder("true");
				suite.setVerbose(3);
				
				
				XmlTest test=new XmlTest(suite);
				test.setPreserveOrder("true");
				test.setName(o.getTest_Scenario_Name());
				if(!classes.contains(xmClass)){
					xmClass.setName(o.getTESTCLASS());
					 includeMethods.add(new XmlInclude(o.getMethod(), iMethodOrder));
					 xmClass.setIncludedMethods(includeMethods);
					 classes.add(xmClass);
					 
				}
				else{
					includeMethods.add(new XmlInclude(o.getMethod(), iMethodOrder));
					xmClass.setIncludedMethods(includeMethods);
				}
				test.setClasses(classes);
				suites=new ArrayList<XmlSuite>();
				suites.add(suite);
				iMethodOrder++;
			}
			System.out.println(suite.toXml());
			TestNG tng=new TestNG();
			tng.setXmlSuites(suites);
			tng.setPreserveOrder(true);
			tng.run();	
			
		}
	
	}

}
