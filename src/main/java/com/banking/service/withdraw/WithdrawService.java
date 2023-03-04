package com.banking.service.withdraw;

import com.banking.model.Withdraw;
import com.banking.repository.WithdrawRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawService implements IWithdrawService{

    private WithdrawRepository withdrawRepository;
    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return withdrawRepository.existsById(id);
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Withdraw withdraw) {
        withdrawRepository.delete(withdraw);
    }

    @Override
    public void deleteById(Long id) {
        withdrawRepository.deleteById(id);
    }
}
