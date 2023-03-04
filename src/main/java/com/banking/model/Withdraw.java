package com.banking.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "withdraws")
public class Withdraw extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;


    @Column(name = "transaction_amount", precision = 10, scale = 0)
    private BigDecimal transAmount;



    public Withdraw() {
    }

    public Withdraw(Long id, Customer customer, BigDecimal transAmount) {
        this.id = id;
        this.customer = customer;
        this.transAmount = transAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }
}
