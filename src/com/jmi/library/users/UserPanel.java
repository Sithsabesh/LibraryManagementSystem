package com.jmi.library.users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import com.jmi.library.books.BookOperation;

public class UserPanel {

	@SuppressWarnings("resource")
	public void userOptions(int userId) throws IOException, SQLException {
		int userChoice;
		BookOperation bookOperationObj = new BookOperation();
		boolean loop = true;
		while (loop) {
			System.out.println("\n1.ViewBooks");
			System.out.println("2.My Books");
			System.out.println("3.Take Book");
			System.out.println("4.Return Book");
			System.out.println("5.Exit\n");
			Scanner scObj = new Scanner(System.in);
			System.out.println("Enter your choice");
			userChoice = scObj.nextInt();

			switch (userChoice) {
			case 1:
				bookOperationObj.viewBook(); 
				break;

			case 2:
				bookOperationObj.myBooks(userId); 
				break;

			case 3:
				bookOperationObj.chooseBook(userId);
				break;
			case 4:
				bookOperationObj.returnBook(userId); 
				break;

			case 5:
				System.out.println("\nThankyou for Visiting US. Hope you had a good time! BYE..\n");
				loop = false;
				break;

			default:
				break;

			}
		}
	}

}
