
package DTO;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {
    /*
   An instance of this DTO class, used as input to
   Gson will provide you with JSON as requested for a list of Persons
   */
    List<PersonDTO> all = new ArrayList<>();
    
    public PersonsDTO(List<Person> personEntitites) {
        personEntitites.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }
    
    
}
