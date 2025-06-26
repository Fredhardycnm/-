package pro;

import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/books?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "000000";

    // 静态代码块，只在类加载时执行一次，并且优先于类的任何对象创建之前执行
    static {
        try {
            // 加载MySQL的JDBC驱动类，使得程序能够与MySQL数据库进行通信
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 如果找不到驱动类，打印异常堆栈信息
            e.printStackTrace();
        }
    }

    // 获取数据库连接的方法
    public static Connection getConnection() throws SQLException {
        // 通过DriverManager获取数据库连接，传入URL、用户名和密码
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 关闭数据库资源的方法，包括Connection、Statement和ResultSet
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            // 如果ResultSet不为空，关闭ResultSet
            if (rs != null) rs.close();
            // 如果Statement不为空，关闭Statement
            if (stmt != null) stmt.close();
            // 如果Connection不为空，关闭Connection
            if (conn != null) conn.close();
        } catch (SQLException e) {
            // 如果关闭资源时发生异常，打印异常堆栈信息
            e.printStackTrace();
        }
    }

    // 执行更新操作的方法（如INSERT、UPDATE、DELETE），返回受影响的行数
    public static int executeUpdate(String sql, Object... params) {
        // 声明数据库连接和PreparedStatement对象
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 获取数据库连接
            conn = getConnection();
            // 创建PreparedStatement对象，预编译SQL语句
            pstmt = conn.prepareStatement(sql);

            // 遍历参数数组，将参数设置到PreparedStatement中
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 执行更新操作，返回受影响的行数
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // 如果执行SQL时发生异常，打印异常堆栈信息
            e.printStackTrace();
            // 返回-1表示执行失败
            return -1;
        } finally {
            // 无论是否发生异常，都要关闭数据库连接和PreparedStatement
            close(conn, pstmt, null);
        }
    }

    // 执行查询操作的方法（如SELECT），返回ResultSet对象
    public static ResultSet executeQuery(String sql, Object... params) {
        // 声明数据库连接和PreparedStatement对象
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 获取数据库连接
            conn = getConnection();
            // 创建PreparedStatement对象，预编译SQL语句
            pstmt = conn.prepareStatement(sql);

            // 遍历参数数组，将参数设置到PreparedStatement中
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 执行查询操作，返回ResultSet对象
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // 如果执行SQL时发生异常，打印异常堆栈信息
            e.printStackTrace();
            // 返回null表示查询失败
            return null;
        }
        // 注意：这里没有关闭Connection和PreparedStatement，因为ResultSet可能还需要使用这些资源
        // 调用者需要在使用完ResultSet后手动调用close方法关闭资源
    }
}