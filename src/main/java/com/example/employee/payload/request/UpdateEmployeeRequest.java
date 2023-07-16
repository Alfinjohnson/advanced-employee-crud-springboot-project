package com.example.employee.payload.request;

import lombok.*;

/**
 * @apiNote Update Employee Request payload
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class UpdateEmployeeRequest {

        private String employeeName;

        private String salaryAmount;

        private String age;

        private String email;

        private String[] degreeDetails;
    }
