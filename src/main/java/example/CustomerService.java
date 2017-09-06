package example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.crypto.CipherInputStream;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//Adnotacja nad klasą wskazująca wskazująca pojedyńczy serwis
@WebService()
public class CustomerService implements CustomerInterface {
    private List<Customer> customers = new ArrayList<>();
    private SessionFactory sessionFactory =buildSessionFactory();

    @WebMethod(operationName = "addCustomer")
    @Override
    public boolean addCustomer(@WebParam(name="name")String name,
                               @WebParam(name="surname") String surname,
                               @WebParam(name="age") Integer age) {
        try {
            Customer customer = new Customer(name, surname, age);
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(customer);
            session.flush();
            tx.commit();
            session.close();
            System.out.println("Customer added");
            return true;
        } catch (Exception e) {
            System.out.println("Error -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public boolean deleteCustomer(@WebParam(name="customerId")long id) {
        try {
            Session session = sessionFactory.openSession();
            Customer customer = session.load(Customer.class,id);
            Transaction tx = session.beginTransaction();
            session.delete(customer);
            session.flush();
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("Can't remove -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public Customer getCustomer(@WebParam(name="id")long id) {
        Session session = sessionFactory.openSession();
        Customer customer = session.get(Customer.class,id);
        session.close();
        return customer;
    }

    @WebMethod()
    @Override
    public List<Customer> getAllCustomers() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();
        return  list;
    }
    SessionFactory buildSessionFactory() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                .applySetting("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/Customers")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.show_sql", true)
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Customer.class)
                .buildMetadata();
        return metadata.buildSessionFactory();
    }
}
