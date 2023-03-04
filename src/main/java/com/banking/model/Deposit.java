package com.banking.model;


import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "desposits")
public class Deposit extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    @DecimalMin(value = "10000", message = "Số tiền nạp tối thiểu là 10.000 VNĐ")
    @DecimalMax(value = "1000000000", message = "Số tiền nạp tối đa là 1.000.000.000 VNĐ")
    private BigDecimal transactionAmount;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
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

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
