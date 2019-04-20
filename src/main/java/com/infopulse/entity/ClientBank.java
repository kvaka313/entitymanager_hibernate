package com.infopulse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "clients_banks")
public class ClientBank {
    @Id
    @GenericGenerator(name="increment", strategy = "increment")
    @GeneratedValue(generator="increment")
    @Basic
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Bank bank;

}
