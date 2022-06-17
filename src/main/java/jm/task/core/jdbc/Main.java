package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService;
        userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("First", "User", (byte) 24);

        userService.saveUser("Michael", "User", (byte) 54);

        userService.saveUser("Dmitry", "Dmitriev", (byte) 24);

        userService.saveUser("Fedor", "Fedorov", (byte) 24);
        System.out.println(userService.getAllUsers());
        userService.dropUsersTable();
        userService.end();
        System.out.println("That's all, folks");
    }
}
