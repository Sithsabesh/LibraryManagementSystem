package com.jmi.library.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginPanel {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int userchoice;
		LoginValidator loginValidaterObj = new LoginValidator();
		boolean loop = true;
		while (loop) {
			System.out.println("JMI LIBRARY\n");
			System.out.println("1.Login");
			System.out.println("2.Exit\n");
			Scanner scObj = new Scanner(System.in);
			System.out.println("Press 1 for Login or 2 for cancel and press enter.");
			userchoice = scObj.nextInt();

			switch (userchoice) {
			case 1:
				try {
					loginValidaterObj.validation();
				} catch (SQLException | IOException e) {
					System.out.println("\nCannot fetch details from database\n");
				}
				break;

			case 2:
				System.out.println("\nBYE!");
				loop = false;
				break;

			default:
				break;

			}

		}
	}
}
