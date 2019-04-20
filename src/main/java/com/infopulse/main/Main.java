package com.infopulse.main;

import com.infopulse.entity.*;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        SessionFactory sessionFactory = (SessionFactory)
              Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa" );
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = new Client();
//        ComplexKey complexKey = new ComplexKey();
//        complexKey.setField1(10);
//        complexKey.setField2(30);
//        client.setKey(complexKey);

        Order order1 = new Order();
        order1.setName("order1");
        order1.setClient(client);

        Order order2 = new Order();
        order2.setName("order2");
        order2.setClient(client);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        client.setOrders(orders);

        Telephone telephone = new Telephone();
        telephone.setPhone("555666777");
        telephone.setClient(client);
        client.setTelephone(telephone);

        client.setName("Kolya");
        client.setSurename("Pupkin");
        Address address = new Address();
        address.setCity("Kiev");
        address.setStreet("Polevya");
        client.setAddress(address);
        entityManager.persist(client);
//direct many to many
//        Bank bank1 = new Bank();
//        bank1.setName("private");
//        bank1.setClients(Arrays.asList(client));
//        entityManager.persist(bank1);
//
//        Bank bank2 = new Bank();
//        bank2.setName("pravex");
//        bank2.setClients(Arrays.asList(client));
//        entityManager.persist(bank2);
//
//        List<Bank> banks = new ArrayList<>();
//        banks.add(bank1);
//        banks.add(bank2);
//        client.setBanks(banks);
//        entityManager.persist(client);

        //Two constraints one to many

        Bank bank1 = new Bank();
        bank1.setName("private");
        entityManager.persist(bank1);

        Bank bank2 = new Bank();
        bank2.setName("pravex");
        entityManager.persist(bank2);

        ClientBank clientBank1 = new ClientBank();
        clientBank1.setClient(client);
        clientBank1.setBank(bank1);
        client.setClientBanks(Arrays.asList(clientBank1));
        bank1.setClientBanks(Arrays.asList(clientBank1));
        entityManager.persist(clientBank1);
        entityManager.persist(client);
        entityManager.persist(bank1);

        ClientBank clientBank2 = new ClientBank();
        clientBank2.setClient(client);
        clientBank2.setBank(bank2);
        client.setClientBanks(Arrays.asList(clientBank2));
        bank2.setClientBanks(Arrays.asList(clientBank2));
        entityManager.persist(clientBank2);
        entityManager.persist(client);
        entityManager.persist(bank2);


        entityManager.getTransaction().commit();
        sessionFactory.close();

    }
}
