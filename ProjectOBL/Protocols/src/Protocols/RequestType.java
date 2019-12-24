package Protocols;

public enum RequestType {
	InsertNewBook, UpdateBook, DeleteBook,
	InsertNewCopies, UpdateCopy, DeleteCopy,
	InsertNewOrder, UpdateOrder, DeleteOrder, CanOrder,
	InsertNewBorrow, UpdateBorrow, DeleteBorrow, ReturnBorrow, Borrow,
	InsertNewExtension, /*UpdateExtension,*/ DeleteExtension, Extend,
	//InsertNewManualExtension, UpdateManualExtension, DeleteManualExtension,
	InsertNewLosing, /*UpdateLosing,*/ DeleteLosing,
	InsertNewSubscriber, UpdateSubscriber, DeleteSubscriber,
	InsertNewReport, /*UpdateReport,*/ DeleteReport,
	/*InsertNewLibrarian, UpdateLibrarian, DeleteLibrarian,*/
	InsertNewNotif, DeleteNotif,
	
	GetUser, GetAllUsers, GetLibrarian, GetAllLibrarians,
	GetSubscriber, GetAllSubscribers, GetProfilePhoto, SetProfilePhoto,
	GetBook, GetAllBooks, GetAllBooksPDF, GetPDFsOfBooks,
	GetBookPDF, SetBookPDF,
	/*GetDeletedBooks,*/ GetCopy, /*GetAllCopies,*/ GetCopiesByBook, GetAllCopies,
	GetReport, GetAllReports, /*GetBorrow, GetAllBorrows,*/ GetBorrowsBySubscriber, 
	/*GetOrder, GetAllOrders,*/ GetOrdersBySubscriber, /*GetOrdersByBook,*/ GetNextOrderByBook, IsBookOrdered,
	/*GetExtension,*/ /*GetAllExtensions,*/ GetActivitiesBySubscriber, GetNotifsBySubscriber,
	
	LogIn, LogOut,
	
	GraduationMessage,
	
	SearchByTitle, SearchByAuthors, SearchByEdition, SearchByPurchaseDate, SearchByPublication,
	SearchByCategory, SearchByDepartment, SearchByDemand, SearchByDescription, GetBookAvailability,
	
	IssueActivity, IssueBorrowing, IssueDelayings
}