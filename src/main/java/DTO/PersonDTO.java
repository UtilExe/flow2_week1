
package DTO;

import entities.Person;
import java.util.Date;
import javax.persistence.Temporal;


public class PersonDTO {
    
 //   private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastEdited;
    
    public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.created = person.getCreated();
        this.lastEdited = person.getLastEdited();
        //created = new java.util.Date();
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

    public Date getCreated() {
        return created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }
    
    
    
}
