package facades;

import DTO.PersonDTO;
import DTO.PersonsDTO;
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
    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    /*
    public PersonsDTO(List<Person> personEntitites) {
        personEntitites.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }*/
    // Was in doubt as to how this method below should return PersonsDTO without a list. Probably to do with its constructor, but still not sure how.
    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        List<Person> personList = new ArrayList();
        List<PersonDTO> listPersons = new ArrayList();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT j FROM Person j", Person.class);
            personList = query.getResultList();
            for (Person p : personList) {
                listPersons.add(new PersonDTO(p));
            }
            return listPersons;
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
