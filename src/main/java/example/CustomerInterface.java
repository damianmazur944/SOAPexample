package example;

import java.util.List;

public interface CustomerInterface {

     boolean addCustomer(String name, String surname, Integer age);

     boolean deleteCustomer(long id);

     Customer getCustomer(long id);

     List<Customer> getAllCustomers();
}
