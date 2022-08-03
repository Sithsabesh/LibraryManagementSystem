package com.jmi.library.users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jmi.library.utility.DbConnection;

public class UserOperation {
	Scanner scObj = new Scanner(System.in);

	public void viewUser() throws IOException, SQLException {
		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			String sql = "select * from users where role='user'";
			ResultSet rsObj = stmtObj.executeQuery(sql);
			System.out.println("\n-----------------------");
			System.out.println("USER ID\t  NAME\t  ROLE");
			System.out.println("------------------------");
			while (rsObj.next()) {
				System.out.println(rsObj.getString(1) + "\t" + rsObj.getString(2) + "\t" + rsObj.getString(4));
			}
		}

		catch (Exception e) {
			System.out.println("\n"+e.getMessage());
		}
		
		finally {
			stmtObj.close();
			conObj.close();
		}

	}

	
	public void addUser() throws IOException, SQLException {
		System.out.println("\nEnter The Name For User");		
		String userName = scObj.next();
		System.out.println("\nEnter The Password For User");
		String userPassword = scObj.next();
		System.out.println("\nEnter the Role");
		String role = scObj.next();

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			System.out.println("\nInserting records into the table...\n");
			String sql = "Insert into users (name, password, role) values ('" + userName + "' , '" + userPassword + "', '" + role + "')";
			stmtObj.executeUpdate(sql);
			System.out.println("User Added!\n");
			viewUser();
		} catch (Exception e) {
			System.out.println("\n"+e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}

	}

}
