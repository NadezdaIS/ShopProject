package services;

import entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductServices {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
    }

    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }

    public void deleteProduct(int productId) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.load(Product.class, productId);
        if (product != null) {
            session.delete(product);
        }
    }

    public List<Product> getProducts() {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("from Product", Product.class);
        return query.getResultList();
    }

    public Product getProductByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Product WHERE name = :productName");
        query.setParameter("productName", name);

        List<Product> products = query.getResultList();

        session.getTransaction().commit();

        if (products.size() > 0) {
            return products.get(0);
        } else {
            return null;
        }
    }

    public void save(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(product);
        session.getTransaction().commit();
    }

    public List<Product> getAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query<Product> query = session.createQuery("FROM Product", Product.class);
        List<Product> products = query.getResultList();
        session.getTransaction().commit();
        return products;
    }


}
