package client;

import example.Customer;
import example.CustomerService;

import java.util.List;

public class Client {
    public static void main(String[] args) {
        CustomerService service=new CustomerService();
//        service.addCustomer(new Customer("test1","test2",23));
//        service.addCustomer(new Customer("123","te2st2",23));
//        service.addCustomer(new Customer("tes123t1","tes3t2",23));
//        service.addCustomer(new Customer("32","tes4t2",26));
//        service.addCustomer(new Customer("1","te5st2",25));

        List<Customer> customers=service.getAllCustomers();
        System.out.println("All customers : "+customers);
    }
}
