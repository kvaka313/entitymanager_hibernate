package com.infopulse.main;

import com.infopulse.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = (SessionFactory)
                Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
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

        BadClient badClient = new BadClient();
        badClient.setName("Kolya");
        badClient.setSurename("Pupkin");
        badClient.setSum(300);
        entityManager.persist(badClient);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Order> ordersList = entityManager
                .createQuery("select o FROM Order o JOIN o.client cl WHERE cl.name = 'Kolya'", Order.class).getResultList();
        for (Order o : ordersList) {
            System.out.println(o.getName());
        }
        Order o1 = ordersList.get(0);
        Client c = o1.getClient();
        c.getOrders().remove(o1);
        o1.setClient(null);
        entityManager.remove(o1);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        List<Result> result = entityManager
                .createQuery("SELECT new com.infopulse.entity.Result(cl.name, o.name) FROM Client cl JOIN cl.orders o", Result.class).getResultList();
        for (Result r : result) {
            System.out.println(r.getClientName() + " " + r.getOrderName());
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Statustics> statistics = entityManager
                .createQuery(
                        "SELECT new com.infopulse.entity.Statustics(cl.name, count(cl.name)) " +
                                "FROM Client cl WHERE cl.name = :name GROUP BY cl.name", Statustics.class)
                .setParameter("name", "Kolya")
                .getResultList();

        for (Statustics s : statistics) {
            System.out.println(s.getName() + " " + s.getCount());
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteria = builder
                .createQuery(Client.class);
        Root<Client> root = criteria.from(Client.class);
        criteria.select(root);
        List<Client> clients = entityManager.createQuery(criteria).getResultList();
        for (Client cli : clients) {
            System.out.println(cli.getName());
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        CriteriaBuilder builder1 = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteria1 = builder
                .createQuery(String.class);
        Root<Client> root1 = criteria1.from(Client.class);
        criteria1.select(root1.get(Client_.name));
        List<String> clientNames = entityManager.createQuery(criteria1).getResultList();
        for (String name : clientNames) {
            System.out.println(name);
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        CriteriaBuilder builder2 = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria2 = builder
                .createQuery(Order.class);
        Root<Order> root2 = criteria2.from(Order.class);
        criteria2.select(root2);
        Join<Order, Client> orderJoin
                = root2.join(Order_.client);

        criteria2.where(builder2.equal(orderJoin.get(Client_.name), "Kolya"));
        List<Order> orderLists = entityManager.createQuery(criteria2)
                .getResultList();
        for (Order o : orderLists) {
            System.out.println(o.getName());
        }
        entityManager.getTransaction().commit();
        entityManager.close();

        sessionFactory.close();
      }

}

