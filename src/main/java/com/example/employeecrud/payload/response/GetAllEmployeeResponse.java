package com.example.employeecrud.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllEmployeeResponse {

    private String employeeName;

    private String salaryAmount;

    private String age;

    private String email;

    private String[] degreeDetails;
}
