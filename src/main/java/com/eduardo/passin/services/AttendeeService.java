package com.eduardo.passin.services;

import com.eduardo.passin.domain.attendee.Attendee;
import com.eduardo.passin.domain.attendee.exception.AttendeeAlreadyRegisteredException;
import com.eduardo.passin.domain.attendee.exception.AttendeeNotFoundException;
import com.eduardo.passin.domain.checkin.CheckIn;
import com.eduardo.passin.dto.attendee.AttendeeBadgeDTO;
import com.eduardo.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.eduardo.passin.dto.attendee.AttendeeDetailDTO;
import com.eduardo.passin.dto.attendee.AttendeeListResponseDTO;
import com.eduardo.passin.repositories.AttendeeRepository;
import com.eduardo.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAttendeeListFromEvent(String eventId) {
        return attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.getAttendeeListFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailDTOList = attendeeList
                .stream()
                .map(attendee -> {
                    Optional<CheckIn> checkIn = checkInRepository.findByAttendeeId(attendee.getId());
                    LocalDateTime checkInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
                    return new AttendeeDetailDTO(
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getCreatedAt(),
                            checkInAt
                    );
                }).toList();

        return new AttendeeListResponseDTO(attendeeDetailDTOList);
    }

    public void checkSubscriptionAttendee(String email, String eventId) {
        Optional<Attendee> registeredAttendee = this.attendeeRepository.findByEmailAndEventId(email, eventId);

        if (registeredAttendee.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee already registered");
    }

    public void registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));

        String uri = uriComponentsBuilder.path("/atendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUriString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }
}
