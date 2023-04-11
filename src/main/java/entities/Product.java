package entities;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import services.HibernateUtil;

import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    private SessionFactory sessionFactory;

    public Product() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public int getQuantitySold(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT SUM(oi.quantity) FROM Order o JOIN o.orderItems oi WHERE oi.product.id = :productId");
        query.setParameter("productId", productId);
        Object result = query.getSingleResult();
        int quantitySold = (result == null) ? 0 : (int) result;
        session.getTransaction().commit();

        return quantitySold;
    }
}