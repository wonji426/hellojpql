package jpql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;
    private Address address;
}
