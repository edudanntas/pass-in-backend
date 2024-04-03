package com.eduardo.passin.repositories;

import com.eduardo.passin.domain.entities.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
    List<Attendee> findByEventId(String id);
}
