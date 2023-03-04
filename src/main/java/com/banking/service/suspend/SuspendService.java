package com.banking.service.suspend;

import com.banking.model.Suspend;
import com.banking.repository.SuspendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuspendService implements ISuspendService{

    @Autowired
    private SuspendRepository suspendRepository;

    @Override
    public List<Suspend> findAll() {
        return suspendRepository.findAll();
    }

    @Override
    public Optional<Suspend> findById(Long id) {
        return suspendRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return suspendRepository.existsById(id);
    }

    @Override
    public Suspend save(Suspend suspend) {
        return suspendRepository.save(suspend);
    }

    @Override
    public void delete(Suspend suspend) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
