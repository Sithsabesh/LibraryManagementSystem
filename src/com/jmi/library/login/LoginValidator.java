package com.jmi.library.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jmi.library.users.AdminPanel;
import com.jmi.library.users.UserPanel;
import com.jmi.library.utility.DbConnection;

public class LoginValidator {
	int userId;

	@SuppressWarnings("resource")
	public void validation() throws SQLException, IOException {
		int userChoice;
		boolean loop = true;
		Scanner scObj = new Scanner(System.in);
		while (loop) {
			try {
				System.out.println("\nJMI LIBRARY LOGIN\n");
				System.out.println("1.USER\n2.ADMIN\n3.CANCEL\n");
				System.out.println("Press 1 for User or 2 for Admin. Choose your role and press enter.");
				userChoice = scObj.nextInt();
				switch (userChoice) {
				case 1:
					boolean isValidUser = validateUser("user");
					if (isValidUser) {
						System.out.print("\nWelcome to our library\n");
						UserPanel userPanelObj = new UserPanel();
						userPanelObj.userOptions(userId);

					} else {
						System.out.println("\nInvalid Credentials. Please Check your UserName and Password.\n");
					}
					break;

				case 2:
					boolean isValidAdmin = validateUser("admin");
					if (isValidAdmin) {
						System.out.print("\nWelcome to our library\n");
						AdminPanel adminPanelObj = new AdminPanel();
						adminPanelObj.adminOptions();

					} else {
						System.out.println("\nInvalid Credentials. Please Check your UserName and Password.\n");
					}
					break;
				case 3:
					System.out.println("\nExiting Login!\n");
					loop = false;
					break;
				default:
					break;
				}
			} catch (SQLException | IOException e) {
				throw (e);
			}
		}

	}

	@SuppressWarnings("resource")
	private boolean validateUser(String role) {
		Scanner scObj = new Scanner(System.in);
		System.out.println("\nEnter your Username");
		String username = scObj.next();
		System.out.println("\nEnter your Password");
		String password = scObj.next();

		try {

			Connection conObj = DbConnection.getConnection();
			Statement stmtObj = conObj.createStatement();

			String sqlUser = ("select * from users where role='" + role + "' and name='" + username + "' and password='"
					+ password + "'");
			ResultSet rsObjUser = stmtObj.executeQuery(sqlUser);

			if (rsObjUser.next()) {
				System.out.print("\nWelcome to our library," + "\t" + rsObjUser.getString(2) + "\n");
				userId = rsObjUser.getInt(1);
				return true;
			} else {
				System.out.println("\nInvalid Credentials. Please Check your UserName and Password.\n");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * finally { stmtObj.close(); conObj.close();
		 * 
		 * }
		 */
		return false;
	}
}
