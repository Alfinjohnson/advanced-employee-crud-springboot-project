package com.example.employee.helper;

import com.example.employee.payload.request.CreateEmployeeRequest;
import com.example.employee.payload.request.UpdateEmployeeRequest;
import com.example.employee.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import static com.example.employee.utility.APPConst.*;
import static com.example.employee.utility.APPConst.isArrayOfStringsAlphabetic;

/**
 * @apiNote input validation class
 */
@Slf4j(topic = "InputValidation")
public final class InputValidation {
    /**
     * @apiNote create employee input validation method
     */
    @NonNull
    public static CreateEmployeeRequest createEmployeeRequestValidationMethod(@NonNull CreateEmployeeRequest createEmployeeRequest) {
        log.info("inside the create employee validation method");
        final String employeeName = createEmployeeRequest.getEmployeeName();
        final String employeeAge = createEmployeeRequest.getAge();
        final String employeeEmail = createEmployeeRequest.getEmail();
        final String employeeSalaryAmount = createEmployeeRequest.getSalaryAmount();
        final String[] employeeDegreeDetails = createEmployeeRequest.getDegreeDetails();

        log.info("Request create employee payload, EmployeeName: {}, Age {}, Degree Details {}, Email {}, Salary Amount {}", employeeName,
                employeeAge,employeeDegreeDetails,employeeEmail,employeeSalaryAmount);

        final boolean isEmployeeNameNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeName);
        final boolean isAgeNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeAge);


        if (isEmployeeNameNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Name can't be null or empty");
        if (isAgeNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee age can't be null or empty");
        if (!isAlphabeticWithSpace(employeeName)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Name can only have Alphabets");
        if (!isPositiveNumber(employeeAge) || !isValidAge(employeeAge)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Age not valid");


        final boolean isEmailNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeEmail);
        final boolean isSalaryAmountNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeSalaryAmount);
        final boolean isDegreeDetailsNullOrEmptyOrBlank = isArrayNullOrEmpty(employeeDegreeDetails);


        // creating a new employee DTO
        CreateEmployeeRequest newCreateEmployeeRequest = new CreateEmployeeRequest();

        newCreateEmployeeRequest.setEmployeeName(employeeName);
        newCreateEmployeeRequest.setAge(employeeAge);

        if (!isEmailNullOrEmptyOrBlank){
            if (!isValidEmail(employeeEmail)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Email format not valid");
            newCreateEmployeeRequest.setEmail(employeeEmail);
        }
        else newCreateEmployeeRequest.setEmail("NA");

        if (!isSalaryAmountNullOrEmptyOrBlank){
            if (!isPositiveNumber(employeeSalaryAmount)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Salary Amount not valid");
            newCreateEmployeeRequest.setSalaryAmount(employeeSalaryAmount);
        }else newCreateEmployeeRequest.setSalaryAmount("NA");

        if (!isDegreeDetailsNullOrEmptyOrBlank){
            if (!isArrayOfStringsAlphabetic(employeeDegreeDetails))throw new CustomException(HttpStatus.BAD_REQUEST, "Employee DegreeDetails not valid");
            newCreateEmployeeRequest.setDegreeDetails(employeeDegreeDetails);
        }else newCreateEmployeeRequest.setDegreeDetails(new String[]{"NA"});
        log.debug("newCreateEmployeeRequest {}", newCreateEmployeeRequest);
        return newCreateEmployeeRequest;
    }

    /**
     * @apiNote update employee validation method
     * @param employeeId
     * @param updateEmployeeRequest
     * @return newUpdateEmployeeRequest
     */
    @NonNull
    public static UpdateEmployeeRequest updateEmployeeRequestValidationMethod(String employeeId, UpdateEmployeeRequest updateEmployeeRequest) {
        log.info("inside update Employee validation method");
        final boolean isEmployeeIdNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeId);
        if (isEmployeeIdNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Id can't be null or empty");
        if (!isValidAlphaNumeric(employeeId)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Id format not valid");

        final String employeeName = updateEmployeeRequest.getEmployeeName();
        final String employeeAge = updateEmployeeRequest.getAge();
        final String employeeEmail = updateEmployeeRequest.getEmail();
        final String employeeSalaryAmount = updateEmployeeRequest.getSalaryAmount();
        final String[] employeeDegreeDetails = updateEmployeeRequest.getDegreeDetails();

        log.info("Request create employee payload, EmployeeName: {}, Age {}, Degree Details {}, Email {}, Salary Amount {}", employeeName,
                employeeAge,employeeDegreeDetails,employeeEmail,employeeSalaryAmount);
        final boolean isEmployeeNameNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeName);
        final boolean isAgeNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeAge);
        final boolean isEmailNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeEmail);
        final boolean isSalaryAmountNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(employeeSalaryAmount);
        final boolean isDegreeDetailsNullOrEmptyOrBlank = isArrayNullOrEmpty(employeeDegreeDetails);
        // new UpdateEmployeeRequest DTO class
        UpdateEmployeeRequest newUpdateEmployeeRequest = new UpdateEmployeeRequest();

        if (!isEmployeeNameNullOrEmptyOrBlank){
            if (!isAlphabeticWithSpace(employeeName)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Name format not valid");
            newUpdateEmployeeRequest.setEmployeeName(employeeName);
        }
        if (!isAgeNullOrEmptyOrBlank){
            if (!isPositiveNumber(employeeAge) || !isValidAge(employeeAge)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Age format not valid");
            newUpdateEmployeeRequest.setAge(employeeAge);
        }
        if (!isEmailNullOrEmptyOrBlank){
            if (!isValidEmail(employeeEmail)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Email format not valid");
            newUpdateEmployeeRequest.setEmail(employeeEmail);
        }
        if (!isSalaryAmountNullOrEmptyOrBlank){
            if (!isPositiveNumber(employeeSalaryAmount)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Salary Amount not valid");
            newUpdateEmployeeRequest.setSalaryAmount(employeeSalaryAmount);
        }
        if (!isDegreeDetailsNullOrEmptyOrBlank){
            if (!isArrayOfStringsAlphabetic(employeeDegreeDetails)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee DegreeDetails not valid");
            newUpdateEmployeeRequest.setDegreeDetails(employeeDegreeDetails);
        }
        log.debug("newUpdateEmployeeRequest {}", newUpdateEmployeeRequest);
        return newUpdateEmployeeRequest;
    }
}
