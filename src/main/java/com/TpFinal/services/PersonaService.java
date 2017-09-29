package com.TpFinal.services;


import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dto.persona.Persona;
import org.apache.commons.beanutils.BeanUtils;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class PersonaService {



    private static PersonaService instance;

    public static PersonaService getService() {
        if (instance == null) {

            final PersonaService personaService = new PersonaService();

            instance = personaService;
        }

        return instance;
    }

    private HashMap<Long, Persona> personas = new HashMap<>();


    public synchronized List<Persona> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<Persona>(Persona.class);
        List<Persona> personas=dao.readAll();
        if(stringFilter!=""){

            for (Persona persona : personas) {
                try {
                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                            || persona.toString().toLowerCase()
                                            .contains(stringFilter.toLowerCase());
                    if (passesFilter) {

                        arrayList.add(persona.clone());
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(PersonaService.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }
        else{
            arrayList.addAll(personas);
        }

        Collections.sort(arrayList, new Comparator<Persona>() {

            @Override
            public int compare(Persona o1, Persona o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }



    public synchronized void delete(Persona value) {
        DAO dao=new DAOImpl<Persona>(Persona.class);
        dao.delete(value);
    }

    public synchronized void save(Persona entry) {

        try {
            entry = (Persona) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        DAO dao=new DAOImpl<Persona>(Persona.class);
        dao.save(entry);
    }

}
