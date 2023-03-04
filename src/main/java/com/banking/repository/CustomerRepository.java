package com.banking.repository;

import com.banking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address);

    List<Customer> findAllByIdNot(Long id);
}
