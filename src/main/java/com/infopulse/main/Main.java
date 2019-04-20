package com.infopulse.main;

import com.infopulse.entity.Address;
import com.infopulse.entity.Client;
import com.infopulse.entity.ComplexKey;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args){
        SessionFactory sessionFactory = (SessionFactory)
                Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa" );
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = new Client();
        ComplexKey complexKey = new ComplexKey();
        complexKey.setField1(10);
        complexKey.setField2(30);
        client.setKey(complexKey);
        client.setName("Kolya");
        client.setSurename("Pupkin");
        Address address = new Address();
        address.setCity("Kiev");
        address.setStreet("Polevya");
        client.setAddress(address);
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        sessionFactory.close();

    }
}
