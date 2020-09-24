
package DTO;

import entities.Person;
import java.util.Date;
import javax.persistence.Temporal;


public class PersonDTO {
    /* DTO
    For the REST-endpoints that returns a Person, the JSON object should be built like this (observe the absence of the two date-fields):
 
    {"fName":"Kurt","lName":"Wonnegut", phone:"12345678","id":0}
    */ 
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    
    public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.id = person.getId();
    }
    
    public PersonDTO(String firstName,String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
