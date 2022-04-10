package com.akif.entity;

import com.akif.enums.CreditStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"identityNumber"})
@ToString
public class Credit implements Serializable {
    @Id
    private long identityNumber;

    private double creditLimit;

    @Enumerated
    private CreditStatusEnum creditStatus;

    @OneToOne
    @JoinColumn(name = "customer_credit_id")
    private Customer customer;
}
