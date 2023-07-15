package com.example.employee.controller;

import com.example.employee.payload.request.CreateEmployeeRequest;
import com.example.employee.payload.request.UpdateEmployeeRequest;
import com.example.employee.payload.response.CreateEmployeeResponse;
import com.example.employee.payload.response.GetEmployeeResponse;
import com.example.employee.payload.response.UpdateEmployeeResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.utility.ApiResponse;
import com.example.employee.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.employee.helper.InputValidation.createEmployeeRequestValidationMethod;
import static com.example.employee.helper.InputValidation.updateEmployeeRequestValidationMethod;
import static com.example.employee.utility.APPConst.*;

@RestController
@RequestMapping("/api/employees")
@Slf4j(topic = "Employee Controller")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @apiNote  get all employee method
     * */
    @NonNull
    @GetMapping
    private ResponseEntity<ApiResponse<List<GetEmployeeResponse>>> getAllEmployees() {
        log.info("inside get all employees controller");
        List<GetEmployeeResponse> getAllEmployee = employeeService.findAll();
        ApiResponse<List<GetEmployeeResponse>> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Success");
        response.setData(getAllEmployee);
        response.setTimestamp(getCurrentTime());
        return ResponseEntity.ok(response);
    }

    /**
     * @param id 
     * @return GetEmployeeResponse
     */
    @NonNull
    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<GetEmployeeResponse>> getEmployeeById(@PathVariable String id) {
        log.info("inside get all get employees by id controller");
        if (isStringNullOrEmptyOrBlank(id)) throw new CustomException(HttpStatus.BAD_REQUEST, "EmployeeId not present");
        if (!isValidAlphaNumeric(id)) throw new CustomException(HttpStatus.BAD_REQUEST, "EmployeeId not valid");

        GetEmployeeResponse getAllEmployee = employeeService.findById(id);
        ApiResponse<GetEmployeeResponse> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Success");
        response.setData(getAllEmployee);
        response.setTimestamp(getCurrentTime());
        return ResponseEntity.ok(response) ;
    }

    /**
     * @apiNote create employee controller
     * */
    @NonNull
    @PostMapping
    private ResponseEntity<ApiResponse<CreateEmployeeResponse>> createEmployee(@NonNull @RequestBody CreateEmployeeRequest createEmployeeRequest)   {
        log.info("inside create employee controller");
        CreateEmployeeRequest newCreateEmployeeRequest = createEmployeeRequestValidationMethod(createEmployeeRequest);
        CreateEmployeeResponse employeeResponse= employeeService.createEmployee(newCreateEmployeeRequest);
        ApiResponse<CreateEmployeeResponse> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("New Employee Created");
        response.setData(employeeResponse);
        response.setTimestamp(getCurrentTime());

        return ResponseEntity.ok(response);
    }


    /**
     * @apiNote update employee method
     * @param id 
     * @param updateEmployeeRequest
     * @return
     */
    @NonNull
    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<UpdateEmployeeResponse>> updateEmployee(@PathVariable String id, @RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        log.info("inside update employee controller");
        UpdateEmployeeRequest newUpdateEmployeeDTO = updateEmployeeRequestValidationMethod(id, updateEmployeeRequest);
        UpdateEmployeeResponse updateEmployeeResponse = employeeService.updateEmployee(id, newUpdateEmployeeDTO);
        ApiResponse<UpdateEmployeeResponse> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Employee updated");
        response.setData(updateEmployeeResponse);
        response.setTimestamp(getCurrentTime());

        return ResponseEntity.ok(response);
    }


    /**
     * @param id
     * @return
     */
    @NonNull
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        log.info("inside delete employee controller");
        final boolean isEmployeeIdNullOrEmptyOrBlank = isStringNullOrEmptyOrBlank(id);
        if (isEmployeeIdNullOrEmptyOrBlank) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Id can't be null or empty");
        if (!isValidAlphaNumeric(id)) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Id format not valid");
        if (!employeeService.delete(id))throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed delete employee:n"+id);
        return ResponseEntity.noContent().build();
    }
}
