package com.example.employeecrud.helper;

import com.example.employeecrud.payload.request.CreateEmployeeRequest;
import com.example.employeecrud.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import static com.example.employeecrud.utility.Const.*;
import static com.example.employeecrud.utility.Const.isArrayOfStringsAlphabetic;

@Slf4j(topic = "InputValidation")
public class InputValidation {
    /**
     * @apiNote create employee input validation method
     */
    @NonNull
    public static CreateEmployeeRequest createEmployeeRequestValidationMethod(@NonNull CreateEmployeeRequest createEmployeeRequest) {
        final String employeeName = createEmployeeRequest.getEmployeeName();
        final String employeeAge = createEmployeeRequest.getAge();
        final String employeeEmail = createEmployeeRequest.getEmail();
        final String employeeSalaryAmount = createEmployeeRequest.getSalaryAmount();
        final String[] employeeDegreeDetails = createEmployeeRequest.getDegreeDetails();

        log.info("Request create employee payload, EmployeeName: {}, Age {}, Degree Details {}, Email {}, Salary Amount {}", employeeName,
                employeeAge,employeeDegreeDetails,employeeEmail,employeeSalaryAmount);


        final boolean isEmployeeNameNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeName);
        final boolean isAgeNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeAge);
        final boolean isEmployeeNameAlphabetical = isAlphabeticWithSpace(employeeName);
        final boolean isEmployeeAgePositiveNumber = isPositiveNumber(employeeAge);
        final boolean isEmployeeAgeValidAge = isValidAge(employeeAge);

        if (isEmployeeNameNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Name can't be null or empty");
        if (isAgeNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee age can't be null or empty");
        if (!isEmployeeNameAlphabetical) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Name can only have Alphabets");
        if (!isEmployeeAgePositiveNumber || !isEmployeeAgeValidAge) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Age not valid");


        final boolean isEmailNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeEmail);
        final boolean isSalaryAmountNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeSalaryAmount);
        final boolean isDegreeDetailsNullOrEmptyOrBlank = isArrayNullOrEmpty(employeeDegreeDetails);
        final boolean isEmployeeSalaryAmountPositiveNumber = isPositiveNumber(employeeSalaryAmount);
        final boolean isValidEmail = isValidEmail(employeeEmail);
        final boolean isEmployeeDegreeDetailsArrayOfStringsAlphabetic = isArrayOfStringsAlphabetic(employeeDegreeDetails);

        // creating a new employee request
        CreateEmployeeRequest newCreateEmployeeRequest = new CreateEmployeeRequest();

        newCreateEmployeeRequest.setEmployeeName(employeeName);
        newCreateEmployeeRequest.setAge(employeeAge);

        if (!isEmailNullOrEmptyOrBlank){
            if (!isValidEmail) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Email format not valid");
            newCreateEmployeeRequest.setEmail(employeeEmail);
        }
        else newCreateEmployeeRequest.setEmail("NA");

        if (!isSalaryAmountNullOrEmptyOrBlank){
            if (!isEmployeeSalaryAmountPositiveNumber) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Salary Amount not valid");
            newCreateEmployeeRequest.setSalaryAmount(employeeSalaryAmount);
        }else newCreateEmployeeRequest.setSalaryAmount("NA");

        if (!isDegreeDetailsNullOrEmptyOrBlank){
            if (!isEmployeeDegreeDetailsArrayOfStringsAlphabetic)throw new CustomException(HttpStatus.BAD_REQUEST, "Employee DegreeDetails not valid");
            newCreateEmployeeRequest.setDegreeDetails(employeeDegreeDetails);
        }else newCreateEmployeeRequest.setDegreeDetails(new String[]{"NA"});

        return newCreateEmployeeRequest;
    }
}
