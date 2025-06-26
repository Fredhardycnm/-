package pro;

public class BorrowRecord {
	private int id;
	private int userId;
	private int bookId;
	private String bookTitle;
	private String borrowDate;
	private String returnDate;
	private String status;

	// Getters and Setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }
	public int getBookId() { return bookId; }
	public void setBookId(int bookId) { this.bookId = bookId; }
	public String getBookTitle() { return bookTitle; }
	public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
	public String getBorrowDate() { return borrowDate; }
	public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }
	public String getReturnDate() { return returnDate; }
	public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	@Override
	public String toString() {
		return "BorrowRecord [id=" + id + ", bookId=" + bookId + ", bookTitle=" + bookTitle +
				", borrowDate=" + borrowDate + ", returnDate=" + returnDate + ", status=" + status + "]";
	}
}