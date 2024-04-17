package edu.tcu.cs.superfrogscheduler.model.dto;

import javax.validation.constraints.NotEmpty;

public record SuperFrogStudentDto(
        Integer id,
        @NotEmpty(message = "First name is required") String firstName,
        @NotEmpty(message = "Last name is required") String lastName,
        @NotEmpty(message = "Email is required") String email,

        String phoneNumber,
        String address,

        boolean active
) {
}
