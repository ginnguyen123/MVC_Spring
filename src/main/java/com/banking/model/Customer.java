package com.banking.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "customers")
public class Customer extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String address;

    @OneToMany(targetEntity = Deposit.class)
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> transfers;

    @OneToMany(targetEntity = Transfer.class)
    private List<Withdraw> withdraws;

    @OneToOne(targetEntity = Suspend.class)
    private Customer customerSuspend;

    @Column(precision = 10,scale = 0,nullable = false)
    private BigDecimal balance;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
//        validate cho fiel cuủa customer
    }
}