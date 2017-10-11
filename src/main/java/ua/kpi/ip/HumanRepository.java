package ua.kpi.ip;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HumanRepository extends MongoRepository<Human, String> {

    public Human findByFbId(String fbId);
    public List<Human> findByFirstName(String firstName);
    public List<Human> findByLastName(String lastName);

}