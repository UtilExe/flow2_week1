package facades;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import Exceptions.PersonNotFoundException;
import entities.Address;
import entities.Person;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone, String street, int zip, String city) {
        Person person = new Person(fName, lName, phone);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            /* What is the benefit of the commented code (From Jon) instead of the one I have, hm.
            (why need to check if the address already exists? and why is a SELECT query and setParameter() necessary here?
            Query query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.zip = :zip AND a.city = :city");
            query.setParameter("street", street);
            query.setParameter("zip", zip);
            query.setParameter("city", city);
            List<Address> addresses = query.getResultList();
            if (addresses.size() > 0) {
                person.setAddress(addresses.get(0)); // The address already exists
            } else {
                person.setAddress(new Address(street, zip, city));
            }*/
            
            person.setAddress(new Address(street,zip,city));
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        Address address = em.find(Address.class, id);
        try {
            if (person == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }
            em.getTransaction().begin();
            em.remove(person);
            // If you delete a Person, also delete the Address since no one else “is living here” given the one-to-one relationship between the two.
            em.remove(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    // Would it have been better to use Catch, instead of throwing it at method level?
    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("No person with provided id found");
            }
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            return new PersonsDTO(em.createQuery("SELECT j FROM Person j").getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, p.getId());
        person.setFirstName(p.getFirstName());
        person.setLastName(p.getLastName());
        person.setPhone(p.getPhone());
        // Issue: Last edit uses the initial object/date initialization and not the last time it was actually edited.
        person.setLastEdited();
        person.getAddress().setStreet(p.getStreet());
        person.getAddress().setZip(p.getZip());
        person.getAddress().setCity(p.getCity());

        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

}
