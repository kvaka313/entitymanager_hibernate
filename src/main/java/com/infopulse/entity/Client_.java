package com.infopulse.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client, Long> id;
    public static volatile SingularAttribute<Client, String> name;
    public static volatile SingularAttribute<Client, String> surename;
    public static volatile SingularAttribute<Client, Address> address;
    public static volatile ListAttribute<Client, Order> orders;
    public static volatile SingularAttribute<Client, Telephone> telephone;
    public static volatile SingularAttribute<Client, ClientBank> clientBanks;
    public static volatile SingularAttribute<Client, String> nameSurename;
}

