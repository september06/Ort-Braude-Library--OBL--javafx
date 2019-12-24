package echoServer;


import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import FileHandling.FileTransmitor;
import Protocols.*;
import javafx.scene.control.Alert.AlertType;
import Methods.*;
import Methods.Book.Demand;
import Methods.Copy.Availability;
import Methods.Librarian.Department;
import Methods.Librarian.Role;
import Methods.Subscriber.ReaderCardStatus;


public class DataBaseManagemer {
private static final DataBaseManagemer dBManager = new DataBaseManagemer();
	
	public static DataBaseManagemer getDBManager() {
		return dBManager;
	}
	
	private DataBaseManagemer() {};
	
	
	private Connection DBCon;

	/**
	 * indicates if connected to DataBase
	 */
	private boolean isDBConnected = false;

	/**
	 * The Default DataBase Data
	 */
	private String root  ;
	private String passWord  ;
	
	public  Connection connection(String userName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			/* handle the error */
		}
		try {
			this.root = userName;
			this.passWord = password;
			
			DBCon = DriverManager.getConnection("jdbc:mysql://localhost/obl", root, passWord);
			System.out.println("SQL connection succeed");
			DBCon.close();
			return DBCon;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}
	
	/*
	 *
	 */
	
	public Response HandleInsertNewBook(Book book) {
		Savepoint svp = null;
		try {
			svp = DBCon.setSavepoint();
		} catch (SQLException sqlx) {
			return new Response(RequestType.InsertNewBook, false, null,
					"Failed to insert book.\nError: " + sqlx.getMessage());
		}

		Response res = this._HandleInsertNewBook(book);
		if (!res.isSucceeded())
			return res;

		try {
			for (int serial = 1; serial <= book.getTotalCopiesNumber(); serial++) {
				PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO bookcopies Values(?,?,?)");
				pstmt.setString(1, book.getISBN());
				pstmt.setInt(2, serial);
				pstmt.setString(3, Availability.Available.toString());
				pstmt.executeUpdate();
			}
			return new Response(RequestType.InsertNewBook, true, null,
					"Book has been successfully added to inventory.");
		} catch (SQLException ex) {
			try {
				DBCon.rollback(svp);
			} catch (SQLException sqlx) {
				return new Response(RequestType.InsertNewBook, false, null,
						"Failed to insert book.\n Error: " + sqlx.getMessage());
			}
			return new Response(RequestType.InsertNewBook, false, null,
					"Failed to insert book.\n Error: " + ex.getMessage());
		}
	}
	
