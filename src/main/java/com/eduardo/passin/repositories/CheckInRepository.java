package com.eduardo.passin.repositories;

import com.eduardo.passin.domain.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
