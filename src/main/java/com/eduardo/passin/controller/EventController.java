package com.eduardo.passin.controller;

import com.eduardo.passin.dto.attendee.AttendeeListResponseDTO;
import com.eduardo.passin.dto.event.EventIdDTO;
import com.eduardo.passin.dto.event.EventRequestDTO;
import com.eduardo.passin.dto.event.EventResponseDTO;
import com.eduardo.passin.services.AttendeeService;
import com.eduardo.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventDetails(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventDetails(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO requestDTO, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = this.eventService.createEvent(requestDTO);

        URI uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeeListResponseDTO attendeeList = this.attendeeService.getEventAttendees(id);
        return ResponseEntity.ok(attendeeList);
    }

}
