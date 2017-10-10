package ua.kpi.ip;

import org.springframework.data.annotation.Id;

public class Customer {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String fbId;

    public Customer() {
    }


    public Customer(String firstName, String lastName, String fbId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fbId = fbId;

    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}