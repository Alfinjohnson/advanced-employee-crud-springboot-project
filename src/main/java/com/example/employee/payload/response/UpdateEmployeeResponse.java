package com.example.employee.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateEmployeeResponse {
    private String employeeId;

    private String employeeName;

    private String salaryAmount;

    private String age;

    private String email;

    private String[] degreeDetails;
}
