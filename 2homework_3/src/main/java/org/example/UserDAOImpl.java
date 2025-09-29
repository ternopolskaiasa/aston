package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAOImpl() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new HibernateException("Hibernate's initialization failed!");
        }
    }

    /**
     * Method gives user by its id
     * @return user with particular id
     */
    @Override
    public User getUserById(Long id) throws DAOException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new DAOException("Invalid id!", e);
        }
    }

    /**
     * Method gives all users from database
     * @return all users
     */
    @Override
    public List<User> getAllUsers() throws DAOException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            throw new DAOException("Problems with database connection!", e);
        }
    }

    /**
     * Method saves user into database
     */
    @Override
    public void saveUser(User user) throws DAOException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Problems with adding new user!", e);
        }
    }

    /**
     * Method updates particular user's fields
     */
    @Override
    public void updateUser(User user) throws DAOException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User existingUser = session.get(User.class, user.getId());

            if (existingUser == null) {
                throw new DAOException("User is not found!");
            }

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAge(user.getAge());
            existingUser.setCreatedAt(user.getCreatedAt());
            session.update(existingUser);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Problems with updating user!", e);
        }
    }

    /**
     * Method deletes user by its id
     */
    @Override
    public void deleteUser(Long id) throws DAOException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if (user == null) {
                throw new DAOException("User is not found!");
            }

            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Problems with deleting user!", e);
        }
    }
}
