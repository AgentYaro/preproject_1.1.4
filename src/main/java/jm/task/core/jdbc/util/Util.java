package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection connect() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/preproject";
        String USERNAME = "root";
        String PASSWORD = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    public static SessionFactory connectH() {
        Configuration config = new Configuration();
        config.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        config.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/preproject");
        config.setProperty("hibernate.connection.username","root");
        config.setProperty("hibernate.connection.password","root");
        config.setProperty("hibernate.current_session_context_class", "thread");
        config.addAnnotatedClass(User.class);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(registry);
    }
}

