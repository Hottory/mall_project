package com.nike.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBconnector {
	public static Connection getConnect() throws Exception{
		Connection con=null;
		String user="nike";
		String password="nike";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		//String url="jdbc:oracle:thin:@192.168.0.78:1521:xe";
		String driver="oracle.jdbc.driver.OracleDriver";
				
		Class.forName(driver);
		
		con=DriverManager.getConnection(url, user, password);
		
		return con;
	}
	
	public static void disConnect(ResultSet rs, PreparedStatement st, Connection con) {
		try {
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void disConnect(PreparedStatement st, Connection con) {
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
