package facades;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import Exceptions.PersonNotFoundException;
import entities.Person;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    public PersonDTO addPerson(String fName, String lName, String phone) {
        Person person = new Person(fName, lName, phone);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
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
        try {
            if (person == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new PersonDTO(person);
    }
    
    public long getPersonCount(){
        EntityManager em = getEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return personCount;
        } finally{  
            em.close();
        } 
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
        // Issue: Last edit doesn't change. Tried this code but no luck: 
        // person.setLastEdited(new java.util.Date());
        // Besides from that, edit method works well.
        
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new PersonDTO(person);
    }

}
