package com.eduardo.passin.dto.attendee;

import java.util.List;

public record AttendeeListResponseDTO(
        List<AttendeeDetailDTO> attendees
) {
}
