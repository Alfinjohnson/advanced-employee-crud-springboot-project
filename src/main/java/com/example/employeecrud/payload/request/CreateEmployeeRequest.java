package com.example.employeecrud.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequest {

    private String employeeName;

    private String salaryAmount;

    private String age;

    private String email;

    private String[] degreeDetails;
}
