package example;

import javax.crypto.CipherInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

@WebService()
public class CustomerService implements CustomerInterface {

    List<Customer> customers = new ArrayList<>();

    @WebMethod
    @Override
    public boolean addCustomer(String name, String surname, Integer age) {
        try {
            Customer customer = new Customer(name, surname, age);
            customer.setId(customers.size() * 3);
            customers.add(customer);
            System.out.println("Customer added");
            return true;
        } catch (Exception e) {
            System.out.println("Error -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public boolean deleteCustomer(Customer customer) {
        try {
            customers.remove(customer);
            return true;
        } catch (Exception e) {
            System.out.println("Can't remove -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public Customer getCustomer(long id) {
        return customers.stream().filter(cus -> cus.getId() == id).findFirst().get();
    }

    @WebMethod
    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    public static void main(String[] argv) {
        Object implementor = new CustomerService();
        String address = "http://localhost:9000/Customers";
        Endpoint.publish(address, implementor);
    }
}
