package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String CreateTable = """
                    CREATE TABLE IF NOT EXISTS users (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT NOT NULL,
                                        PRIMARY KEY (`id`))""";
        try {
            Statement statement = connection.createStatement();
            statement.execute(CreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String DropTable = "DROP TABLE IF EXISTS users;";
        try {
            Statement statement = connection.createStatement();
            statement.execute(DropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String InsertInto = """
                INSERT INTO users (name, lastName, age)
                VALUES (?,?,?);""";
        try {
            PreparedStatement statement = connection.prepareStatement(InsertInto);
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String DeleteFrom = """
        DELETE FROM users
        WHERE id=?;""";
        try {
            PreparedStatement statement = connection.prepareStatement(DeleteFrom);
            statement.setLong(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String SelectFrom = "SELECT * FROM users";
        List<User> result = new ArrayList<>();
        try {
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(SelectFrom);
           while (resultSet.next()) {
               User user = new User();
               user.setId(resultSet.getLong("id"));
               user.setAge(resultSet.getByte("age"));
               user.setName(resultSet.getString("name"));
               user.setLastName(resultSet.getString("lastName"));
               result.add(user);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        String Delete = """
                delete
                from users;""";
        try {
            Statement statement = connection.createStatement();
            statement.execute(Delete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void end() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
