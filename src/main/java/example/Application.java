package example;

import org.apache.log4j.Logger;

import javax.xml.ws.Endpoint;

public class Application {
    final static Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] argv) {
        Object implementor = new CustomerService();
        String address = "http://localhost:9000/Customers2";
        Endpoint.publish(address, implementor);
        logger.debug("Endpoint published on address: "+address);
    }
}


