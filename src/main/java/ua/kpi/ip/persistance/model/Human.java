package ua.kpi.ip.persistance.model;

import org.springframework.data.annotation.Id;

/**
 * with lombok it would look better, but let's keep build simple
 */
public class Human {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String fbId;

    public Human(String firstName, String lastName, String fbId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fbId = fbId;
    }

    @Override
    public String toString() {
        return String.format(
                "Human[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public String getFbId() {
        return fbId;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}