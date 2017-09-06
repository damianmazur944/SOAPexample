package example;

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
//W metodzie main tworzymy instancje serwisu, ustawiamy adres i publikujemy endpoint
    public static void main(String[] argv) {
        Object implementor = new CustomerService();
        String address = "http://localhost:9000/Customers";
        Endpoint.publish(address, implementor);
    }


//Adnotacja WebMethod wskazuje na metode dostępną przez WS
    @WebMethod(operationName = "addCustomer")
    @Override
    public boolean addCustomer(@WebParam(name="name")String name,
                               @WebParam(name="surname") String surname,
                               @WebParam(name="age") Integer age) {
        try {
            Customer customer = new Customer(name, surname, age);
            customer.setId(getMaxId()+1);
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
    public boolean deleteCustomer(@WebParam(name="id")long id) {
        try {
            customers.remove(customers.stream().filter(cus -> cus.getId() == id).findFirst().orElse(null));
            return true;
        } catch (Exception e) {
            System.out.println("Can't remove -> " + e.getLocalizedMessage());
            return false;
        }
    }

    @WebMethod
    @Override
    public Customer getCustomer(@WebParam(name="id")long id) {
        return customers.stream().filter(cus -> cus.getId() == id).findFirst().orElse(null);
    }

    @WebMethod()
    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    private long getMaxId(){
        List<Long> ids = customers.stream().map(Customer::getId).collect(Collectors.toList());
        return ids.stream().max(Long::compare).orElse(-1L);
    }
}
