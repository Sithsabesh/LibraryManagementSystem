package com.jmi.library.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConnection {
	
	private static final String DB_URL="url";
	private static final String DB_USERNAME="userName";
	private static final String DB_PASSWORD="password";
	private static final String PROPERTY_FILE_LOCATION="/home/sithsabesh/workspace/LibraryManagementSystem/src/system.properties";
	
	
	public static Connection getConnection() throws IOException, SQLException {
		Connection connectObj = null;
		try {
		InputStream input = new FileInputStream(PROPERTY_FILE_LOCATION);
		Properties propObj = new Properties();
		
		propObj.load(input);		
		connectObj = DriverManager.getConnection(propObj.getProperty(DB_URL), propObj.getProperty(DB_USERNAME),
				propObj.getProperty(DB_PASSWORD));
		}catch(Exception e) {
			e.getMessage();
		}
		return connectObj;
	}
		
}