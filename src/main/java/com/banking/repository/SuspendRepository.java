package com.banking.repository;

import com.banking.model.Suspend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspendRepository extends JpaRepository<Suspend, Long> {
}
