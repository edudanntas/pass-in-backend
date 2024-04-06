package com.eduardo.passin.controller;

import com.eduardo.passin.dto.event.EventIdDTO;
import com.eduardo.passin.dto.event.EventRequestDTO;
import com.eduardo.passin.dto.event.EventResponseDTO;
import com.eduardo.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventDetails(@PathVariable String id) {
        EventResponseDTO event = this.service.getEventDetails(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO requestDTO, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = this.service.createEvent(requestDTO);

        URI uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }
}
