package Methods;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Button;

import java.sql.Date;


public class Book implements Serializable, Comparable{
	public enum Demand {
		Regular,Low,High
	}
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	private String ISBN, authors, category, Catalogue, Shelf, Description, Department;
	private int edition;
	private Date PublicationDate, PurchaseDate;
	private Demand Demand;
	private int TotalCopiesNumber, AvailableCopiesNumber;
	//private File ContentsTable;
	private boolean Deleted;
	//private ArrayList<Copy> Copy;
	
	
	public Book(String isbn, String tilte, String authors, int edition, Date publicationDate, Date purchaseDate, 
			String category, String catalogue, String Department, int totalCopiesNumber, int availableCopiesNumber, String shelf,
			Methods.Book.Demand demand, String description, boolean deleted)
	{
		this.title=tilte;
		ISBN = isbn;
		this.edition = edition;
		Catalogue = catalogue;
		Shelf = shelf;
		Description = description;
		this.authors = authors;
		this.category = category;
		PublicationDate = publicationDate;
		PurchaseDate = purchaseDate;
		Demand = demand;
		TotalCopiesNumber = totalCopiesNumber;
		AvailableCopiesNumber = availableCopiesNumber;
		//ContentsTable = contentsTable;
		Deleted = deleted;
		this.Department=Department;
		/*for(int i=1; i<= TotalCopiesNumber; i++)
			Copy.add(new Copy( ISBN, Methods.Copy.Availability.Available));*/
		
	}
	

	

	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdittionNumber(int edition) {
		this.edition = edition;
	}
	public String getCatalogue() {
		return Catalogue;
	}
	public void setCatalogue(String catalogue) {
		Catalogue = catalogue;
	}
	public String getShelf() {
		return Shelf;
	}
	public void setShelf(String shelf) {
		Shelf = shelf;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getCategory() {
		return category;
	}
	public void setTopic(String category) {
		this.category = category;
	}
	public Date getPublicationDate() {
		return PublicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		PublicationDate = publicationDate;
	}
	public Date getPurchaseDate() {
		return PurchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		PurchaseDate = purchaseDate;
	}
	public Demand getDemand() {
		return Demand;
	}
	public void setDemand(Demand demand) {
		Demand = demand;
	}
	public int getTotalCopiesNumber() {
		return TotalCopiesNumber;
	}
	public void setTotalCopiesNumber(int totalCopiesNumber) {
		TotalCopiesNumber = totalCopiesNumber;
	}
	public int getAvailableCopiesNumber() {
		return AvailableCopiesNumber;
	}
	public void setAvailableCopiesNumber(int availableCopiesNumber) {
		AvailableCopiesNumber = availableCopiesNumber;
	}
	
	/*public File getContentsTable() {
		return ContentsTable;
	}
	public void setContentsTable(File contentsTable) {
		ContentsTable = contentsTable;
	}*/
	
	public boolean isDeleted() {
		return Deleted;
	}
	public void setDeleted(boolean deleted) {
		Deleted = deleted;
	}
	
	/*public ArrayList<Copy> getCopy() {
		return Copy;
	}
	public void setCopy(ArrayList<Copy> copy) {
		Copy = copy;
	}*/
	
	@Override
	public String toString() {
		return "Book [ISBN=" + ISBN + ", tilte=" + title + ", authors=" + authors + ", category=" + category
				+ ", Catalogue=" + Catalogue + ", Shelf=" + Shelf + ", Description=" + Description + ", edition="
				+ edition + ", PublicationDate=" + PublicationDate + ", PurchaseDate=" + PurchaseDate + ", Demand="
				+ Demand + ", TotalCopiesNumber=" + TotalCopiesNumber + ", AvailableCopiesNumber="
				+ AvailableCopiesNumber + ", Deleted=" + Deleted + "]";
	}
	@Override
	public int compareTo(Object comTo) {
		if(!(comTo instanceof Book))
			throw new IllegalArgumentException("Book.compareTo: Cannot compare a book to another class.");
		return this.title.compareTo(((Book)comTo).getTitle());
	}
}
