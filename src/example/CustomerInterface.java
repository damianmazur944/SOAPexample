package example;

import java.util.List;

public interface CustomerInterface {

    public boolean addCustomer(String name, String surname, Integer age);

    public boolean deleteCustomer(Customer customer);

    public Customer getCustomer(long id);

    public List<Customer> getAllCustomers();
}
