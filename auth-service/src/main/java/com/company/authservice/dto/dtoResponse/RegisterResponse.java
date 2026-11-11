package com.company.authservice.dto.dtoResponse;

import java.util.UUID;

public record RegisterResponse(

        UUID id,
        String firstName,
        String lastName,
        String email,
        String message
) {
}
