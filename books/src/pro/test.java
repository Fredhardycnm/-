package pro;

public class test {
    public static void main(String[] args) {
        // 创建对象快捷键 alt + 回车
//        Users users = new Users("admin", "admin", "admin@qq.com");
//        int result = UserDao.addUser(users);
//        if (result > 0){
//            System.out.println("新增成功");
//        }else{
//            System.out.println("新增失败");
//        }

//        System.out.println(UserDAO.getUserByUsername("admin"));
        int i = UserDAO.deleteUser(3);
        if (i == -2){
            System.out.println("未归还书本");
        }else if (i == -1){
            System.out.println("检查代码");
        }else{
            System.out.println("删除成功");
        }

//        Book book = new Book("测试书籍", "测试作者", "1346", "测试出版社", "2023-01-01", "测试类别", 6);
//        System.out.println(BookDAO.addBook(book));

//        int  a = 1;
//        switch (a){
//            case 1:
//                System.out.println("1");
//                break;
//            case 2:
//                System.out.println("2");
//                break;
//            case 3:
//                System.out.println("3");
//                break;
//            default:
//                System.out.println("4");
//                break;
//        }
    }
}
