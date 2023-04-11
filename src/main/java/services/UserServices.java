package services;

import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserServices {
    private static SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        UserServices.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    public static void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void deleteUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, userId);
        if (user != null) {
            session.delete(user);
        }
    }

    public List<User> getUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    public static User getUserByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User WHERE email = :email AND password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);

        User user = (User) query.uniqueResult();

        session.getTransaction().commit();

        return user;
    }


}