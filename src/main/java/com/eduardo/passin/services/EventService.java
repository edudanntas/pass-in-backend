package com.eduardo.passin.services;

import com.eduardo.passin.domain.attendee.Attendee;
import com.eduardo.passin.domain.event.Event;
import com.eduardo.passin.domain.event.exceptions.EventNotFoundException;
import com.eduardo.passin.dto.event.EventIdDTO;
import com.eduardo.passin.dto.event.EventRequestDTO;
import com.eduardo.passin.dto.event.EventResponseDTO;
import com.eduardo.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetails(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Not found event with id:" + eventId));
        List<Attendee> attendees = attendeeService.getAttendeeListFromEvent(eventId);
        return new EventResponseDTO(event, attendees.size());
    }

    public EventIdDTO createEvent(EventRequestDTO requestDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(requestDTO.title());
        newEvent.setDetails(requestDTO.details());
        newEvent.setMaximumAttendees(requestDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(requestDTO.title()));

        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
