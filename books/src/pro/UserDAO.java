package pro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	// 添加用户到数据库
	public static int addUser(Users user) {
		// SQL 插入语句，使用占位符 ? 来防止 SQL 注入
		String sql = "INSERT INTO users(username, password, email) VALUES(?, ?, ?)";
		// 调用 DBUtil 的 executeUpdate 方法执行 SQL 语句，并传入用户的相关信息
		return DBUtil.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getEmail());
	}

	// 根据用户名查询用户信息
	public static Users getUserByUsername(String username) {
		// SQL 查询语句，使用占位符 ? 来防止 SQL 注入
		String sql = "SELECT * FROM users WHERE username = ?";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入用户名作为参数
		ResultSet rs = DBUtil.executeQuery(sql, username);
		// 创建一个 User 对象用于存储查询结果
		Users user = null;

		try {
			// 如果 ResultSet 不为空且存在下一行数据，则将查询结果映射到 User 对象中
			if (rs != null && rs.next()) {
				user = new Users();
				user.setId(rs.getInt("id")); // 设置用户ID
				user.setUsername(rs.getString("username")); // 设置用户名
				user.setPassword(rs.getString("password")); // 设置密码
				user.setEmail(rs.getString("email")); // 设置邮箱
				user.setRole(rs.getString("role")); // 设置角色
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

		// 返回查询到的用户对象，如果没有找到则返回 null
		return user;
	}

	// 验证用户登录信息是否正确
	public static boolean validateUser(String username, String password) {
		// SQL 查询语句，使用占位符 ? 来防止 SQL 注入
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入用户名和密码作为参数
		ResultSet rs = DBUtil.executeQuery(sql, username, password);

		try {
			// 如果 ResultSet 不为空且存在下一行数据，则表示用户验证成功
			return rs != null && rs.next();
		} catch (SQLException e) {
			// 如果查询过程中发生异常，打印异常堆栈信息
			e.printStackTrace();
			// 发生异常时返回 false，表示验证失败
			return false;
		} finally {
			// 无论是否发生异常，都要关闭 ResultSet 以释放资源
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// 如果关闭 ResultSet 时发生异常，打印异常堆栈信息
				e.printStackTrace();
			}
		}
	}

	// 获取所有用户信息
	public static List<Users> getAllUsers() {
		// SQL 查询语句，查询所有用户
		String sql = "SELECT * FROM users";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询
		ResultSet rs = DBUtil.executeQuery(sql);
		// 创建一个 List 用于存储所有用户对象
		List<Users> userList = new ArrayList<>();

		try {
			// 遍历 ResultSet，将每一行数据映射到 User 对象中，并添加到 List 中
			while (rs != null && rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id")); // 设置用户ID
				user.setUsername(rs.getString("username")); // 设置用户名
				user.setEmail(rs.getString("email")); // 设置邮箱
				user.setRole(rs.getString("role")); // 设置角色
				userList.add(user); // 将用户对象添加到 List 中
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

		// 返回包含所有用户对象的 List
		return userList;
	}

	// 删除用户
	public static int deleteUser(int id) {
		// 先检查用户是否有未归还的图书
		String checkSql = "SELECT COUNT(*) FROM borrow_records WHERE user_id = ? AND status = 'borrowed'";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入用户ID作为参数
		ResultSet rs = DBUtil.executeQuery(checkSql, id);

		try {
			// 如果 ResultSet 不为空且存在下一行数据，并且未归还的图书数量大于0，则返回 -2 表示用户有未归还的图书，不能删除
			if (rs != null && rs.next() && rs.getInt(1) > 0) {
				return -2; // 用户有未归还的图书，不能删除
			}
		} catch (SQLException e) {
			// 如果查询过程中发生异常，打印异常堆栈信息
			e.printStackTrace();
			// 发生异常时返回 -1 表示删除失败
			return -1;
		} finally {
			// 无论是否发生异常，都要关闭 ResultSet 以释放资源
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// 如果关闭 ResultSet 时发生异常，打印异常堆栈信息
				e.printStackTrace();
			}
		}

		// 删除用户
		String sql = "DELETE FROM users WHERE id = ?";
		// 调用 DBUtil 的 executeUpdate 方法执行 SQL 删除语句，并传入用户ID作为参数
		return DBUtil.executeUpdate(sql, id);
	}

	// 根据用户ID查询用户信息
	public static Users getUserById(int id) {
		// SQL 查询语句，使用占位符 ? 来防止 SQL 注入
		String sql = "SELECT * FROM users WHERE id = ?";
		// 调用 DBUtil 的 executeQuery 方法执行 SQL 查询，并传入用户ID作为参数
		ResultSet rs = DBUtil.executeQuery(sql, id);
		// 创建一个 User 对象用于存储查询结果
		Users user = null;

		try {
			// 如果 ResultSet 不为空且存在下一行数据，则将查询结果映射到 User 对象中
			if (rs != null && rs.next()) {
				user = new Users();
				user.setId(rs.getInt("id")); // 设置用户ID
				user.setUsername(rs.getString("username")); // 设置用户名
				user.setEmail(rs.getString("email")); // 设置邮箱
				user.setRole(rs.getString("role")); // 设置角色
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

		// 返回查询到的用户对象，如果没有找到则返回 null
		return user;
	}
}