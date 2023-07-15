package com.example.employeecrud.controller;

import com.example.employeecrud.entity.Employee;
import com.example.employeecrud.payload.request.CreateEmployeeRequest;
import com.example.employeecrud.payload.response.CreateEmployeeResponse;
import com.example.employeecrud.payload.response.GetEmployeeResponse;
import com.example.employeecrud.service.EmployeeService;
import com.example.employeecrud.utility.ApiResponse;
import com.example.employeecrud.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.employeecrud.helper.InputValidation.createEmployeeRequestValidationMethod;
import static com.example.employeecrud.utility.Const.*;

@RestController
@RequestMapping("/api/employees")
@Slf4j(topic = "Employee Controller")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

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
        response.setMessage("Success");
        response.setData(employeeResponse);
        response.setTimestamp(getCurrentTime());

        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    private Optional<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
       // Optional<Employee> employee = employeeService.findById(id);
        return null;
    }

    @DeleteMapping("/{id}")
    private void deleteEmployee(@PathVariable Long id) {
        //Employee employee = employeeService.findById(id)
       //         .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
       // employeeService.delete(employee);
    }


}
