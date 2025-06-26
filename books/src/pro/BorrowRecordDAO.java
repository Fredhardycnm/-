package pro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAO {

	// 借阅图书
	public static int borrowBook(int userId, int bookId) {
		// 检查图书是否可借
		Book book = BookDAO.getBookById(bookId);
		if (book == null || book.getQuantity() <= 0) {
			return -1; // 图书不存在或库存不足
		}

		// 减少图书库存
		book.setQuantity(book.getQuantity() - 1);
		int updateResult = BookDAO.updateBook(book);
		if (updateResult <= 0) {
			return -1; // 更新库存失败
		}

		// 创建借阅记录
		String sql = "INSERT INTO borrow_records(user_id, book_id, borrow_date, status) " +
				"VALUES(?, ?, CURDATE(), 'borrowed')";
		// 调用 DBUtil 的 executeUpdate 方法执行 SQL 语句，并传入用户ID和图书ID作为参数
		return DBUtil.executeUpdate(sql, userId, bookId);
	}

	// 归还图书
	public static int returnBook(int recordId) {
		// 获取借阅记录
		String sql = "SELECT * FROM borrow_records WHERE id = ?";
		ResultSet rs = DBUtil.executeQuery(sql, recordId);

		try {
			if (rs != null && rs.next()) {
				int bookId = rs.getInt("book_id");

				// 更新借阅记录
				sql = "UPDATE borrow_records SET return_date = CURDATE(), status = 'returned' WHERE id = ?";
				int result = DBUtil.executeUpdate(sql, recordId);

				if (result > 0) {
					// 重新获取书籍信息
					Book book = BookDAO.getBookById(bookId);
					if (book != null) {
						book.setQuantity(book.getQuantity() + 1);
						BookDAO.updateBook(book);
					}
				}

				return result;
			}
		} catch (SQLException e) {
			// 如果查询或更新过程中发生异常，打印异常堆栈信息
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

		return -1; // 返回 -1 表示归还失败
	}

	// 获取用户的所有借阅记录
	public static List<BorrowRecord> getBorrowRecordsByUser(int userId) {
		// SQL 查询语句，查询指定用户的所有借阅记录，并关联书籍表获取书籍标题
		String sql = "SELECT br.*, b.title as book_title FROM borrow_records br " +
				"JOIN books b ON br.book_id = b.id " +
				"WHERE br.user_id = ? ORDER BY br.borrow_date DESC";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入用户ID作为参数
		ResultSet rs = DBUtil.executeQuery(sql, userId);
		// 创建一个 List 用于存储用户的借阅记录
		List<BorrowRecord> records = new ArrayList<>();

		try {
			// 遍历 ResultSet，将每一行数据映射到 BorrowRecord 对象中，并添加到 List 中
			while (rs != null && rs.next()) {
				BorrowRecord record = new BorrowRecord();
				record.setId(rs.getInt("id")); // 设置借阅记录ID
				record.setUserId(rs.getInt("user_id")); // 设置用户ID
				record.setBookId(rs.getInt("book_id")); // 设置图书ID
				record.setBookTitle(rs.getString("book_title")); // 设置书籍标题
				record.setBorrowDate(rs.getString("borrow_date")); // 设置借阅日期
				record.setReturnDate(rs.getString("return_date")); // 设置归还日期
				record.setStatus(rs.getString("status")); // 设置借阅状态
				records.add(record); // 将借阅记录对象添加到 List 中
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

		// 返回包含用户所有借阅记录的 List
		return records;
	}

	// 获取所有借阅记录(管理员用)
	public static List<BorrowRecord> getAllBorrowRecords() {
		// SQL 查询语句，查询所有借阅记录，并关联书籍表和用户表获取书籍标题和用户名
		String sql = "SELECT br.*, b.title as book_title, u.username FROM borrow_records br " +
				"JOIN books b ON br.book_id = b.id " +
				"JOIN users u ON br.user_id = u.id " +
				"ORDER BY br.borrow_date DESC";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询
		ResultSet rs = DBUtil.executeQuery(sql);
		// 创建一个 List 用于存储所有借阅记录
		List<BorrowRecord> records = new ArrayList<>();

		try {
			// 遍历 ResultSet，将每一行数据映射到 BorrowRecord 对象中，并添加到 List 中
			while (rs != null && rs.next()) {
				BorrowRecord record = new BorrowRecord();
				record.setId(rs.getInt("id")); // 设置借阅记录ID
				record.setUserId(rs.getInt("user_id")); // 设置用户ID
				record.setBookId(rs.getInt("book_id")); // 设置图书ID
				record.setBookTitle(rs.getString("book_title")); // 设置书籍标题
				record.setBorrowDate(rs.getString("borrow_date")); // 设置借阅日期
				record.setReturnDate(rs.getString("return_date")); // 设置归还日期
				record.setStatus(rs.getString("status")); // 设置借阅状态
				records.add(record); // 将借阅记录对象添加到 List 中
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

		// 返回包含所有借阅记录的 List
		return records;
	}
}