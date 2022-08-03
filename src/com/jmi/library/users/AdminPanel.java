package com.jmi.library.users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.jmi.library.books.*;

public class AdminPanel {

	@SuppressWarnings("resource")
	public void adminOptions() throws IOException, SQLException {
		BookOperation bookOperationObj = new BookOperation();
		UserOperation userOperationObj = new UserOperation();
		boolean loop = true;
		while (loop) {
			System.out.print(
					"\n1.Add User\t2.Add Books\t3.View Users\t4.View Books\t5.Issued Book\t\n6.Books Available\t7.Update Book Name\t8.Remove Book\t9.Exit\t\n");
			Scanner scObj = new Scanner(System.in);
			System.out.println("\nEnter your choice");
			int adminchoice = scObj.nextInt();

			switch (adminchoice) {
			case 1:
				userOperationObj.addUser();
				break;

			case 2:
				bookOperationObj.addBook();
				break;
			case 3:
				userOperationObj.viewUser();
				break;
			case 4:
				bookOperationObj.viewBook();
				break;
			case 5:
				bookOperationObj.issuedBook();
				break;
			case 6:
				bookOperationObj.availableBooks();
				break;
			case 7:
				bookOperationObj.updateBookName();
				break;
			case 8:
				bookOperationObj.removeBook();
				break;
			case 9:
				System.out.println("\nExiting Admin...!\n");
				loop = false;
				break;

			default:
				break;

			}
		}
	}

}
