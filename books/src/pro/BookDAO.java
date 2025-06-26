package pro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

	// 添加书籍到数据库
	public static int addBook(Book book) {
		// SQL 插入语句，使用占位符 ? 来防止 SQL 注入
		String sql = "INSERT INTO books(title, author, isbn, publisher, publish_date, category, quantity) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?)";
		// 调用 DBUtil 的 executeUpdate 方法执行 SQL 语句，并传入书籍的相关信息
		return DBUtil.executeUpdate(sql, book.getTitle(), book.getAuthor(), book.getIsbn(),
				book.getPublisher(), book.getPublishDate(),
				book.getCategory(), book.getQuantity());
	}

	// 获取所有书籍信息
	public static List<Book> getAllBooks() {
		// SQL 查询语句，查询所有书籍
		String sql = "SELECT * FROM books";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询
		ResultSet rs = DBUtil.executeQuery(sql);
		// 创建一个 List 用于存储所有书籍对象
		List<Book> bookList = new ArrayList<>();

		try {
			// 遍历 ResultSet，将每一行数据映射到 Book 对象中，并添加到 List 中
			while (rs != null && rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id")); // 设置书籍ID
				book.setTitle(rs.getString("title")); // 设置书籍标题
				book.setAuthor(rs.getString("author")); // 设置书籍作者
				book.setIsbn(rs.getString("isbn")); // 设置书籍ISBN
				book.setPublisher(rs.getString("publisher")); // 设置书籍出版社
				book.setPublishDate(rs.getString("publish_date")); // 设置书籍出版日期
				book.setCategory(rs.getString("category")); // 设置书籍类别
				book.setQuantity(rs.getInt("quantity")); // 设置书籍数量
				bookList.add(book); // 将书籍对象添加到 List 中
			}
		} catch (SQLException e) {
			// 如果查询过程中发生异常，打印异常堆栈信息
			e.printStackTrace();
		} finally {
			// 无论是否发生异常，都要关闭 ResultSet 以释放资源
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// 如果关闭 ResultSet 时发生异常，打印异常堆栈信息
				e.printStackTrace();
			}
		}

		// 返回包含所有书籍对象的 List
		return bookList;
	}

	// 删除书籍
	public static int deleteBook(int id) {
	    // 先删除与该书相关的借阅记录
	    String deleteBorrowRecordsSql = "DELETE FROM borrow_records WHERE book_id = ?";
	    DBUtil.executeUpdate(deleteBorrowRecordsSql, id);

	    // 再删除图书
	    String sql = "DELETE FROM books WHERE id = ?";
	    return DBUtil.executeUpdate(sql, id);
	}

	// 更新书籍信息
	public static int updateBook(Book book) {
		// SQL 更新语句，使用占位符 ? 来防止 SQL 注入
		String sql = "UPDATE books SET title=?, author=?, isbn=?, publisher=?, " +
				"publish_date=?, category=?, quantity=? WHERE id=?";
		// 调用 DBUtil 的 executeUpdate 方法执行 SQL 更新语句，并传入书籍的相关信息
		return DBUtil.executeUpdate(sql, book.getTitle(), book.getAuthor(), book.getIsbn(),
				book.getPublisher(), book.getPublishDate(),
				book.getCategory(), book.getQuantity(), book.getId());
	}

	// 根据书籍ID查询书籍信息
	public static Book getBookById(int id) {
		// SQL 查询语句，使用占位符 ? 来防止 SQL 注入
		String sql = "SELECT * FROM books WHERE id = ?";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入书籍ID作为参数
		ResultSet rs = DBUtil.executeQuery(sql, id);
		// 创建一个 Book 对象用于存储查询结果
		Book book = null;

		try {
			// 如果 ResultSet 不为空且存在下一行数据，则将查询结果映射到 Book 对象中
			if (rs != null && rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id")); // 设置书籍ID
				book.setTitle(rs.getString("title")); // 设置书籍标题
				book.setAuthor(rs.getString("author")); // 设置书籍作者
				book.setIsbn(rs.getString("isbn")); // 设置书籍ISBN
				book.setPublisher(rs.getString("publisher")); // 设置书籍出版社
				book.setPublishDate(rs.getString("publish_date")); // 设置书籍出版日期
				book.setCategory(rs.getString("category")); // 设置书籍类别
				book.setQuantity(rs.getInt("quantity")); // 设置书籍数量
			}
		} catch (SQLException e) {
			// 如果查询过程中发生异常，打印异常堆栈信息
			e.printStackTrace();
		} finally {
			// 无论是否发生异常，都要关闭 ResultSet 以释放资源
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// 如果关闭 ResultSet 时发生异常，打印异常堆栈信息
				e.printStackTrace();
			}
		}

		// 返回查询到的书籍对象，如果没有找到则返回 null
		return book;
	}

	// 根据关键词搜索书籍
	public static List<Book> searchBooks(String keyword) {
		// SQL 查询语句，使用 LIKE 操作符进行模糊查询，使用占位符 ? 来防止 SQL 注入
		String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入关键词作为参数
		ResultSet rs = DBUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
		// 创建一个 List 用于存储搜索到的书籍对象
		List<Book> bookList = new ArrayList<>();

		try {
			// 遍历 ResultSet，将每一行数据映射到 Book 对象中，并添加到 List 中
			while (rs != null && rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id")); // 设置书籍ID
				book.setTitle(rs.getString("title")); // 设置书籍标题
				book.setAuthor(rs.getString("author")); // 设置书籍作者
				book.setIsbn(rs.getString("isbn")); // 设置书籍ISBN
				book.setPublisher(rs.getString("publisher")); // 设置书籍出版社
				book.setPublishDate(rs.getString("publish_date")); // 设置书籍出版日期
				book.setCategory(rs.getString("category")); // 设置书籍类别
				book.setQuantity(rs.getInt("quantity")); // 设置书籍数量
				bookList.add(book); // 将书籍对象添加到 List 中
			}
		} catch (SQLException e) {
			// 如果查询过程中发生异常，打印异常堆栈信息
			e.printStackTrace();
		} finally {
			// 无论是否发生异常，都要关闭 ResultSet 以释放资源
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// 如果关闭 ResultSet 时发生异常，打印异常堆栈信息
				e.printStackTrace();
			}
		}

		// 返回包含搜索到的书籍对象的 List
		return bookList;
	}
}