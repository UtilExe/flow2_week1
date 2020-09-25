package rest;

import DTO.PersonDTO;
import Exceptions.PersonNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("count")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokeCount() {
        long count = FACADE.getPersonCount();
        
        return "{\"count\":"+count+"}";
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson(@PathParam("id") int id) throws PersonNotFoundException {
        return GSON.toJson(FACADE.getPerson(id));
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllpersons() {
        return GSON.toJson(FACADE.getAllPersons());
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPerson(String person) {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pAdded = FACADE.addPerson(pDTO.getFirstName(), pDTO.getLastName(), pDTO.getPhone());
        return GSON.toJson(pAdded);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String editPerson(@PathParam("id") Integer id, String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO personNew = FACADE.editPerson(personDTO);
        return GSON.toJson(personNew);
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(@PathParam("id") Integer id) throws PersonNotFoundException {
        PersonDTO personDeleted = FACADE.deletePerson(id);
        return GSON.toJson(personDeleted);
    }
    
}