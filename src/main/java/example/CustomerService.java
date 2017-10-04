package example;


import Validators.AgeValidator;
import Validators.NameValidator;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;


@WebService()
public class CustomerService implements example.CustomerInterface {

    final static Logger logger = Logger.getLogger(CustomerService.class);
    private List<Customer> customers = new ArrayList<>();
    private SessionFactory sessionFactory = buildSessionFactory();

    @WebMethod(operationName = "addCustomer")
    @Override
    public boolean addCustomer(@WebParam(name = "name") String name,
                               @WebParam(name = "surname") String surname,
                               @WebParam(name = "age") Integer age) {
        if(NameValidator.checkIsValid(name) && NameValidator.checkIsValid(surname) && AgeValidator.checkIsValid(age)) {
            try {
                Customer customer = new Customer(name, surname, age);
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();
                session.save(customer);
                session.flush();
                tx.commit();
                session.close();
                logger.debug("Customer added");
                return true;
            } catch (Exception e) {
                logger.debug("Error -> " + e.getLocalizedMessage());
                return false;
            }
        }
        else{
            logger.debug("Input data is not valid (name,surname or age)");
            return false;
        }
    }

    @WebMethod
    @Override
    public boolean deleteCustomer(@WebParam(name = "customerId") long id) {
        try {
            Session session = sessionFactory.openSession();
            Customer customer = session.load(Customer.class, id);
            Transaction tx = session.beginTransaction();
            session.delete(customer);
            session.flush();
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.debug("Can't remove -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public Customer getCustomer(@WebParam(name = "id") long id) {
        Session session = sessionFactory.openSession();
        Customer customer = session.get(Customer.class, id);
        session.close();
        return customer;
    }

    @WebMethod
    public List<Orders> getCustomerOrders(@WebParam(name = "id") long id) {
        Session session = sessionFactory.openSession();
        Customer customer = session.get(Customer.class, id);
        if (customer == null) {
            return new ArrayList<Orders>();
        }
        return customer.getOrders();
    }

    @WebMethod()
    @Override
    public List<Customer> getAllCustomers() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();
        return list;
    }

    @WebMethod()
    public boolean addNewOrder(@WebParam(name = "description") String description,
                               @WebParam(name = "price") Integer price,
                               @WebParam(name = "customerId") Long customerId) {
        try {
            Session session = sessionFactory.openSession();
            Customer customer = session.get(Customer.class, customerId);
            if (customer == null) {
                logger.debug("Can't find customer with id " + customerId);
                return false;
            }
            Orders orders = new Orders(description, price);
            customer.addOrder(orders);
            Transaction tx = session.beginTransaction();
            session.save(orders);
            session.flush();
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            logger.debug("Error -> " + e.getLocalizedMessage());
            return false;
        }
    }

    SessionFactory buildSessionFactory() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                .applySetting("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/customers")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.show_sql", true)
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Orders.class)
                .buildMetadata();
        return metadata.buildSessionFactory();
    }
}































