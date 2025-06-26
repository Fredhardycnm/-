package pro;

public class Book {
	private int id;
	private String title;
	private String author;
	private String isbn;
	private String publisher;
	private String publishDate;
	private String category;
	private int quantity;

    public Book(String title, String author, String isbn, String category, String publisher, int quantity) {
		this.title=title;
		this.author=author;
		this.isbn=isbn;
		this.publisher=publisher;
        this.category=category;
		this. quantity=quantity;
    }

	public Book() {


	}

	// Getters and Setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public String getIsbn() { return isbn; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
	public String getPublisher() { return publisher; }
	public void setPublisher(String publisher) { this.publisher = publisher; }
	public String getPublishDate() { return publishDate; }
	public void setPublishDate(String publishDate) { this.publishDate = publishDate; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	@Override
	public String toString() {return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", publisher=" + publisher + ", publishDate=" + publishDate + ", category=" + category + ", quantity=" + quantity + "]";}}