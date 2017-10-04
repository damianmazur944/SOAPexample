package example;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class Orders {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String description;
    @Column
    private Integer price;
    @ManyToOne
    private Customer customer;

    public Orders(String description, Integer price) {
        this.description = description;
        this.price = price;
    }

    public Orders() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
