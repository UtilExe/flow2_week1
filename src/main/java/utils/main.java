/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Address;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilg
 */
public class main {
    /*
    public static void main(String[] args) {
          EntityManagerFactory emf;
          emf = EMF_Creator.createEntityManagerFactory();
         EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person p1 = new Person("Mads", "Hansen", "123");
            Address a1 = new Address("Streettest", 2000, "Copenhagen");
            p1.setAddress(a1);
            em.persist(p1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }*/
}
