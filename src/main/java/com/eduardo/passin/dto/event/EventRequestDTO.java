package com.eduardo.passin.dto.event;

public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees,
        String slug
) {
}
