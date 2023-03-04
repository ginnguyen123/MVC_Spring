package com.banking.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;

    Long fees;

    @Column(name = "fees_amount", precision = 10, scale = 0, nullable = false)
    BigDecimal feesAmount;

    @Column(name = "trans_amount", precision = 10, scale = 0, nullable = false)
    BigDecimal transAmount;

    public Transfer() {
    }

    public Transfer(Long id, Customer sender, Customer recipient, Long fees, BigDecimal feesAmount, BigDecimal transAmount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transAmount = transAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    public Long getFees() {
        return fees;
    }

    public void setFees(Long fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }
}
