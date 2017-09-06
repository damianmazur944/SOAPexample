package example;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.xml.ws.Endpoint;

public class Application {

    CustomerService customerService = new CustomerService();

    public static void main(String[] argv) {
        Object implementor = new CustomerService();
        String address = "http://localhost:9000/Customers";
        Endpoint.publish(address, implementor);
    }
}
