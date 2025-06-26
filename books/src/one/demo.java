package one;

import java.sql.*;

public class demo {
    public static void main(String[] args) throws SQLException {
        // TODO 下面都是非预处理方式
        /**
         * java操作数据库（JDBC）
         * 连接数据库
         *      使用一个jar包（可以称为依赖或者配置），也会对应数据库的版本（5.0/8.0）
         *  使用jar包
         *      1  在项目中新建一个文件夹（lib）
         *      2 把jar包放进去
         *      3  右键文件夹，选择Add as Lib
         */
        /**
         * 连接时的区别（5.0和8.0）
         * 格式：jdbc:数据库类型://主机名:端口/数据库名?       后面的就是区别  配置连接使用 & 隔开
         * 1 字符集
         *      5.0                                     8.0
         *      需要手动设置characterEncoding = UTF-8     直接使用ut8mb4
         * 2 时区
         *      5.0                                     8.0
         *      不支持（所有时间默认使用服务器本地时间）    支持使用serverTimezone  UTC/Asia/Shanghai
         * 3 ssL
         *      5.0     8.0
         *      不支持     可以设置为true
         */
        // 连接
//        getConnection();
        // 增加、删除、修改公用executeUpdate
        // 查询使用executeQuery
        // 增加
//        addSql();
        // 查询
//        selectSql();
        // 删除
//        deleteSql(); // alt + 回车
        // 修改
//        updateSql();

        // 问题
//        System.out.println(sqlStatement("admin1","123")); // 正常查询
        // TODO sql注入问题
        System.out.println(sqlStatement("admin1' -- ","123"));
        // 生成的sql语句：select * from test where username = 'admin1' -- ' and password = '123'

    }


    private static boolean sqlStatement(String username, String password) throws SQLException {
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

        String sql = "select * from test where username = '" + username + "' and password = '" +password+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.next();

    }

    private static void updateSql()  {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String username = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String password = "000000";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("数据库连接成功");
            } else {
                System.out.println("数据库连接失败");
            }

            // 创建一个基本的statement对象，用于执行静态的sql语句
            Statement statement = connection.createStatement();
            // 准备修改的sql语句
            String sql = "update test set email = 'admin@qq.com' where id = 6";
            int addId = statement.executeUpdate(sql); // 得到的变量是指受到影响的行数(可以在修改和删除中再具体体现)
            if (addId > 0) {
                System.out.println("修改数据成功：" + addId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteSql() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String username = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String password = "000000";
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        // 创建一个基本的statement对象，用于执行静态的sql语句
        Statement statement = connection.createStatement();
        // 准备删除的sql语句
        String sql = "delete from test where id = 5";
        int addId = statement.executeUpdate(sql); // 得到的变量是指受到影响的行数(可以在修改和删除中再具体体现)
        if (addId > 0) {
            System.out.println("删除数据成功：" + addId);
        }
    }

    private static void selectSql() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String username = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String password = "000000";
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        Statement statement = connection.createStatement();
        String sql = "select * from test";
        // 作为一个结果集（结果的集合）
        ResultSet resultSet = statement.executeQuery(sql);
        // 处理结果集，将游标（箭头）移动到下一行，如果有结果返回true，否则返回false
        while (resultSet.next()) {
            // 处理每一行数据
            // 从当前行获取指定列的值，需要注意列的数据类型
            int id = resultSet.getInt("id"); // 里面是数据库的字段名
            String name = resultSet.getString("username"); // 里面是数据库的字段名
            String email = resultSet.getString("email"); // 里面是数据库的字段名
            System.out.println("ID：" + id + "，username：" + name + "，email：" + email);
        }

    }

    private static void addSql() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String username = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String password = "000000";
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }

        // 创建一个基本的statement对象，用于执行静态的sql语句
        Statement statement = connection.createStatement();
        // 准备新增的sql语句
        String sql = "insert into test(username,email,info) values('admin','admin@qq.com','info')";
        int addId = statement.executeUpdate(sql); // 得到的变量是指受到影响的行数(可以在修改和删除中再具体体现)
        if (addId > 0) {
            System.out.println("新增数据成功：" + addId);
        }

    }

    private static void getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/books?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&ssl=true";
        String username = "root";
        // Exception in thread "main" java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
        String password = "000000";
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection != null) {
            System.out.println("数据库连接成功");
        } else {
            System.out.println("数据库连接失败");
        }
    }
}
