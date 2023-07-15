package com.example.employee.payload.request;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateEmployeeRequest {

        private String employeeName;

        private String salaryAmount;

        private String age;

        private String email;

        private String[] degreeDetails;
    }
