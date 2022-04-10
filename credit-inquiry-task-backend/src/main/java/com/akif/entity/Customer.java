package com.akif.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"identityNumber"})
@ToString
public class Customer implements Serializable {
    @Id
    private long identityNumber;

    @Column(length = 100, name = "name")
    private String name;

    @Column(length = 100, name = "surname")
    private String surName;

    private double monthlyIncome;

    private long telephone;

    @OneToOne
    @JoinColumn(name = "customer_credit_id")
    private Credit credit;
}
