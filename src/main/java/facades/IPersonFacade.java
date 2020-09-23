package facades;

import DTO.PersonDTO;

public interface IPersonFacade {
  public PersonDTO addPerson(String fName, String lName, String phone);  
  public PersonDTO deletePerson(int id);  
  public PersonDTO getPerson(int id);  
  public PersonDTO getAllPersons();  
  public PersonDTO editPerson(PersonDTO p);  
}