	private Response _HandleInsertNewBook(Book book)
	{
		Response toRet = null;
		
		try
		{
			int i=1;
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO Books Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(i++, book.getISBN());
			pstmt.setString(i++, book.getTitle());
			pstmt.setString(i++, book.getAuthors());
			pstmt.setInt(i++, book.getEdition());
			pstmt.setDate(i++, book.getPublicationDate());
			pstmt.setDate(i++, book.getPurchaseDate());
			pstmt.setString(i++, book.getCategory());
			pstmt.setString(i++, book.getCatalogue());
			pstmt.setString(i++, book.getDepartment());
			pstmt.setInt(i++, book.getTotalCopiesNumber());
			pstmt.setInt(i++, book.getAvailableCopiesNumber());
			pstmt.setString(i++, book.getShelf());
			pstmt.setString(i++, book.getDemand().toString());
			pstmt.setString(i++, book.getDescription());
			pstmt.setString(i++, Boolean.toString(book.isDeleted()));
			pstmt.setString(i++, "/Books/Contents/"+book.getISBN());
			pstmt.setString(i++, "/Books/Pictures/"+book.getISBN());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.InsertNewBook, true, null, "Book has been successfully added to inventory.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.InsertNewBook, false, null, "Failed to add new book.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleUpdateBook(Book book)
	{
		Response toRet = null;
		
		try
		{
			int i=1;
			PreparedStatement pstmt = DBCon.prepareStatement("UPDATE Books set Title=?, Author=?, Edition=?, Publication=?, PurchaseDate=?, "
					+ "Category=?, Catalogue=?, TotalCopies=?, AvailableCopies=?, Shelf=?, Demand=?, ContentsTable=?, Picture=?, Description=?, Deleted=? WHERE ISBN=?");
			pstmt.setString(i++, book.getTitle());
			pstmt.setString(i++, book.getAuthors());
			pstmt.setInt(i++, book.getEdition());
			pstmt.setDate(i++, book.getPublicationDate());
			pstmt.setDate(i++, book.getPurchaseDate());
			pstmt.setString(i++, book.getCategory());
			pstmt.setString(i++, book.getCatalogue());
			pstmt.setInt(i++, book.getTotalCopiesNumber());
			pstmt.setInt(i++, book.getAvailableCopiesNumber());
			pstmt.setString(i++, book.getShelf());
			pstmt.setString(i++, book.getDemand().toString());
			pstmt.setString(i++, "/Books/Contents/"+book.getISBN());
			pstmt.setString(i++, "/Books/Pictures/"+book.getISBN());
			pstmt.setString(i++, book.getDescription());
			pstmt.setString(i++, Boolean.toString(book.isDeleted()));
			pstmt.setString(i, book.getISBN());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.UpdateBook, true, null, "Book has been successfully updated.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.UpdateBook, false, null, "Failed to update book.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleDeleteBook(Book book)
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM Books WHERE ISBN=?");
			pstmt.setString(1, book.getISBN());
			pstmt.executeUpdate();
			
			toRet= new Response(RequestType.DeleteBook, true, null, "Book has been successfully deleted.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.DeleteBook, false, null, "Failed to delete book.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleInsertNewCopies(Request req) {
		Response toRet = null;
		ArrayList<String> data = (ArrayList<String>) req.getData();
		int copies = Integer.parseInt(data.get(1));// 1=how many copies to add
		int toadd=0;
		try {
			for (toadd=1; toadd <= copies; toadd++) {
				PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO bookcopies Values(?,?,?)");
				pstmt.setString(1, data.get(0));
				pstmt.setInt(2, toadd);
				pstmt.setString(3, Availability.Available.toString());
				pstmt.executeUpdate();
			}	
			toRet = new Response(RequestType.InsertNewCopies, true, null,
					toadd + " new copies have been successfully inserted.\n" + (copies - toadd) + " failed.");

		} catch (SQLException ex) {
			toRet = new Response(RequestType.InsertNewCopies, false, null, "Failed to add copies.\n" + toadd
					+ " copies where added, " + (copies-toadd) + " failed.\n" + ex.getMessage());
		}

		return toRet;
	}

	public Response HandleUpdateCopy(Copy copy)
	{	
		Savepoint svp=null;
		try {
			svp = DBCon.setSavepoint();
		}
		catch(SQLException sqlx) {
			return new Response(RequestType.UpdateCopy, false, null, "Failed to update copy.\nError: "+sqlx.getMessage());
		}
		
		try
		{
			Availability beforeChange = getCopyAvailability(copy.getISBN(), copy.getSerialCode());
			if(beforeChange == copy.getAvailability()) {
				return new Response(RequestType.UpdateCopy, true, null, "Copy has been successfully updated."); //no need to change anything
			}
			else {
				PreparedStatement pstmt = DBCon.prepareStatement("Update bookcopies SET Availability=? WHERE ISBN=? AND SerialCode=?");
				pstmt.setString(1, copy.getAvailability().toString());
				pstmt.setString(2, copy.getISBN());
				pstmt.setInt(3, copy.getSerialCode());
				int n = pstmt.executeUpdate();
				
				//update Book
				if(n>0) {
					String changestr;
					if(copy.getAvailability()==Availability.Available)
						changestr = "AvailableCopies=AvailableCopies+1";
					else if(beforeChange == Availability.Available)
						changestr = "AvailableCopies=AvailableCopies-1";
					else
						return new Response(RequestType.UpdateCopy, true, null, "Copy has been successfully updated."); //no need to update book
					
					pstmt = DBCon.prepareStatement("Update books SET "+changestr+" WHERE ISBN=?");
					pstmt.setString(1, copy.getISBN());
					pstmt.executeUpdate();
				}
				return new Response(RequestType.UpdateCopy, true, null, "Copy has been successfully updated.");
			}
		}
		catch(SQLException ex)
		{
			try {
				DBCon.rollback(svp);
			}
			catch(SQLException sqlx) {
				return new Response(RequestType.UpdateCopy, false, null, "Failed to update copy.\nError: "+sqlx.getMessage());
			}
			
			return new Response(RequestType.UpdateCopy, false, null, "Failed to update copy.\nError: "+ex.getMessage());
		}
	}

	//Helper Function
	private Availability getCopyAvailability(String isbn, int serialCode) throws SQLException
	{
		PreparedStatement pstmt = DBCon.prepareStatement("SELECT Availability From bookcopies WHERE ISBN=? AND SerialCode=?");
		pstmt.setString(1, isbn);
		pstmt.setInt(2, serialCode);
		ResultSet res = pstmt.executeQuery();
		if(res.next())
			 return Availability.valueOf(res.getString(1));
		else
			throw new SQLException("Invalid PK for a Copy:<"+isbn+", "+serialCode+">\n");
	}

	public Response HandleDeleteCopy(Copy copy)
	{
		Savepoint svp=null;
		try {
			svp = DBCon.setSavepoint();
		}
		catch(SQLException sqlx) {
			return new Response(RequestType.DeleteCopy, false, null, "Failed to delete copy.\n"+sqlx.getMessage());
		}
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM bookcopies WHERE ISBN=? AND SerialCode=?");
			pstmt.setString(1, copy.getISBN());
			pstmt.setInt(1, copy.getSerialCode());
			pstmt.executeUpdate();
			
			pstmt = DBCon.prepareStatement("UPDATE Books SET TotalCopies=TotalCopies-1, AvailableCopies=AvailableCopies-1 WHERE ISBN=?");
			pstmt.setString(1, copy.getISBN());
			pstmt.executeUpdate();
			
			
			return new Response(RequestType.DeleteCopy, true, null, "Copy has been successfully deleted.");
		}
		catch(SQLException ex)
		{
			try {
				DBCon.rollback(svp);
			}
			catch(SQLException sqlx) {
				return new Response(RequestType.DeleteCopy, false, null, "Failed to delete copy.\n"+sqlx.getMessage());
			}
			
			return new Response(RequestType.DeleteCopy, false, null, "Failed to delete copy.\n"+ex.getMessage());
		}
	}

	public Response HandleInsertNewOrder(Order order) 
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO Orders Values(?,?,?,?,?,?)");
			pstmt.setString(1, order.getSubscriberID());
			pstmt.setTimestamp(2, order.getIssueDate());
			pstmt.setString(3, order.getISBN());
			pstmt.setDate(4, order.getSaveDate());
			pstmt.setString(5, order.getActivityName());
			pstmt.setString(6, Boolean.toString(order.isRealised()));
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.InsertNewOrder, true, null, "Order has been successfully saved.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.InsertNewOrder, false, null, "Failed to save new order.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleUpdateOrder(Order order) 
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("Update Orders SET SaveDate=?, Realised=? WHERE OrdererID=? AND IssueDate=?");
			pstmt.setDate(1, order.getSaveDate());
			pstmt.setString(2, Boolean.toString(order.isRealised()));
			pstmt.setString(3, order.getSubscriberID());
			pstmt.setTimestamp(4, order.getIssueDate());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.UpdateOrder, true, null, "Order has been successfully updated.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.UpdateOrder, false, null, "Failed to updated order.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleDeleteOrder(Order order) 
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM Orders WHERE OrdererID=? AND IssueDate=?");
			pstmt.setString(1, order.getSubscriberID());
			pstmt.setTimestamp(2, order.getIssueDate());
			
			pstmt.executeUpdate();
			
			this.NotifyNextOrderer(order.getISBN());
			toRet= new Response(RequestType.DeleteOrder, true, null, "Order has been successfully deleted.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.DeleteOrder, false, null, "Failed to delete order.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleInsertNewBorrow(Borrow borrow) 
	{
		Savepoint svp=null;
		try {
			svp = DBCon.setSavepoint();
		}
		catch(SQLException sqlx) {
			return new Response(RequestType.InsertNewBorrow, false, null, "Failed to save new borrow.\n"+sqlx.getMessage());
		}
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO Borrows Values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, borrow.getSubscriberID());
			pstmt.setTimestamp(2, borrow.getIssueDate());
			pstmt.setString(3, borrow.getActivityName());
			pstmt.setString(4, borrow.getCopyISBN());
			pstmt.setInt(5, borrow.getCopySerialCode());
			pstmt.setDate(6, borrow.getDueDate());
			pstmt.setDate(7, borrow.getReturnDate());
			pstmt.setString(8, Boolean.toString(borrow.isExtendable()));
			
			pstmt.executeUpdate();
			
			//Update Borrowed Copy
			Response ret = HandleUpdateCopy(new Copy(borrow.getCopyISBN(), borrow.getCopySerialCode(), Availability.Borrowed));
			
			if(ret.isSucceeded())
				return new Response(RequestType.InsertNewBorrow, true, null, "Borrow has been successfully saved.");
			else {
				DBCon.rollback(svp);
				return new Response(RequestType.InsertNewBorrow, false, null, "Failed to save new borrow.\n"+ret.getMessage());
			}
		}
		catch(SQLException ex)
		{
			try {
				DBCon.rollback(svp);
			}
			catch(SQLException sqlx) {
				return new Response(RequestType.InsertNewBorrow, false, null, "Failed to save new borrow.\n"+sqlx.getMessage());
			}
			
			return new Response(RequestType.InsertNewBorrow, false, null, "Failed to save new borrow.\n"+ex.getMessage());
		}
	}
	
	public Response HandleUpdateBorrow(Borrow borrow) 
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("Update Borrows SET Name=?, DueDate=?, ReturnDate=?, Extendable=? WHERE "
					+ "BorrowerID=? AND IssueDate=?");
			pstmt.setString(1, borrow.getActivityName());
			pstmt.setDate(2, borrow.getDueDate());
			pstmt.setDate(3, borrow.getReturnDate());
			pstmt.setString(4, Boolean.toString(borrow.isExtendable()));
			pstmt.setString(5, borrow.getSubscriberID());
			pstmt.setTimestamp(6, borrow.getIssueDate());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.UpdateBorrow, true, null, "Borrow has been successfully updated.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.UpdateBorrow, false, null, "Failed to update borrow.\n"+ex.getMessage());
		}
		
		return toRet;
	}

	public Response HandleDeleteBorrow(Borrow borrow) 
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM Borrows WHERE BorrowerID=? AND IssueDate=?");
			pstmt.setString(1, borrow.getSubscriberID());
			pstmt.setTimestamp(2, borrow.getIssueDate());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.DeleteBorrow, true, null, "Borrow has been successfully deleted.");
			
		}
		catch(SQLException ex)
		{
			toRet = new Response(RequestType.DeleteBorrow, false, null, "Failed to delete borrow.\n"+ex.getMessage());
		}
		
		return toRet;
	}
	
	public Response HandleInsertNewExtension(Extension ex)
	{
		//if there's a new extension, a certain borrow must be updated		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO extensions Values(?,?,?,?,?)");
			pstmt.setString(1, ex.getSubscriberID());
			pstmt.setTimestamp(2, ex.getBorrowingDate());
			pstmt.setTimestamp(3, ex.getIssueDate());
			pstmt.setString(4, ex.getActivityName());
			pstmt.setString(5, Boolean.toString(ex.isManual()));
			
			pstmt.executeUpdate();
			return new Response(RequestType.InsertNewExtension, true, "", "Extension inserted successfully.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.InsertNewExtension, false, null, "Failed to save extension.\n"+exp.getMessage());
		}
	}

	/*
	public Response HandleUpdateExtension(Request req)
	{
		Response toRet = null;
		Extension ex = (Extension)req.getData();
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("UPDATE extensions SET ");
			pstmt.setString(1, ex.getSubscriberID());
			pstmt.setDate(2, ex.getBorrowingDate());
			pstmt.setDate(3, ex.getIssueDate());
			pstmt.setString(4, ex.getActivityName());
			pstmt.setString(5, Boolean.toString(false));
			
			pstmt.executeUpdate();
			toRet= new Response(true, null, "Extension has been successfully saved.");
			
		}
		catch(SQLException exp)
		{
			toRet = new Response(false, null, "Failed to save extension.\n"+exp.getMessage());
		}
		
		return toRet;
	}
	*/
	
	public Response HandleDeleteExtension(Extension ex)
	{
		Response toRet = null;
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM extensions WHERE ExtenderID=? AND IssueDate=?");
			pstmt.setString(1, ex.getSubscriberID());
			pstmt.setTimestamp(2, ex.getIssueDate());
			
			pstmt.executeUpdate();
			toRet= new Response(RequestType.DeleteExtension, true, null, "Extension has been successfully deleted.");
			
		}
		catch(SQLException exp)
		{
			toRet = new Response(RequestType.DeleteExtension, false, null, "Failed to delete extension.\n"+exp.getMessage());
		}
		
		return toRet;
	}

	public Response HandleInsertNewLosing(Losing los)
	{
		Savepoint svp=null;
		try {
			svp = DBCon.setSavepoint();
		}
		catch(SQLException sqlx) {
			return new Response(RequestType.InsertNewLosing, false, null, "Failed to save extension.\n"+sqlx.getMessage());
		}
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO Losings Values(?,?,?,?,?)");
			pstmt.setString(1, los.getSubscriberID());
			pstmt.setTimestamp(2, los.getIssueDate());
			pstmt.setString(3, los.getCopyISBN());
			pstmt.setInt(4, los.getCopySerialCode());
			pstmt.setString(5, los.getActivityName());
			
			pstmt.executeUpdate();
			
			//Updating Borrowed Book - no changes
			
			//Update Borrowed Copy
			Response ret = HandleUpdateCopy(new Copy(los.getCopyISBN(), los.getCopySerialCode(), Availability.Lost));
			
			if(ret.isSucceeded())
				return new Response(RequestType.InsertNewLosing, true, null, "Extension has been successfully saved.");
			else {
				DBCon.rollback(svp);
				return new Response(RequestType.InsertNewLosing,  false, null, "Failed to save extension.\n"+ret.getMessage());
			}
		}
		catch(SQLException exp)
		{
			try {
				DBCon.rollback(svp);
			}
			catch(SQLException sqlx) {
				return new Response(RequestType.InsertNewLosing, false, null, "Failed to save extension.\n"+sqlx.getMessage());
			}
			
			return new Response(RequestType.InsertNewLosing, false, null, "Failed to save extension.\n"+exp.getMessage());
		}
	}

	public Response HandleDeleteLosing(Losing los)
	{
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM Losings WHERE LoserID=? AND IssueDate=?");
			pstmt.setString(1, los.getSubscriberID());
			pstmt.setTimestamp(2, los.getIssueDate());
			
			pstmt.executeUpdate();
			
			
			return new Response(RequestType.DeleteLosing, true, null, "Losing has been successfully deleted.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.DeleteLosing, false, null, "Failed to delete losing.\n"+exp.getMessage());
		}
	}

	public Response HandleInsertNewSubscriber(Subscriber sub)
	{		
		try
		{
			//Step one - add user part
			int i=1;
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO users Values(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(i++, sub.getID());
			pstmt.setString(i++, sub.getFirstName());
			pstmt.setString(i++, sub.getLastName());
			pstmt.setString(i++, sub.getUserName());
			pstmt.setString(i++, sub.getPassword());
			pstmt.setString(i++, sub.getPhone());
			pstmt.setString(i++, sub.getEmail());
			pstmt.setString(i++, Boolean.toString(sub.isOnline()));
			pstmt.setString(i++, "/Users/Pictures/"+sub.getID()+".png");
			
			pstmt.executeUpdate();
			
		}catch(SQLException exp)
		{
			return new Response(RequestType.InsertNewSubscriber, false, null, "Failed to save Subscriber.\n"+exp.getMessage());
		}
		
		try
		{
			//Step two - add reader-card part
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO readercards Values(?,?,?)");
			pstmt.setString(1, sub.getID());
			pstmt.setString(2, Boolean.toString(sub.isGraduated()));
			pstmt.setString(3, sub.getCardStatus().toString());
			
			pstmt.executeUpdate();
			
			return new Response(RequestType.InsertNewSubscriber, true, null, "Subscriber has been successfully saved.");
		}
		catch(SQLException exp)
		{
			HandleDeleteSubscriber(sub);
			return new Response(RequestType.InsertNewSubscriber, false, null, "Failed to save Subscriber.\n"+exp.getMessage());
		}
	}
	public Response HandleUpdateSubscriber(Subscriber sub)
	{
		Savepoint svp=null;
		try {
			svp = DBCon.setSavepoint();
		}
		catch(SQLException sqlx) {
			return new Response(RequestType.UpdateSubscriber, false, null, "Failed to update Subscriber.\n"+sqlx.getMessage());
		}
		
		try
		{
			//Step one - user part
			int i=1;
			PreparedStatement pstmt = DBCon.prepareStatement("Update users SET FirstName=?, LastName=?, Username=?, Password=?, Phone=?, Email=?, OnlineStatus=? WHERE ID=?");
			pstmt.setString(i++, sub.getFirstName());
			pstmt.setString(i++, sub.getLastName());
			pstmt.setString(i++, sub.getUserName());
			pstmt.setString(i++, sub.getPassword());
			pstmt.setString(i++, sub.getPhone());
			pstmt.setString(i++, sub.getEmail());
			pstmt.setString(i++, Boolean.toString(sub.isOnline()));
			pstmt.setString(i++, sub.getID());
			
			pstmt.executeUpdate();
			
			//Step two - reader-card part
			pstmt = DBCon.prepareStatement("Update readercards SET Graduated=?, Status=? Where rID=?");
			pstmt.setString(1, Boolean.toString(sub.isGraduated()));
			pstmt.setString(2, sub.getCardStatus().toString());
			pstmt.setString(3, sub.getID());
			
			pstmt.executeUpdate();
			
			
			return new Response(RequestType.UpdateSubscriber, true, null, "Subscriber has been successfully updated.");
		}
		catch(SQLException exp)
		{
			try {
				DBCon.rollback(svp);
			}
			catch(SQLException sqlx) {
				return new Response(RequestType.UpdateSubscriber, false, null, "Failed to update Subscriber.\n"+sqlx.getMessage());
			}
			
			return new Response(RequestType.UpdateSubscriber, false, null, "Failed to update Subscriber.\n"+exp.getMessage());
		}
	}
	
	public Response HandleDeleteSubscriber(Subscriber sub)
	{
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM users WHERE ID=?");
			pstmt.setString(1, sub.getID());
			pstmt.executeUpdate();
			 //reader card part is auto cascaded on delete
			
			return new Response(RequestType.DeleteSubscriber, true, null, "Subscriber has been successfully deleted.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.DeleteSubscriber, false, null, "Failed to deleted Subscriber.\n"+exp.getMessage());
		}
	}
	
	public Response HandleInsertNewReport(Report ar)
	{
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO reports Values(?,?)");
			pstmt.setTimestamp(1, ar.getIssueDate());
			pstmt.setString(2, ar.getReportText());
			
			pstmt.executeUpdate();
			
			
			return new Response(RequestType.InsertNewReport, true, null, "Report has been successfully deleted.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.InsertNewReport, false, null, "Failed to deleted report.\n"+exp.getMessage());
		}
	}

	public Response HandleDeleteReport(Report ar)
	{
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("DELETE FROM reports WHERE IssueDate=?");
			pstmt.setTimestamp(1, ar.getIssueDate());
			
			pstmt.executeUpdate();
			return new Response(RequestType.DeleteReport, true, null, "Report has been successfully deleted.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.DeleteReport, false, null, "Failed to deleted report.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetUser(String uniqueFiled) {
		Response ret;
		try
		{
			//ID is key so unique be default, and Username is unique as well, so if it's a valid user, one of them should be true.
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM users WHERE ID=? OR Username=?");
			pstmt.setString(1,uniqueFiled);
			pstmt.setString(2,uniqueFiled);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getString("ID")+" "+Boolean.parseBoolean(rs.getString("OnlineStatus")));
				
				ret = new Response(RequestType.GetUser, true, 
						new User(rs.getString("ID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Username"), rs.getString("Password"),
						rs.getString("Phone"), rs.getString("Email"), Boolean.parseBoolean(rs.getString("OnlineStatus"))), "Success");
			}
			else
				ret = new Response(RequestType.GetUser, false, null, "Invalid User: no such user was found.\n");
		}
		catch(SQLException exp)
		{
			ret = new Response(RequestType.GetUser, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		return ret;
	}
	
	public Response HandleGetAllUsers() {
		Response ret = null;
		HashMap<String, User> users = new HashMap<String, User>();
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM users");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				users.put(rs.getString(1), new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), Boolean.parseBoolean(rs.getString(8))));
			}
			ret = new Response(RequestType.GetAllUsers, true, users, users.size()+" Users.");
			
		}
		catch(SQLException exp)
		{
			ret = new Response(RequestType.GetAllUsers, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		return ret;
	}
	
	public Response HandleGetSubscriber(String ID_Username) {
		//Step One - User Part
		Response t = HandleGetUser(ID_Username);
		if(!t.isSucceeded())
			return new Response(RequestType.GetSubscriber, false, null, t.getMessage());
		
		String rID = ((User)(t.getData())).getID();
		try {//Step Two - Reader-Card Part
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM readercards WHERE rID=?");
			pstmt.setString(1,rID);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				t = new Response(RequestType.GetSubscriber, true, new Subscriber((User)t.getData(),
						Boolean.parseBoolean(rs.getString(2)), ReaderCardStatus.valueOf(rs.getString(3))), "Success");
			}
			else return new Response(RequestType.GetSubscriber, false, null, "Invalid Subscriber ID: no such subscriber was found.\n");
		}
		catch(SQLException exp)
		{
			t = new Response(RequestType.GetSubscriber, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		return t;
	}
	
	public Response HandleGetAllSubscribers() {
		//Step One - User Part
		Response t = HandleGetAllUsers();
		if(!t.isSucceeded())
			return new Response(RequestType.GetAllSubscribers, false, null, t.getMessage());

		HashMap<String, User> users = (HashMap<String, User>)t.getData();
		ArrayList<Subscriber> subs = new ArrayList<Subscriber>();
		
		try {//Step Two - Reader-Card Part
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM readercards");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				subs.add(
						new Subscriber(users.get(rs.getString(1)), Boolean.parseBoolean(rs.getString(2)), ReaderCardStatus.valueOf(rs.getString(3))));
			}
			
			return new Response(RequestType.GetAllSubscribers, true, subs, subs.size()+" Subscribers.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetAllSubscribers, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetLibrarian(String ID_Username) {
		//Step One - User Part
		Response t = HandleGetUser(ID_Username);
		if(!t.isSucceeded())
			return new Response(RequestType.GetLibrarian, false, null, t.getMessage());
		
		String ID = ((User)(t.getData())).getID();
		try {//Step Two - Librarian Part
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM librarians WHERE libID=?");
			pstmt.setString(1,ID);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				t = new Response(RequestType.GetLibrarian, true, new Librarian((User)t.getData(),
						Role.valueOf(rs.getString(2)), Department.valueOf(rs.getString(3))), "Success");
			}
			else return new Response(RequestType.GetLibrarian, false, null, "Invalid Librarian ID: no such librarian was found.\n");
		}
		catch(SQLException exp)
		{
			t = new Response(RequestType.GetLibrarian, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		return t;
	}
	
	public Response HandleGetAllLibrarians() {
		//Step One - User Part
		Response t = HandleGetAllUsers();
		if(!t.isSucceeded())
			return new Response(RequestType.GetAllLibrarians, false, null, t.getMessage());

		HashMap<String, User> users = (HashMap<String, User>)t.getData();
		ArrayList<Librarian> libs = new ArrayList<Librarian>();
		
		try {//Step Two - Reader-Card Part
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM librarians");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				libs.add(
						new Librarian(users.get(rs.getString(1)), Role.valueOf(rs.getString(2)), Department.valueOf(rs.getString(3))));

			}
			return new Response(RequestType.GetAllLibrarians, true, libs, libs.size()+" Librarians.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetAllLibrarians, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetBook(String ISBN) {
		Response ret;
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM books WHERE ISBN=?");
			pstmt.setString(1,ISBN);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int i=1;
				ret = new Response(RequestType.GetBook, true, 
						/*
						 * Public Book(String isbn, String tilte, String authors, int edition, Date publicationDate, Date purchaseDate, 
						 * String category, String catalogue, int totalCopiesNumber, int availableCopiesNumber, String shelf,
						 * Methods.Book.Demand demand, String description, *File contentsTable,* boolean deleted)
						 */
						new Book(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getDate(i++), rs.getDate(i++),
								rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++),
								Demand.valueOf(rs.getString(i++)), rs.getString(i++), Boolean.valueOf(rs.getString(i++))), "Success");
			}
			else
				ret = new Response(RequestType.GetBook, false, null, "Invalid Books ISBN: no such book was found.\n");
			
		}
		catch(SQLException exp)
		{
			ret = new Response(RequestType.GetBook, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		return ret;
	}
	
	public Response HandleGetAllBooks() {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM books");
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Book> books = new ArrayList<Book>();
			while(rs.next()) {
				int i=1;
				books.add(new Book(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getDate(i++), rs.getDate(i++),
						rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++),
						Demand.valueOf(rs.getString(i++)), rs.getString(i++), Boolean.valueOf(rs.getString(i++))));
			}
			return new Response(RequestType.GetAllBooks, true, books, books.size()+" Books.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetAllBooks, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetCopy(Request req) {
		String ISBN;
		int SerialCode;
		try {
			ISBN=(String)(((ArrayList<String>)req.getData()).get(0));
			SerialCode=Integer.parseInt((String)(((ArrayList<String>)req.getData()).get(1)));
		}
		catch(Exception exe) {
			return new Response(RequestType.GetCopy, false, null, "Failed to fetch data.\n"+exe.getMessage()); 
		}
		
		try
		{
			Availability av  = this.getCopyAvailability(ISBN, SerialCode);
			return new Response(RequestType.GetCopy, true, new Copy(ISBN, SerialCode, av), "Success");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetCopy, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetAllCopies() {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM bookcopies");
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Copy> copies = new ArrayList<Copy>();
			while(rs.next()) {
				copies.add(new Copy(rs.getString(1), rs.getInt(2), Availability.valueOf(rs.getString(3))));
			}
			return new Response(RequestType.GetAllCopies, true, copies, copies.size()+" copies.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetAllCopies, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	
	public Response HandleGetCopiesByBook(String ISBN) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM bookcopies WHERE ISBN=?");
			pstmt.setString(1, ISBN);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Copy> copies = new ArrayList<Copy>();
			while(rs.next()) {
				copies.add(new Copy(ISBN, rs.getInt(2), Availability.valueOf(rs.getString(3))));
			}
			return new Response(RequestType.GetCopiesByBook, true, copies, copies.size()+" Copies for Book<"+ISBN+">.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetCopiesByBook, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetBorrowsBySubscriber(String subID) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE BorrowerID=?");
			pstmt.setString(1, subID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Borrow> borrows = new ArrayList<Borrow>();
			while(rs.next()) {
				/*
				 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, 
				 * String copyISBN, int copySerialCode, Date dueDate, Date returnDate, boolean extendible)
				 */
				borrows.add(new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8))));
			}
			return new Response(RequestType.GetBorrowsBySubscriber, true, borrows, borrows.size()+" Borrows for Subscriber<"+subID+">.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetBorrowsBySubscriber, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetOrdersBySubscriber(String subID) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM orders WHERE OrdererID=?");
			pstmt.setString(1, subID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Order> orders = new ArrayList<Order>();
			while(rs.next()) {
				/*
				 * public Order(String subscriberID, Timestamp issueDate, String ISBN, 
				 * Date saveDate, String activityName, boolean realised)
				 */
				orders.add(new Order(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getDate(4), rs.getString(5), Boolean.valueOf(rs.getString(6))));
			}
			return new Response(RequestType.GetOrdersBySubscriber, true, orders, orders.size()+" Orders for Subscriber<"+subID+">.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetOrdersBySubscriber, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	/*
	public Response HandleGetNextOrderByBook(String ISBN) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT OrdererID, IssueDate FROM orders Where OrderedBook=? AND SaveDate IS NULL");
			pstmt.setString(1, ISBN);
			ResultSet rs = pstmt.executeQuery();
			
			HashMap<String, Timestamp> list = new HashMap<>();
			while(rs.next()) {
				list.put(rs.getString(1), rs.getTimestamp(2));				
			}
			if(list.size()==0)
				return new Response(RequestType.GetNextOrderByBook, true, null, "No Order was found.\n");
			else {
				//
			}
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetNextOrderByBook, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}*/
	
	public Response HandleIsBookOrdered(String ISBN) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT COUNT(*) FROM orders WHERE OrderedBook=? AND (SaveDate IS NULL)");
			pstmt.setString(1, ISBN);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return new Response(RequestType.IsBookOrdered, true, rs.getInt(1), "The Books has "+rs.getInt(1)+" Orders.\n");
			}
			else return new Response(RequestType.IsBookOrdered, false, null, "Failed to fecth data.\n");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.IsBookOrdered, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	
	public Response HandleGetActivitiesBySubscriber(String subID) {
		ArrayList<Activity> acts = new ArrayList<Activity>();
		
		try
		{
			//extensions
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM extensions WHERE ExtenderID=?");
			pstmt.setString(1, subID);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				acts.add(new Extension(rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getString(4), rs.getBoolean(5)));
			}
			
			pstmt = DBCon.prepareStatement("SELECT * FROM losings WHERE LoserID=?");
			pstmt.setString(1, subID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				acts.add(new Losing(rs.getString(1), rs.getTimestamp(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
			}
			
			return new Response(RequestType.GetActivitiesBySubscriber, true, acts, acts.size()+" Extensions or Losings.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetActivitiesBySubscriber, false, "", "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	/*
	 * Switch(...){
	 * Case SearchByTitle:
	 * Case SearchByAuthors:
	 * .....
	 * Case SearchByDescription: client.sendToClien(...HandleSearch(request))
	 * 
	 */
	
	public Response HandleSearch(Request req) {
		//Preparing statement
		String where = " LIKE '%"+(String)(req.getData())+"%'";
		switch (req.getRequestType()) {
		case SearchByTitle:
			where = new String("Title"+where);
			break;
		case SearchByAuthors:
			where = new String("Author"+where);
			break;
		case SearchByCategory:
			where = new String("Category"+where);
			break;
		case SearchByDepartment:
			where = new String("Department"+where);
			break;
		case SearchByDescription:
			where = new String("Description"+where);
			break;
			/*
		case SearchByDemand:
			where = new String("Demand=?"); //data is expected to be String
			break;
		case SearchByEdition:
			where = new String("Edition=?");//data is expected to be Integer (class, not int)
			break;
		case SearchByPurchaseDate:
			where = new String("PurchaseDate=?"); //data is expected to be SQL.Date
			break;
		case SearchByPublication:
			where = new String("Publication=?"); //data is expected to be SQL.Date
			break;*/
		default: // do noting for the rest
			break;
		}
	
		
		List<Book> books = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM books WHERE "+where);
		/*	switch (req.getRequestType()) {
			case SearchByDemand:
				pstmt.setString(1, (String) (req.getData()));
				break;
			case SearchByEdition:
				pstmt.setInt(1, ((Integer) (req.getData())).intValue());
				break;
			case SearchByPurchaseDate:
			case SearchByPublication:
				pstmt.setDate(1, (Date) (req.getData()));
		*/	
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int i=1;
				books.add(new Book(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getDate(i++), rs.getDate(i++),
						rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++),
						Demand.valueOf(rs.getString(i++)), rs.getString(i++), Boolean.valueOf(rs.getString(i++))));
			}
			return new Response(req.getRequestType(), true, books, books.size()+" Books were found that match your search.\n");
		}
		catch(SQLException exp)
		{
			return new Response(req.getRequestType(), false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public LogInResponse HandleLogIn(Request req) {
		ArrayList<String> data = (ArrayList<String>) req.getData();

		boolean isSub = false, isLib = false, isMang = false;
		Response res = HandleGetUser(data.get(0)); // try by ID as subscriber

		if (!res.isSucceeded())
			return new LogInResponse(false, "Wrong Username/ID or password.");

		User user = (User) res.getData();
		if (!(user.getPassword().equals(data.get(1)))) // 1=password
			return new LogInResponse(false, "Wrong Username/ID or password.");

		//valid username and password		
		if (user.isOnline())
			return new LogInResponse(false, "User is already online.");
		
		//still offfline - can log in
		
		try {
			this.ChangeOnLineStatus(user.getID(), true);

			// prev succeeded - check if subscriber
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM readercards WHERE rID=?");
			pstmt.setString(1, user.getID());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) { //sub found
				isSub = true;
				if(ReaderCardStatus.valueOf(rs.getString(3))==ReaderCardStatus.Locked)
					return new LogInResponse(false, "You're reader card is locked. You can't log in at the moment."); 
			}

			// check if library/manager
			pstmt = DBCon.prepareStatement("SELECT * FROM librarians WHERE libID=?");
			pstmt.setString(1, user.getID());
			rs = pstmt.executeQuery();

			if (rs.next()) { //lib found
				isLib = true;
				isMang = (Department.valueOf(rs.getString(3)) == Department.Management);
			}

			/*
			 * public LogInResponse(boolean succeeded, String ID, String message,
			 * boolean isLibrarian, boolean isManager, boolean isSubscriber)
			 */
			return new LogInResponse(true, user.getID(), "Welcome " + user.getFirstName(), isLib, isMang, isSub);

		} catch (SQLException ex) {
			return new LogInResponse(false, "Log In Failure.\n" + ex.getMessage());
		}
	}
	
	public Response HandleLogOut(String ID) {
		try {
			this.ChangeOnLineStatus(ID, false);
			return new Response(RequestType.LogOut, true, null, "You have been logged out.\n");
		}catch (SQLException ex) {
			return new LogInResponse(false, "Log Out Failure.\n" + ex.getMessage());
		}
	}

	private void ChangeOnLineStatus(String id, boolean b) throws SQLException{
		PreparedStatement pstmt = DBCon.prepareStatement("Update users SET OnlineStatus=? WHERE ID=? OR Username=?");
		pstmt.setString(1, Boolean.toString(b));
		pstmt.setString(2, id);
		pstmt.setString(3, id);
		pstmt.executeUpdate();
	}
	
	public Response HandleInsertNewNotif(Notif notif) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("INSERT INTO notifys Values(?,?,?)");
			pstmt.setString(1, notif.getSubscriberID());
			pstmt.setTimestamp(2, notif.getIssueDate());
			pstmt.setString(3, notif.getMessage());
			pstmt.executeUpdate();
			
			return new Response(RequestType.InsertNewNotif, true, null, "User has been notified.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.InsertNewNotif, false, null, "Failed to deleted report.\n"+exp.getMessage());
		}
	}
	
	public Response HandleGetNotifsBySubscriber(String subID) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM notifys WHERE userID=?");
			pstmt.setString(1, subID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Notif> notifs = new ArrayList<Notif>();
			while(rs.next()) {
				notifs.add( new Notif(rs.getString(1), rs.getTimestamp(2), rs.getString(3)));
			}
			return new Response(RequestType.GetNotifsBySubscriber, true, notifs, notifs.size()+" Notifications for Subscriber<"+subID+">.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.GetNotifsBySubscriber, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleReturnBorrow(Copy copy) {
		boolean success = false;
		try {
			copy.setAvailability(this.getCopyAvailability(copy.getISBN(), copy.getSerialCode()));
			if (copy.getAvailability() != Availability.Borrowed) {
				return new Response(RequestType.ReturnBorrow, false, "", "Invalid copy: the copy you entered is not borrowed.");
			}
			
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE CopyISBN=? AND CopyCode=? AND (ReturnDate IS NULL)");
			pstmt.setString(1, copy.getISBN());
			pstmt.setInt(2, copy.getSerialCode());
			ResultSet rs = pstmt.executeQuery();
			
			Borrow borrow;
			if(rs.next()) {
				borrow = new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8)));
			}else {
				return new Response(RequestType.ReturnBorrow, false, "", "Invalid copy: the copy you entered is not borrowed.");
			}
			
			java.util.Date date= new java.util.Date();
			long time = date.getTime();
			Date today = new Date(time);
			borrow.setReturnDate(today); //returned now
			
			Response res = this.HandleUpdateBorrow(borrow);
			if (!res.isSucceeded()) {
				res.setRequestType(RequestType.ReturnBorrow);
				return res;
			}

			res = this.HandleUpdateCopy(
					new Copy(borrow.getCopyISBN(), borrow.getCopySerialCode(), Availability.Available));
			if (!res.isSucceeded()) {
				res.setRequestType(RequestType.ReturnBorrow);
				return res;
			}
			success = true;
			
			this.UpdateStatusAfterReturn(borrow.getSubscriberID());
			this.NotifyNextOrderer(borrow.getCopyISBN());
			
			return new Response(RequestType.ReturnBorrow, true, null, "Book Copy has been successfully returned.");
		} catch (SQLException sqlx) {
			if(success)
				return new Response(RequestType.ReturnBorrow, true, null, "Book Copy has been successfully returned.");
			return new Response(RequestType.ReturnBorrow, false, "", "Failed to fetch data.\n" + sqlx.getMessage());
		}
	}
	
	private void UpdateStatusAfterReturn(String subscriberID) {
		Response res = this.HandleGetSubscriber(subscriberID);
		if(!res.isSucceeded())
			return;
		
		Subscriber sub = (Subscriber)res.getData();
		
		res=this.HandleGetBorrowsBySubscriber(subscriberID);
		if(!res.isSucceeded())
			return;
		
		ArrayList<Borrow> borrows = ((ArrayList<Borrow>)res.getData());
		if(sub.isGraduated()) {
			//check if has unretuned
			boolean hasUnReturned = false;
			for (Borrow bor : borrows) 
			{
				if (bor.getReturnDate() == null) {
					hasUnReturned = true;
					break;
				}
			}
			if(hasUnReturned) 
				sub.setCardStatus(ReaderCardStatus.Suspended); //graduted with unretunred borrows
			else  sub.setCardStatus(ReaderCardStatus.Locked); //graduted without unretunred borrows
		}
		else {
			java.util.Date date = new java.util.Date();
			long time = date.getTime();
			Date today = new Date(time);

			if (sub.getCardStatus() == ReaderCardStatus.Suspended) {
				boolean hasLates = false;
				for (Borrow bor : borrows) {
					if (bor.getReturnDate() == null && bor.getDueDate().compareTo(today) < 0) {
						// DueDate is before today = delay
						hasLates = true;
						break;
					}
				}
				if (!hasLates)
					sub.setCardStatus(ReaderCardStatus.Active); // has no more delays
			}
		}
		
		//update
		this.HandleUpdateSubscriber(sub);
	}

	private void NotifyNextOrderer(String copyISBN) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM orders WHERE OrderedBook=? AND (SaveDate IS NULL)");
			pstmt.setString(1, copyISBN);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Order> orders = new ArrayList<Order>();
			while(rs.next()) {
				/*
				 * public Order(String subscriberID, Timestamp issueDate, String ISBN, 
				 * Date saveDate, String activityName, boolean realised)
				 */
				orders.add( new Order(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getDate(4), rs.getString(5), Boolean.valueOf(rs.getString(6))));
			}
			if(orders.size()<1)
				return; //there are no orders
			
			orders.sort(new Comparator<Order>() {
			    @Override
			    public int compare(Order ord1, Order ord2) {
			    	return ord1.getIssueDate().compareTo(ord2.getIssueDate());
			    }});
			
			String ordererID = orders.get(0).getSubscriberID();
			java.util.Date date= new java.util.Date();
			long time = date.getTime();
			Response res = this.HandleInsertNewNotif(new Notif(ordererID, new Timestamp(time),"A Book you ordered is now available."));
			if(!res.isSucceeded()) {
				System.out.println("Failed to fetch data.\n"+res.getMessage());
			}
			
			orders.get(0).setSaveDate(new Date(time));
			res = HandleUpdateOrder(orders.get(0));
			if(!res.isSucceeded()) {
				System.out.println("Failed to fetch data.\n"+res.getMessage());
			}
		}
		catch(SQLException exp)
		{
			System.out.println("Failed to fetch data.\n"+exp.getMessage());
		}
	}
	
	public Response HandleBorrow(Request req) {
		String subID = ((ArrayList<String>)req.getData()).get(0);
		String isbn = ((ArrayList<String>)req.getData()).get(1);
		String serialCode = ((ArrayList<String>)req.getData()).get(2);
		
		Response res = this.HandleGetSubscriber(subID);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.Borrow);
			return res;
		}
		Subscriber sub = (Subscriber)res.getData();
		
		if(sub.getCardStatus()!=ReaderCardStatus.Active)
			return new Response(RequestType.Borrow,false, "", "Subscriber is not active. Can't Commit a borrowing.");
				
		try {
			Availability av = this.getCopyAvailability(isbn, Integer.parseInt(serialCode));
			if(av != Availability.Available)
				return new Response(RequestType.Borrow,false, "", "The Book Copy you specified is not available for borrowing.");
			
			res = this.HandleGetBook(isbn);
			if(!res.isSucceeded()) {
				res.setRequestType(RequestType.Borrow);
				return res;
			}
			Book book = (Book)res.getData();			
			
			//Calculating Dates
			java.util.Date currentDate= new java.util.Date();
			long time = currentDate.getTime();
			Timestamp now = new Timestamp(time);
			
			LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			java.util.Date dueDate_init;
			
			if(book.getDemand()==Demand.High) {
				localDateTime = localDateTime.plusDays(3);//can be borrowed for three days
				dueDate_init = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			}
			else {
				localDateTime = localDateTime.plusDays(14);//can be borrowed for two weeks = 14 days
				dueDate_init = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			}
			Date dueDate = new Date(dueDate_init.getTime());
			//Done;
			
			
			/*
			 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, String copyISBN, int copySerialCode,
			 * Date dueDate, Date returnDate, boolean extendible)
			 */
			Borrow bor = new Borrow(subID, now, "Borrow", isbn, Integer.parseInt(serialCode), dueDate, null, book.getDemand()!=Demand.High);
		
			res = this.HandleInsertNewBorrow(bor);
			if(!res.isSucceeded()) {
				res.setRequestType(RequestType.Borrow);
				return res;
			}
			
			return new Response(RequestType.Borrow, true, "", "The Book Borrowing has been successfully completed.\n"
					+ "Please be sure to return it on "+bor.getDueDate().toString());
		}
		catch(SQLException qx) {
			return new Response(RequestType.Borrow,false, "", "Failed to fetch Data.\n"+qx.getMessage());
		}
	}
	
	public Response HandleGraduationMessage(String subID) {
		Response res = this.HandleGetSubscriber(subID);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.GraduationMessage);
			return res;
		}
		
		Subscriber sub = (Subscriber)res.getData();
		sub.setGraduated(true);
		
		res = this.HandleGetBorrowsBySubscriber(subID);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.GraduationMessage);
			return res;
		}
			
		ArrayList<Borrow> borrows =(ArrayList<Borrow>)res.getData();
		boolean hasUnret=false;
		for(Borrow bo: borrows) {
			if(bo.getReturnDate()==null){
				hasUnret=true;
				break;
			}
		}
		
		if(hasUnret) //has unreturned borrowings/books
			sub.setCardStatus(ReaderCardStatus.Suspended);
		else sub.setCardStatus(ReaderCardStatus.Locked);
		
		res = this.HandleUpdateSubscriber(sub);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.GraduationMessage);
			return res;
		}
		
		return new Response(RequestType.GraduationMessage, true, "", "Graduting Subscriber has been successfully updated.");
	}
	
	public Response HandleGetPDFsOfBooks(ArrayList<String> isbns) {
		Response res;
		ArrayList<FileTransmitor> files = new ArrayList<FileTransmitor>();
		for(String isbn: isbns) {
			res=this.HandleGetBookPDF(isbn);
			if(res.isSucceeded())
				files.add((FileTransmitor)res.getData());
		}
		
		return new Response(RequestType.GetAllBooksPDF, true, files, 
				files.size()+" files succeeded.\n"+(isbns.size()-files.size())+" files failed");
	}
	
	public Response HandleGetAllBooksPDF() {
		Response res = this.HandleGetAllBooks();
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.GetAllBooksPDF);
			return res;
		}
		
		ArrayList<Book> books = (ArrayList<Book>)res.getData();
		ArrayList<String> isbns = new ArrayList<String>();
		for(Book book: books)
			isbns.add(book.getISBN());
		
		res = this.HandleGetPDFsOfBooks(isbns);
		res.setRequestType(RequestType.GetAllBooksPDF);
		return res;
	}
	
	public Response HandleGetBookPDF(String isbn) {
		Response res = this.HandleGetBook(isbn);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.GetBookPDF);
			return res;
		}
		
		String fileName = isbn+".pdf";
		FileTransmitor msg = new FileTransmitor(fileName, isbn);
		String LocalfilePath =  System.getProperty("user.dir")+"/Books/Contents/"+fileName;
		System.out.println(LocalfilePath);
		try {
			transmitFile(msg, LocalfilePath);
			return new Response(RequestType.GetBookPDF,true, msg, "Contents Table of "+isbn);
		} catch (Exception e) {
			return new Response(RequestType.GetBookPDF, false, "", "Failed to fetch contents table: "+e.getMessage());
		}
	}
	
	public Response HandleSetFile(Request req) {
		FileTransmitor file = (FileTransmitor)req.getData();
		
		Response res; String path;
		if(req.getRequestType()==RequestType.SetBookPDF) {
			path = System.getProperty("user.dir")+ "/Books/Contents/"+file.getID()+".pdf";
			res= this.HandleGetBook(file.getID());
		}
		else {
			path = System.getProperty("user.dir")+ "/Users/Pictures/"+file.getID()+".png";
			res=this.HandleGetSubscriber(file.getID());
		}
		
		if (!res.isSucceeded()) { //validating the book or user
			res.setRequestType(req.getRequestType());
			return res;
		}

		try {
			int fileSize = file.getSize();
			byte[] mybytearray = new byte[fileSize];

			File newFIle = new File(path);
			FileOutputStream fos = new FileOutputStream(newFIle);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(file.getMybytearray(), 0, file.getSize());
			bos.close();
			fos.close();
			
			return new Response(req.getRequestType(), true, "", "File has been updated");
		} catch (Exception e) {
			return new Response(req.getRequestType(), false, "", "Failed to updated file.\n"+e.getMessage());
		}
	}
	
	public Response HandleGetProfilePhoto(String subID) {
		Response res = this.HandleGetSubscriber(subID);
		if (!res.isSucceeded()) {
			res.setRequestType(RequestType.GetProfilePhoto);
			return res;
		}
		try {
			String fileName = subID + ".png";
			FileTransmitor msg = new FileTransmitor(fileName, subID);
			String LocalfilePath = "/Users/Pictures/" + fileName;

			transmitFile(msg, LocalfilePath);
			return new Response(RequestType.GetProfilePhoto, true, msg, "Profile Photo of " + subID);
		} catch (Exception e) {
			return new Response(RequestType.GetProfilePhoto, false, "",
					"Failed to fetch contents table: " + e.getMessage());
		}
	}
	
	private void transmitFile(FileTransmitor  msg, String LocalfilePath) throws Exception {
		File newFile = new File(LocalfilePath);
		byte[] mybytearray = new byte[(int) newFile.length()];
		FileInputStream fis = new FileInputStream(newFile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		msg.initArray(mybytearray.length);
		msg.setSize(mybytearray.length);

		bis.read(msg.getMybytearray(), 0, mybytearray.length);
	}
	
	public Response HandleRequest(Request req) {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} 
		catch (Exception ex) {/* handle the error */}
		
		try {
			DBCon = DriverManager.getConnection("jdbc:mysql://localhost/obl", root, passWord);
			System.out.println("SQL connection succeed");
		}
		catch(SQLException ex) {
			System.out.println("SQL connection failed.\n"+ex.getMessage());
			return new Response(req.getRequestType(), false, null, "Failed to connect to database.\n"+ex.getMessage());
		}
		
		Response toRet = null;
		
		switch (req.getRequestType()) {
		case InsertNewNotif: 
			toRet = this.HandleInsertNewNotif((Notif)req.getData());
			break;
		case GetNotifsBySubscriber:
			toRet = this.HandleGetNotifsBySubscriber((String)req.getData());
			break;
		//
		case InsertNewBook:
			toRet = this.HandleInsertNewBook((Book) req.getData());
			break;
		case UpdateBook:
			toRet = this.HandleUpdateBook((Book) req.getData());
			break;
		case DeleteBook:
			toRet = this.HandleDeleteBook((Book) req.getData());
			break;
		case IsBookOrdered:
			toRet = this.HandleIsBookOrdered((String) req.getData());
			break;
		//
		case Borrow:
			toRet = this.HandleBorrow(req);
			break;
		case InsertNewBorrow:
			toRet = this.HandleInsertNewBorrow((Borrow) req.getData());
			break;
		case UpdateBorrow:
			toRet = this.HandleUpdateBorrow((Borrow) req.getData());
			break;
		case DeleteBorrow:
			toRet = this.HandleDeleteBorrow((Borrow) req.getData());
			break;
		case ReturnBorrow:
			toRet = this.HandleReturnBorrow((Copy) req.getData());
			break;
		//
		case InsertNewCopies:
			toRet = this.HandleInsertNewCopies(req);
			break;
		case UpdateCopy:
			toRet = this.HandleUpdateCopy((Copy) req.getData());
			break;
		case DeleteCopy:
			toRet = this.HandleDeleteCopy((Copy) req.getData());
			break;
		//
		case Extend:
			toRet = this.HandleExtend((Extension) req.getData());
			break;
		case InsertNewExtension:
			toRet = this.HandleInsertNewExtension((Extension) req.getData());
			break;
		case DeleteExtension:
			toRet = this.HandleDeleteExtension((Extension) req.getData());
			break;
		//
		case InsertNewLosing:
			toRet = this.HandleInsertNewLosing((Losing) req.getData());
			break;
		case DeleteLosing:
			toRet = this.HandleDeleteLosing((Losing) req.getData());
			break;
		//
		case InsertNewOrder:
			toRet = this.HandleInsertNewOrder((Order) req.getData());
			break;
		case UpdateOrder:
			toRet = this.HandleUpdateOrder((Order) req.getData());
			break;
		case DeleteOrder:
			toRet = this.HandleDeleteOrder((Order) req.getData());
			break;
		//
		case InsertNewReport:
			toRet = this.HandleInsertNewReport((Report) req.getData());
			break;
		case DeleteReport:
			toRet = this.HandleDeleteReport((Report) req.getData());
			break;
		//
		case InsertNewSubscriber:
			toRet = this.HandleInsertNewSubscriber((Subscriber) req.getData());
			break;
		case UpdateSubscriber:
			toRet = this.HandleUpdateSubscriber((Subscriber) req.getData());
			break;
		case DeleteSubscriber:
			toRet = this.HandleDeleteSubscriber((Subscriber) req.getData());
			break;
		//
		case GetActivitiesBySubscriber:
			toRet = this.HandleGetActivitiesBySubscriber((String) req.getData());
			break;
		case GetBook:
			toRet = this.HandleGetBook((String) req.getData());
			break;
		case GetBookAvailability:
			toRet = this.HandleGetBookAvailability((String) req.getData());
			break;
		case GetAllBooks:
			toRet = this.HandleGetAllBooks();
			break;
		case GetPDFsOfBooks:
			toRet = this.HandleGetPDFsOfBooks((ArrayList<String>)req.getData());
			break;
		case GetAllBooksPDF:
			toRet = this.HandleGetAllBooksPDF();
			break;
		case GetBookPDF:
			toRet = this.HandleGetBookPDF((String) req.getData());
			break;
		case SetBookPDF:
			toRet = this.HandleSetFile(req);
			break;
		//
		case GetLibrarian:
			toRet = this.HandleGetLibrarian((String) req.getData());
			break;
		case GetAllLibrarians:
			toRet = this.HandleGetAllLibrarians();
			break;
		//
		//case GetAllReports:
		//	break;
		//
		case IssueActivity:
			toRet = this.HandleIssueActivity();
			break;
		case IssueBorrowing:
			toRet = this.HandleIssueBorrowing(req);
			break;
		case IssueDelayings:
			toRet = this.HandleIssueDelayings(req);
			break;
		case GetSubscriber:
			toRet = this.HandleGetSubscriber((String) req.getData());
			break;
		case GetAllSubscribers:
			toRet = this.HandleGetAllSubscribers();
			break;
		case GetProfilePhoto:
			toRet = this.HandleGetProfilePhoto((String) req.getData());
			break;
		case SetProfilePhoto:
			toRet = this.HandleSetFile(req);
			break;
		//
		case GetUser:
			toRet = this.HandleGetUser((String) req.getData());
			break;
		case GetAllUsers:
			toRet = this.HandleGetAllUsers();
			break;
		//
		case GetCopiesByBook:
			toRet = this.HandleGetCopiesByBook((String) req.getData());
			break;
		case GetCopy:
			toRet = this.HandleGetCopy(req);
			break;
		case GetBorrowsBySubscriber:
			toRet = this.HandleGetBorrowsBySubscriber((String) req.getData());
			break;
		//case GetNextOrderByBook:// toRet= this.HandleGetNextOrderByBook((String)req.getData()));
			//break;
		case GetOrdersBySubscriber:
			toRet = this.HandleGetOrdersBySubscriber((String) req.getData());
			break;
		case CanOrder:
			toRet = this.HandleCanOrder((String) req.getData());
			break;
		//case GetReport:
		//	break;
		//
		case GraduationMessage:
			toRet = this.HandleGraduationMessage((String) req.getData());
			break;
		//
		case LogIn:
			toRet = this.HandleLogIn(req);
			break;
		case LogOut:
			toRet = this.HandleLogOut((String) req.getData());
			break;
		//
		case SearchByAuthors:
		case SearchByDepartment:
		case SearchByCategory:
		case SearchByDemand:
		case SearchByDescription:
		case SearchByEdition:
		case SearchByPublication:
		case SearchByPurchaseDate:
		case SearchByTitle:
			toRet = this.HandleSearch(req);
			break;
		default:
			toRet = null;
			System.out.println("@DBM-HandleRequest: Default Case");
			break;
		}
		
		try { DBCon.close();
		}catch(SQLException ex) {
			System.out.println("SQL close connection failed"+ex.getMessage());
		}
		System.out.println("toRet " + toRet.getData());
		return toRet;
	}

	private Response HandleIssueDelayings(Request req) {
		long fromDate = ((ArrayList<Date>)req.getData()).get(0).getTime();
		long toDate = ((ArrayList<Date>)req.getData()).get(1).getTime();
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE (ReturnDate>DueDate)");
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Borrow> borrows = new ArrayList<Borrow>();
			while(rs.next()) {
				/*
				 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, 
				 * String copyISBN, int copySerialCode, Date dueDate, Date returnDate, boolean extendible)
				 */
				borrows.add(new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8))));
			}
			if(borrows.size()==0)
				return new Response(RequestType.IssueDelayings, false, null, "No Sufficient Data.");
			
			ArrayList<Long> latePeriods = new ArrayList<Long>();
			for (Borrow bor : borrows) {
				if (bor.getIssueDate().getTime() >= fromDate && bor.getReturnDate().getTime() <= toDate) {
					// in range
					long dueTime = bor.getDueDate().getTime();
					long returnTime = bor.getReturnDate().getTime();
					long diffDays = (returnTime - dueTime) / (1000 * 60 * 60 * 24);
					latePeriods.add(new Long(diffDays));
				}
			}

			latePeriods.sort(new Comparator<Long>() {
				@Override
				public int compare(Long ln1, Long ln2) {
					return ln1.compareTo(ln2);
				}
			});
			
			long day = (1000 * 60 * 60 * 24);
			long dec =  toDate - fromDate; //interval in time
			dec /= day; //interval in days
			dec = dec/10 + dec%10; //decimal distr.
			
			double Dest[] = new double[(int)dec];
			Arrays.fill(Dest, 0);
			
			double avg=0;
			long median=0;
			String text;
			
			if (latePeriods.size() > 0) {
				avg = SumLongs(latePeriods) / latePeriods.size();
				median = latePeriods.get(latePeriods.size() / 2);
			}
			else text="No Sufficient Data.";
			
			for (Borrow bor : borrows) {
				if (bor.getIssueDate().getTime() >= fromDate && bor.getReturnDate().getTime() <= toDate) {
					int index = (int) ((bor.getIssueDate().getTime() - fromDate) / day);// how many days since from.
					index = index % ((int) dec); // in which decimal distr
					Dest[index]++;
				}
			}
			
			for(int i=0;i<dec;i++) {
				Dest[i] = Dest[i]/latePeriods.size();
			}
			
			text="Delays in Borrowings:\n"
				+"Number of Delays: "+latePeriods.size()+".\n"
				+"Median delayement in days: "+median+".\n"
				+"Average delayement in days: "+avg+".\n\n"
				+"Decimal Distribution(each index represents "+dec+" days):\n"
				+PrintArray(Dest);
			
			Report rep = new Report("Borrowings Report of Period[" + ((ArrayList<Date>) req.getData()).get(0).toString()
					+ ", " + ((ArrayList<Date>) req.getData()).get(1).toString() + "]:\n\n" + text, null);
			return new Response(RequestType.IssueDelayings, true, rep, "");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.IssueDelayings, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}

	private Response HandleIssueBorrowing(Request req) {
		long fromDate = ((ArrayList<Date>)req.getData()).get(0).getTime();
		long toDate = ((ArrayList<Date>)req.getData()).get(1).getTime();
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE (ReturnDate IS NOT NULL)");
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Borrow> borrows = new ArrayList<Borrow>();
			while(rs.next()) {
				/*
				 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, 
				 * String copyISBN, int copySerialCode, Date dueDate, Date returnDate, boolean extendible)
				 */
				borrows.add(new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8))));
			}
			if(borrows.size()==0)
				return new Response(RequestType.IssueBorrowing, false, null, "No Sufficient Data.");
			
			ArrayList<Long> highDems = new ArrayList<Long>();
			ArrayList<Long> regDems = new ArrayList<Long>();
			for (Borrow bor : borrows) {
				if (bor.getIssueDate().getTime() >= fromDate && bor.getReturnDate().getTime() <= toDate) {
					//in range
					long issueTime = bor.getIssueDate().getTime();
					long returnTime = bor.getReturnDate().getTime();
					long diffDays = (returnTime - issueTime) / (1000 * 60 * 60 * 24);

					Response res = this.HandleGetBook(bor.getCopyISBN());
					if (!res.isSucceeded()) {
						res.setRequestType(RequestType.IssueBorrowing);
						return res;
					}
					Book book = (Book) res.getData();
					if (book.getDemand() == Demand.High)
						highDems.add(new Long(diffDays));
					else if (book.getDemand() == Demand.Regular)
						regDems.add(new Long(diffDays));
				}
			}
			
			highDems.sort(new Comparator<Long>() {
			    @Override
			    public int compare(Long ln1, Long ln2) {
			    	return ln1.compareTo(ln2);
			    }});
			
			regDems.sort(new Comparator<Long>() {
			    @Override
			    public int compare(Long ln1, Long ln2) {
			    	return ln1.compareTo(ln2);
			    }});
			

			long day = (1000 * 60 * 60 * 24);
			long dec = toDate - fromDate; //interval in time
			dec /= day; //interval in days
			dec = dec/10 + dec%10; //decimal distr.
			
			double highDest[] = new double[(int)dec];
			double regDest[] = new double[(int)dec];
			Arrays.fill(highDest, 0);
			Arrays.fill(regDest, 0);
			
			double highAvg=0, regAvg=0;
			long highMed=0, regMed=0;
			String textHigh, textReg;
			
			if (highDems.size() > 0) {
				highAvg = SumLongs(highDems) / highDems.size();
				highMed = highDems.get(highDems.size() / 2);
			}
			else textHigh="No Sufficient Data.";
			
			if (regDems.size() > 0) {
				regAvg = SumLongs(regDems) / regDems.size();
				regMed = regDems.get(regDems.size() / 2);
			}
			else textReg="No Sufficient Data.";
			
			for (Borrow bor : borrows) {
				if (bor.getIssueDate().getTime() >= fromDate && bor.getReturnDate().getTime() <= toDate) {
					int index = (int) ((bor.getIssueDate().getTime() - fromDate) / day);// how many days since from.
					index = index % ((int) dec); // in which decimal distr.

					Response res = this.HandleGetBook(bor.getCopyISBN());
					if (!res.isSucceeded()) {
						res.setRequestType(RequestType.IssueBorrowing);
						return res;
					}
					
					Book book = (Book) res.getData();
					if (book.getDemand() == Demand.High)
						highDest[index]++;
					else if (book.getDemand() == Demand.Regular)
						regDest[index]++;
				}
			}
			
			for(int i=0;i<dec;i++) {
				highDest[i] = highDest[i]/highDems.size();
				regDest[i] = regDest[i]/regDems.size();
			}
			
			textHigh= "Borrowings of Book with High Demand:\n"
					+"Number of Borrowings    : "+highDems.size()+".\n"
					+"Median Borrowing Period : "+highMed+".\n"
					+"Average Borrowing Period: "+highAvg+".\n\n"
					+"Decimal Distribution(each index represents "+dec+" days):\n"
					+PrintArray(highDest);
			
			textReg= "Borrowings of Book with Regular Demand:\n"
					+"Number of Borrowings    : "+regDems.size()+".\n"
					+"Median Borrowing Period : "+regMed+".\n"
					+"Average Borrowing Period: "+regAvg+".\n\n"
					+"Decimal Distribution(each index represents "+dec+" days):\n"
					+PrintArray(regDest);

			Report rep = new Report("Borrowings Report of Period["+((ArrayList<Date>)req.getData()).get(0).toString()+", "+
					((ArrayList<Date>)req.getData()).get(1).toString()+"]:\n\n"+textHigh+"\n\n"+textReg, null);
			
			return new Response(RequestType.IssueBorrowing, true, rep, "");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.IssueBorrowing, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}

	private String PrintArray(double[] array) {
		String str="[";
		for(int i =0;i<array.length-1;i++)
			str+=array[i]+", ";
		return str+array[array.length-1]+"]";
	}

	private double SumLongs(ArrayList<Long> longs) {
		double sum = 0;
		for(Long ln: longs)
			sum+=ln.doubleValue();
		return sum;
	}

	private Response HandleIssueActivity() {
		Response res = this.HandleGetAllSubscribers();
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.IssueActivity);
			return res;
		}
		ArrayList<Subscriber> subs = (ArrayList<Subscriber>)res.getData();
		
		res = this.HandleGetAllCopies();
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.IssueActivity);
			return res;
		}
		ArrayList<Copy> copies = (ArrayList<Copy>)res.getData();
		
		int lates=0;
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT COUNT(DISTINCT BorrowerID) FROM borrows WHERE ReturnDate>DueDate");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				lates = rs.getInt(1);
			}
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.IssueActivity, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
		
		int actives=0, suspends=0, locks=0;
		for(Subscriber sub: subs){
			if(sub.getCardStatus()==ReaderCardStatus.Active)
				actives++;
			else if (sub.getCardStatus()==ReaderCardStatus.Suspended)
				suspends++;
			else locks++;
		}

		double actsAvg = actives/((double)subs.size());
		double suspAvg = suspends/((double)subs.size());
		double lockAvg = locks/((double)subs.size());
		double latesAvg = lates/((double)subs.size());
		
		String text = "Number of Active Reader Cards:    "+actives+" "+actsAvg+"%\n" 
					+ "Number of Suspended Reader Cards: "+suspends+" "+suspAvg+"%\n" 
					+ "Number of Locked Reader Cards:    "+locks+" "+lockAvg+"%\n" 
					+ "Total number of Reader Cards:     "+subs.size()+" 100%\n\n"
					+ "Total number of copies: "+copies.size()+"\n"
					+"There are "+lates+" Late Borrowers, "+latesAvg+"% of Subscribers.\n";
		
		java.util.Date currentDate= new java.util.Date();
		long time = currentDate.getTime();
		Timestamp now = new Timestamp(time);
		Report rep = new Report(text, now);
		
		res = this.HandleInsertNewReport(rep);
		if(!res.isSucceeded()){
			res.setRequestType(RequestType.IssueActivity);
			return res;
		}
		
		return new Response(RequestType.IssueActivity, true, rep, "");
	}

	private Response HandleExtend(Extension ext) {
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE BorrowerID=? AND IssueDate=?");
			pstmt.setString(1, ext.getSubscriberID());
			pstmt.setTimestamp(2, ext.getBorrowingDate());
			ResultSet rs = pstmt.executeQuery();
			
			Borrow borrow;
			if(rs.next()) {
				/*
				 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, 
				 * String copyISBN, int copySerialCode, Date dueDate, Date returnDate, boolean extendible)
				 */
				borrow = new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8)));
			}
			else return new Response(RequestType.Extend, false, "", "Invalid Borrowing data.");
			
			Response res = this.HandleGetBook(borrow.getCopyISBN());
			if(!res.isSucceeded()) {
				res.setRequestType(RequestType.Extend);
				return res;
			}
			
			Book book = (Book) res.getData();
			if (book.getAvailableCopiesNumber() > 0) 
			{
				res = this.HandleInsertNewExtension(ext);
				if (!res.isSucceeded()) {
					res.setRequestType(RequestType.Extend);
					return res;
				}
				
				//Calculating Dates
				java.util.Date tempDate = new java.util.Date(borrow.getDueDate().getTime());
				LocalDateTime localDateTime = tempDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				localDateTime = localDateTime.plusDays(14); //extend one week
				tempDate = Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
				Date newDueDate = new Date(tempDate.getTime());
				
				pstmt = DBCon.prepareStatement("Update Borrows SET DueDate=? WHERE BorrowerID=? AND IssueDate=?");
				pstmt.setDate(1, newDueDate);
				pstmt.setString(2, ext.getSubscriberID());
				pstmt.setTimestamp(3, ext.getBorrowingDate());
					
				pstmt.executeUpdate();
				return new Response(RequestType.Extend, true, null, "Borrow has been successfully extended. Due Date is "+ newDueDate.toString());

			}
			return new Response(RequestType.Extend, false, "", "Can't Extend Borrowing of this book because it has an awaiting order.");
		}
		catch(SQLException exp)
		{
			this.HandleDeleteExtension(ext);
			return new Response(RequestType.Extend, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}

	private Response HandleCanOrder(String isbn) {
		Response res = this.HandleGetBook(isbn);
		if(!res.isSucceeded()) {
			res.setRequestType(RequestType.CanOrder);
			return res;
		}
		int copies = ((Book)res.getData()).getTotalCopiesNumber();
		int aval = ((Book)res.getData()).getAvailableCopiesNumber();
		if(aval>0)
			return new Response(RequestType.CanOrder, false, "", "Book<"+isbn+"> is currently available."); 
		
		try
		{
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT COUNT(*) FROM orders WHERE OrderedBook=?");
			pstmt.setString(1, isbn);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt(1)<copies)
					return new Response(RequestType.CanOrder, true, "", "book<"+isbn+"> can be ordered.");
			}
			return new Response(RequestType.CanOrder, false, "", "book<"+isbn+"> cannot be ordered.");
		}
		catch(SQLException exp)
		{
			return new Response(RequestType.CanOrder, false, null, "Failed to fetch data.\n"+exp.getMessage());
		}
	}

	private Response HandleGetBookAvailability(String isbn) {
		try {
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT DueDate FROM borrows WHERE CopyISBN=? AND ReturnDate IS NULL");
			pstmt.setString(1, isbn);
			ResultSet rs = pstmt.executeQuery();

			ArrayList<Date> dueDates = new ArrayList<Date>();
			while (rs.next()) {
				/*
				 * public Order(String subscriberID, Timestamp issueDate, String ISBN, Date
				 * saveDate, String activityName, boolean realised)
				 */
				dueDates.add(rs.getDate("DueDate"));
			}
			
			dueDates.sort(new Comparator<Date>() {
			    @Override
			    public int compare(Date d1, Date d2) {
			    	return d1.compareTo(d2);
			    }});
			
			if(dueDates.size()>0) {
			return new Response(RequestType.GetBookAvailability, true, "",
					"Will be Avaialable on "+dueDates.get(0).toString());
			}
			else return new Response(RequestType.GetBookAvailability, true, "",
					"Available");
		} catch (SQLException exp) {
			return new Response(RequestType.GetBookAvailability, false, null,
					"Failed to fetch data.\n" + exp.getMessage());
		}
	}

	public void LogOutAll() {
		System.out.println("Logging out all...");
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} 
		catch (Exception ex) {/* handle the error */}
		
		try {
			DBCon = DriverManager.getConnection("jdbc:mysql://localhost/obl", root, passWord);
			System.out.println("SQL connection succeed");
			
			PreparedStatement pstmt = DBCon.prepareStatement("Update users SET OnlineStatus=? WHERE OnlineStatus=?");
			pstmt.setString(1, Boolean.toString(false));
			pstmt.setString(2, Boolean.toString(true));
			pstmt.executeUpdate();
			System.out.println("All Logged out.");
		}
		catch(SQLException ex) {
			System.out.println("SQL failure.\n"+ex.getMessage());
		}
		
		try { DBCon.close();
		}catch(SQLException ex) {
			System.out.println("SQL close connection failed"+ex.getMessage());
		}
	}

	public void NotifyRetunrs() {
		try
		{
			//Calculating Date
			java.util.Date currentDate= new java.util.Date();			
			LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			localDateTime = localDateTime.plusDays(1);//next day
			java.util.Date dueDate_i = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Date dueDate = new Date(dueDate_i.getTime());
			//Done;
			
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM borrows WHERE DueDate=?");
			pstmt.setDate(1, dueDate);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Borrow> borrows = new ArrayList<Borrow>();
			while(rs.next()) {
				/*
				 * public Borrow(String subscriberID, Timestamp issueDate, String activityName, 
				 * String copyISBN, int copySerialCode, Date dueDate, Date returnDate, boolean extendible)
				 */
				borrows.add(new Borrow(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getDate(6), rs.getDate(7), Boolean.valueOf(rs.getString(8))));
			}
			
			Timestamp now = new Timestamp(currentDate.getTime());
			for(Borrow bor: borrows) {
				Notif not = new Notif(bor.getSubscriberID(), now, "Tomorrow is the duedate of one of your borrowings: Book<"+bor.getCopyISBN()+", "
				+bor.getCopySerialCode()+">");
				this.HandleInsertNewNotif(not);
				this.SendEmail(bor.getSubscriberID());
			}
		}	
		catch(SQLException exp)
		{
			System.out.println("Failed in Notify Retunrs: "+exp.getMessage());
		}
	}

	/*
	private void SendEmail(String subscriberID) {
		final String username = "braude.sa@gmail.com";
		final String password = "amaramer164";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("braude.sa@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("amar164@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!\n\n this message from eclipse !!!!!!");
			 
			Transport.send(message);

			System.out.println("Done");
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}*/

	private void SendEmail(String subscriberID) {
	}

	public void DeleteOrders() {
		try
		{
			//Calculating Date
			java.util.Date currentDate= new java.util.Date();			
			LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			localDateTime = localDateTime.minusDays(-2);//two days before
			java.util.Date dueDate_i = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Date dueDate = new Date(dueDate_i.getTime());
			//Done;
			
			PreparedStatement pstmt = DBCon.prepareStatement("SELECT * FROM orders WHERE SaveDate=? AND Realised=?");
			pstmt.setDate(1, dueDate);
			pstmt.setString(1, Boolean.toString(false));
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Order> orders = new ArrayList<Order>();
			while(rs.next()) {
				/*
				 * public Order(String subscriberID, Timestamp issueDate, String ISBN, 
				 * Date saveDate, String activityName, boolean realised)
				 */
				orders.add(new Order(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
						rs.getDate(4), rs.getString(5), Boolean.valueOf(rs.getString(6))));
			}
			
			for(Order ord: orders) {
				this.HandleDeleteOrder(ord);
			}
		}	
		catch(SQLException exp)
		{
			System.out.println("Failed in Notify Retunrs: "+exp.getMessage());
		}
	}
}