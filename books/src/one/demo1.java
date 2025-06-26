package one;

import pro.DBUtil;

import java.sql.*;

public class demo1 {
    public static void main(String[] args) throws SQLException {
        // TODO 下面是预处理方式
//        addSqlStatement();  // 注释快捷键ctrl + /（shift旁边）
//        selectSqlStatement();
//        updateSqlStatement();
        deleteSqlStatement();  // 向下复制 ctrl + d
//        Connection connection = DBUtil.getConnection();
//        DBUtil.close(connection,);
    }

    private static void deleteSqlStatement() {

    }

    private static void updateSqlStatement() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String user = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String pwd = "000000";
        Connection connection = DriverManager.getConnection(url, user, pwd);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        // 这个是之前的
//        String sql = "insert into test(username,email,info) values('admin','admin@qq.com','info')";
        // 这个是现在的
        // 变动1：使用英文问号代替值，作为一个占位符
        String sql = "update test set email = ? where id = ?";

        // 变动2：预处理的方法名
        // 这个是之前的
//        Statement statement = connection.createStatement();
        // 这个是现在的
        // 得到一个预处理的对象
        // 它将sql语句预先发送到数据库中进行编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 变动3：为占位符设置值
        // 需要注意值的数据类型，参数一是索引，从1开始，参数二是值
        // 还有就是顺序不能乱，如username 对应 1,"zhangsan"
        preparedStatement.setString(1, "test@qq.com");
        preparedStatement.setInt(2, 7);

        int addId = preparedStatement.executeUpdate(); // 得到的变量是指受到影响的行数(可以在修改和删除中再具体体现)
        if (addId > 0) {
            System.out.println("修改数据成功：" + addId);
        }
    }

    private static void selectSqlStatement() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String user = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String pwd = "000000";
        Connection connection = DriverManager.getConnection(url, user, pwd);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        String sql = "select * from test where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 7);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            System.out.println("ID：" + id + "，username：" + name + "，password：" + password + "，email：" + email);
        }
    }

    private static void addSqlStatement() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String user = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String pwd = "000000";
        Connection connection = DriverManager.getConnection(url, user, pwd);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        // 这个是之前的
//        String sql = "insert into test(username,email,info) values('admin','admin@qq.com','info')";
        // 这个是现在的
        // 变动1：使用英文问号代替值，作为一个占位符
        String sql = "insert into test(username,password,email,info) values(?,?,?,?)";

        // 变动2：预处理的方法名
        // 这个是之前的
//        Statement statement = connection.createStatement();
        // 这个是现在的
        // 得到一个预处理的对象
        // 它将sql语句预先发送到数据库中进行编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 变动3：为占位符设置值
        // 需要注意值的数据类型，参数一是索引，从1开始，参数二是值
        // 还有就是顺序不能乱，如username 对应 1,"zhangsan"
        preparedStatement.setString(1, "zhangsan");
        preparedStatement.setString(2, "123");
        preparedStatement.setString(3, "zhangsan@qq.com");
        preparedStatement.setString(4, "测试预处理");

        int addId = preparedStatement.executeUpdate(); // 得到的变量是指受到影响的行数(可以在修改和删除中再具体体现)
        if (addId > 0) {
            System.out.println("新增数据成功：" + addId);
        }

    }
}
