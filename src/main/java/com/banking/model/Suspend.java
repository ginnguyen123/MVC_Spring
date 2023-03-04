package com.banking.model;

import sun.java2d.loops.DrawGlyphListAA;

import javax.persistence.*;

@Entity
@Table(name = "suspends")
public class Suspend extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "suspend_id", referencedColumnName = "id", nullable = false)
    private Customer customerSuspend;

    public Suspend() {
    }

    public Suspend(Long id, Customer customerSuspend) {
        this.id = id;
        this.customerSuspend = customerSuspend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomerSuspend() {
        return customerSuspend;
    }

    public void setCustomerSuspend(Customer customerSuspend) {
        this.customerSuspend = customerSuspend;
    }
}
