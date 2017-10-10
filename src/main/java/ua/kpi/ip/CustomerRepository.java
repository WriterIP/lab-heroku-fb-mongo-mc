package ua.kpi.ip;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFbId(String fbId);
    public List<Customer> findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}
