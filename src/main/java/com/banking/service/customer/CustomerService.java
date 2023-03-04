package com.banking.service.customer;

import com.banking.model.*;
import com.banking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private SuspendRepository suspendRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deleteById(Long id) {
       customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address) {
        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(fullName,email,phone,address);
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public Deposit deposit(Deposit deposit) {
        depositRepository.save(deposit);
        customerRepository.save(deposit.getCustomer());
        return deposit;
    }

    @Override
    public Withdraw withdraw(Withdraw withdraw) {
        customerRepository.save(withdraw.getCustomer());
        withdrawRepository.save(withdraw);
        return withdraw;
    }

    @Override
    public Transfer transfer(Transfer transfer) {
        customerRepository.save(transfer.getRecipient());
        customerRepository.save(transfer.getSender());
        transferRepository.save(transfer);
        return transfer;
    }

    @Override
    public Suspend suspend(Suspend suspend) {
        customerRepository.delete(suspend.getCustomerSuspend());
        suspendRepository.save(suspend);
        return suspend;
    }
}
