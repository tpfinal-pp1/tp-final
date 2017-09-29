package com.TpFinal.services;


import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dto.Person;
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
public class ContactService {



    private static ContactService instance;

    public static ContactService getService() {
        if (instance == null) {

            final ContactService contactService = new ContactService();

            instance = contactService;
        }

        return instance;
    }

    private HashMap<Long, Person> contacts = new HashMap<>();


    public synchronized List<Person> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<Person>(Person.class);
        List<Person> personas=dao.readAll();
        for (Person contact : personas) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || contact.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ContactService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }



    public synchronized void delete(Person value) {
        DAO dao=new DAOImpl<Person>(Person.class);
        dao.delete(value);
    }

    public synchronized void save(Person entry) {

        try {
            entry = (Person) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        DAO dao=new DAOImpl<Person>(Person.class);
        dao.save(entry);
    }

}
