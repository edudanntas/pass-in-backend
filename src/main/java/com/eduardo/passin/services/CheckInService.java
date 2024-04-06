package com.eduardo.passin.services;

import com.eduardo.passin.domain.attendee.Attendee;
import com.eduardo.passin.domain.checkin.CheckIn;
import com.eduardo.passin.domain.checkin.CheckInAlreadyExistException;
import com.eduardo.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee){
        this.checkRegisteredCheckin(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(newCheckIn);
    }

    private void checkRegisteredCheckin(String attendeeId){
        Optional<CheckIn> checkinAttendee = this.checkInRepository.findByAttendeeId(attendeeId);
        if (checkinAttendee.isPresent()) throw new CheckInAlreadyExistException("CheckIn already exist");
    }

    public Optional<CheckIn> getCheckinByAttendeeId(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
