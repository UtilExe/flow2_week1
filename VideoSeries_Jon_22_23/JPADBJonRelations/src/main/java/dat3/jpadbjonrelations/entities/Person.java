/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpadbjonrelations.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author emilg
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_id;
    private String name;
    private int year;
    
    // En til en relation og hvis jeg indsætter en person entitet i databasen og den indeholder en adresse, så vil den også automatisk oprette en adresse entitet og koble den på her (Via. CascadeType.PERSIST.
    // Det er muligt via vores reference i Tester.java:
    /* p1.setAddress(a1);
        p2.setAddress(a2);
    Og så slipper vi for:
    //em.persist(a1);
    //em.persist(a2);
    */
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    List<Fee> fees;
    
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    List<SwimStyle> styles;

    public Person() {
    }

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
        this.fees = new ArrayList<>();
        this.styles = new ArrayList<>();
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void addFee(Fee fee) {
        this.fees.add(fee);
        if (fee != null) {
            fee.setPerson(this);
        }
    }
    
    public void addSwimStyle(SwimStyle style) {
        if (styles != null) {
        this.styles.add(style);
        style.getPersons().add(this);
        }
    }
    
    public void removeSwimStyle(SwimStyle swimStyle) {
        if (swimStyle != null) {
            styles.remove(swimStyle);
            swimStyle.getPersons().remove(this);
            
        }
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
      public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setPerson(this);
        }
    }
}
