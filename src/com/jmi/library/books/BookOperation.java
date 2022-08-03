package com.jmi.library.books;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.jmi.library.utility.DbConnection;

public class BookOperation {

	Scanner scObj = new Scanner(System.in);

	String sqlQuery = "select bid from books where isActive='true' and bid  not in(select DISTINCT BookId from issued_books where Return_Date is null) and bid=\"\n"
			+ "+ bookId";

	public void viewBook() throws IOException, SQLException {
		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();
		try {
			ResultSet rsobj = stmtObj.executeQuery("select * from books");
			System.out.println("\n-------------------------------");
			System.out.println("BID\tBNAME\tGENRE\tPRICE\t");
			System.out.println("-------------------------------");
			while (rsobj.next()) {
				System.out.println(rsobj.getInt(1) + "   " + rsobj.getString(2) + "   " + rsobj.getString(3) + "   "
						+ rsobj.getInt(4));
			}
		}

		catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}

		finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void addBook() throws IOException, SQLException {
		System.out.println("\nEnter Book Details\n");
		System.out.println("Enter Book Name");
		String bookName = scObj.next();
		System.out.println("Enter Genre");
		String genre = scObj.next();
		System.out.println("Enter Price");
		int price = scObj.nextInt();

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			System.out.println("\nInserting records into the table...\n");
			String sql = "Insert into books (bname, genre, price) values ('" + bookName + "' , '" + genre + "', '"
					+ price + "')";
			stmtObj.executeUpdate(sql);
			System.out.println("Book Added!\n");

			viewBook();

		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void availableBooks() throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			System.out.println("\nBooks Available\n");
			String sql = ("select bid, bname, genre, price from books where isActive='true' and bid not in(select DISTINCT BookId from issued_books where Return_Date is null)");
			stmtObj.executeQuery(sql);

			ResultSet rsObj = stmtObj.executeQuery(sql);
			System.out.println("-------------------------------");
			System.out.println("BID\tBNAME\tGENRE\tPRICE\t");
			System.out.println("-------------------------------");
			while (rsObj.next()) {
				System.out.println(+rsObj.getInt(1) + "  " + rsObj.getString(2) + "  " + rsObj.getString(3) + "  "
						+ rsObj.getInt(4));
			}
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}

	}

	public void chooseBook(int userId) throws IOException, SQLException {
		availableBooks();
		System.out.println("\nDo you want to take book?");
		System.out.println("Press 1 for yes!");
		System.out.println("Press 2 for Cancel");
		int userchoice = scObj.nextInt();

		switch (userchoice) {
		case 1:
			new BookOperation().takeBook(userId);
			break;

		case 2:
			System.out.println("\nCancelled");
			break;

		default:
			break;

		}

	}

	public void takeBook(int userId) throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();
		try {
			System.out.println("\nEnter the Details\n");
			System.out.println("Enter Book ID :");
			int bookId = scObj.nextInt();
			ResultSet rsObj = stmtObj.executeQuery(sqlQuery);

			if (!rsObj.next()) {
				System.out.println("\nThe Given Book ID is Incorrect");
			}

			else {

				System.out.println("Enter Issuing Date :");
				String issuedDate = scObj.next();
				System.out.println("Enter Period");
				int period = scObj.nextInt();
				String sql = "Insert into issued_books (UserId, BookId, Issued_date, Period) values ('" + userId
						+ "' , '" + bookId + "', '" + issuedDate + "', '" + period + "')";
				stmtObj.executeUpdate(sql);
				System.out.println("\nBook Purchased!");
			}

		}

		catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void myBooks(int userId) throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();
		try {
			String sql = ("SELECT  books.bid, books.bname, books.genre FROM books INNER JOIN issued_books ON issued_books.BookId = books.bid where issued_books.Return_Date is null and issued_books.UserId="
					+ userId);
			ResultSet rsObj = stmtObj.executeQuery(sql);

			if (!rsObj.next()) {
				System.out.println("\nSorry, You have no books");
			}

			else {
				System.out.println("\nMy Books\n");
				System.out.println("----------------------------------");
				System.out.println("BOOK ID\tBOOK NAME\tGENRE");
				System.out.println("----------------------------------");
				ResultSet rsObj1 = stmtObj.executeQuery(sql);
				while (rsObj1.next())
					System.out.println(rsObj1.getInt(1) + "\t" + rsObj1.getString(2) + "\t" + rsObj1.getString(3));
			}

		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}

	}

	public void returnBook(int userId) throws IOException, SQLException {
		int fine = 0;
		String issuedDate = null;
		int extraDays = 0;
		int issuedId = 0;
		int period = 0;

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			ResultSet rsObj = stmtObj
					.executeQuery("select BookId from issued_books where Return_Date is null and UserId=" + userId);

			if (!rsObj.next()) {
				System.out.println("\nYou have no books to return");

			}

			else {
				myBooks(userId);

				System.out.println("\nEnter Your Book ID");
				int bookId = scObj.nextInt();

				ResultSet rsObj2 = stmtObj.executeQuery(
						"select id,Issued_Date, Period from issued_books where Return_Date is null and BookId="
								+ bookId);

				if (!rsObj2.next()) {
					System.out.println("\nThe Given BookID is Incorrect");
				}

				else {

					issuedId = rsObj2.getInt(1);
					issuedDate = rsObj2.getString(2);
					period = rsObj2.getInt(3);

					System.out.println("Issued Date : " + issuedDate);
					System.out.println("\nEnter Returning date :");
					String returnDate = scObj.next();
					String rDate = returnDate;

					Date dateOne = new SimpleDateFormat("dd-MM-yyyy").parse(issuedDate);
					Date dateTwo = new SimpleDateFormat("dd-MM-yyyy").parse(rDate);
					long diff = dateTwo.getTime() - dateOne.getTime();
					extraDays = (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					System.out.println("Total Days :" + extraDays);
					System.out.println("Period : " + period);

					if (extraDays > period) {
						fine = (extraDays - period) * 100;
					}

					stmtObj.executeUpdate("UPDATE issued_books SET Fine=" + fine + " , Return_Date='" + returnDate
							+ "' WHERE Return_Date is null and id=" + issuedId);
					System.out.println("Fine Amount : " + fine);
					System.out.println("\nYour Book has been Returned!\n");
				}
			}
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		} finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void issuedBook() throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();
		try {
			String sql = ("select users.id,users.name,books.bname,books.bid,issued_books.Issued_Date,issued_books.Return_Date,issued_books.Period,issued_books.Fine from issued_books inner join books on issued_books.BookId=books.bid inner join users on issued_books.UserId=users.id");
			ResultSet rsObj = stmtObj.executeQuery(sql);

			if (!rsObj.next()) {
				System.out.println("\nYou have no Issued Books");
			}

			else {
				System.out.println("\nIssued Books\n");
				System.out.println(
						"----------------------------------------------------------------------------------------");
				System.out.println("USER ID\tUSERNAME   BOOKNAME\tBOOK ID\tISSUEDDATE\tRETURNDATE\tPERIOD\tFINE");
				System.out.println(
						"----------------------------------------------------------------------------------------");
				while (rsObj.next()) {
					System.out.println(rsObj.getInt(1) + "\t" + rsObj.getString(2) + "\t" + rsObj.getString(3) + "\t"
							+ rsObj.getInt(4) + "\t" + rsObj.getString(5) + "\t" + rsObj.getString(6) + "\t"
							+ rsObj.getInt(7) + "\t" + rsObj.getInt(8));
				}
			}
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}

		finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void updateBookName() throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			System.out.println("\nEnter Book ID which you want to Rename");
			int bookId = scObj.nextInt();
			ResultSet rsObj = stmtObj.executeQuery(sqlQuery);

			if (!rsObj.next()) {
				System.out.println("\nThe Given BookID cannot be Renamed!");
			}

			else {
				System.out.println("\nEnter New Name");
				String bookName = scObj.next();
				System.out.println("\nRenaming a book...\n");
				String sql = ("UPDATE books SET bname='" + bookName + "'where bid=" + bookId);
				stmtObj.executeUpdate(sql);
				System.out.println("Book Renamed!\n");

				viewBook();
			}

		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}

		finally {
			stmtObj.close();
			conObj.close();
		}
	}

	public void removeBook() throws IOException, SQLException {

		Connection conObj = DbConnection.getConnection();
		Statement stmtObj = conObj.createStatement();

		try {
			availableBooks();
			System.out.println("\nEnter Book ID which you want to Remove");
			int bookId = scObj.nextInt();
			ResultSet rsObj = stmtObj.executeQuery(sqlQuery);

			if (!rsObj.next()) {
				System.out.println("\nThe Given bookID cannot be Removed!");
			}

			else {

				System.out.println("\nRemoving Book!\n");
				String sql = ("update books set isActive='false' where bid=" + bookId);
				stmtObj.executeUpdate(sql);
				System.out.println("Book Removed!\n");
				availableBooks();
			}

		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}

		finally {
			stmtObj.close();
			conObj.close();
		}
	}
}
