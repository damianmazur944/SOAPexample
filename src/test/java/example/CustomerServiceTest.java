package example;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CustomerServiceTest {

    private CustomerService cs = new CustomerService();


    @Test
    public void getOrdersTestForNoCusotmer() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(-1L);
        boolean check = cs.getCustomerOrders(customer.getId()).isEmpty();
        assertTrue(check);
    }

    @Test
    public void getOrdersTestForValidCustomer() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        boolean check = !cs.getCustomerOrders(customer.getId()).isEmpty();
        assertTrue(check);
    }

    @Test
    public void getCustomerTest() {
        Customer customer = cs.getCustomer(1L);
        assertTrue(customer != null);
    }

    @Test
    public void deleteCustomerTest() {
        boolean check = cs.deleteCustomer(2L);
        assertFalse(check);
    }

    @Test
    public void addNewOrderTestForValidCustomer() {
        boolean check = cs.addNewOrder("Test", 200, 1L);
        assertTrue(check);
    }

    @Test
    public void addNewOrderTestForNoValidCustomer() {
        boolean check = cs.addNewOrder("Test", 200, 2L);
        assertFalse(check);
    }

    @Test
    public void getAllCustomersTest() {
        boolean check = cs.getAllCustomers() instanceof List;
        assertTrue(check);
    }

    @Test
    public void addCustomerTest() {
        boolean check = cs.addCustomer("Test", "Testowy", 99);
        assertTrue(check);
    }

}






























