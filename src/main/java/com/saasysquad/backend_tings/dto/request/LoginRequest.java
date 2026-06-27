package com.saasysquad.backend_tings.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @NotBlank(message = "email not provided")
        String email,

        @NotBlank(message = "password not provided")
        @Size(min = 8, message = "password must be at least of length 8")
        String password
) {}
