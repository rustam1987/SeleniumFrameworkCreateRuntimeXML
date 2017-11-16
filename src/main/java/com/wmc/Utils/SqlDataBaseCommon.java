package com.wmc.Utils;


import java.sql.*;

import com.mysql.jdbc.Connection;
import com.wmc.UtilCommon.Generic_methods;

public class SqlDataBaseCommon {
	public String getSingDataFromDataBase(int num) throws SQLException{
		Generic_methods o=new Generic_methods();
		String url=o.getPropertyVal("Database_url");
		String user=o.getPropertyVal("DataBase_user");
		String pass=o.getPropertyVal("DataBase_pass");
		Connection con=null;
		String query="select * from students.information where id=100;";
        String dat="";
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			 con=(Connection) DriverManager.getConnection(url, user, pass);
			Statement st= con.createStatement();
			ResultSet rs=st.executeQuery(query);
			while(rs.next()){
				 dat=rs.getString(num);
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		 finally{
			 con.close();
		 }
		
		return dat;
		
	}
public static void main(String[]args) throws SQLException{
	SqlDataBaseCommon o=new SqlDataBaseCommon();
	String s=o.getSingDataFromDataBase(4);
	System.out.println(s);
}
	
}
