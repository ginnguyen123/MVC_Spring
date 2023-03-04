package com.banking.service.deposit;

import com.banking.model.Deposit;
import com.banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DepositService implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return depositRepository.existsById(id);
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {
        depositRepository.delete(deposit);

    }

    @Override
    public void deleteById(Long id) {
        depositRepository.deleteById(id);
    }
}
