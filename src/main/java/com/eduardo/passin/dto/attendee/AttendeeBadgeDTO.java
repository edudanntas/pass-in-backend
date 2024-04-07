package com.eduardo.passin.dto.attendee;

public record AttendeeBadgeDTO(
        String id,
        String name,
        String email,
        String checkinURL,
        String eventId,
        String eventTitle
        ) {
}
