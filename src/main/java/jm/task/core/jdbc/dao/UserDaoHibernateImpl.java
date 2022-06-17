package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.connectH();
    }


    @Override
    public void createUsersTable() {
        String CreateTable = """
                    CREATE TABLE IF NOT EXISTS users (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT NOT NULL,
                                        PRIMARY KEY (`id`))""";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(CreateTable).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String DropTable = "DROP TABLE IF EXISTS users;";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(DropTable).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        String CleanTable = """
                delete
                from users;""";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(CleanTable).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void end() {
        sessionFactory.close();
    }
}
