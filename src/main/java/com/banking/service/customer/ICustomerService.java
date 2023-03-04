package com.banking.service.customer;

import com.banking.model.*;
import com.banking.service.IGeneralService;


import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {
    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address);

    List<Customer> findAllByIdNot(Long id);

    Deposit deposit(Deposit deposit);

    Withdraw withdraw(Withdraw withdraw);

    Transfer transfer(Transfer transfer);

    Suspend suspend(Suspend suspend);

}
