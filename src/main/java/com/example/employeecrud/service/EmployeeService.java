package com.example.employeecrud.service;

import com.example.employeecrud.entity.Employee;
import com.example.employeecrud.payload.request.CreateEmployeeRequest;
import com.example.employeecrud.payload.response.CreateEmployeeResponse;
import com.example.employeecrud.repository.EmployeeRepository;
import com.example.employeecrud.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j(topic = "EmployeeService")
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findById(Long id) {
        return null;
    }

    public CreateEmployeeResponse createEmployee(@NonNull CreateEmployeeRequest createEmployeeRequest) {
        log.info("inside create Employee service");
        if(isEmailAlreadyExists(createEmployeeRequest.getEmail())) throw new CustomException(HttpStatus.BAD_REQUEST, "Employee Email already exist in record");

        try {
            Employee buildEmployee = Employee.builder()
                    .employeeName(createEmployeeRequest.getEmployeeName())
                    .age(createEmployeeRequest.getAge())
                    .degreeDetails(createEmployeeRequest.getDegreeDetails())
                    .email(createEmployeeRequest.getEmail().toLowerCase())
                    .salaryAmount(createEmployeeRequest.getSalaryAmount())
                    .build();
            buildEmployee = employeeRepository.save(buildEmployee);

            log.info("created EmployeeId {}",buildEmployee.getEmployeeId());
            CreateEmployeeResponse createEmployeeResponse = new CreateEmployeeResponse();
            createEmployeeResponse.setEmployeeId(buildEmployee.getEmployeeId());
            return createEmployeeResponse;
        } catch (CustomException ex) {
           throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to save employee");
        }
    }

    public void delete(Employee employee) {

    }

    public List<Employee> findAll() {
        log.info("inside create Employee service");
        try {
            List<Employee> getEmployeeList = employeeRepository.findAll();
            if (getEmployeeList.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "no employees found");
            log.info("Employees found {}",getEmployeeList);
            return getEmployeeList;
        }catch (EmptyResultDataAccessException ex){
            log.info(String.valueOf(ex));
            throw new CustomException(HttpStatus.NOT_FOUND,"no employees found");
        }
        catch (NullPointerException | DataAccessException ex){
            log.info(String.valueOf(ex));
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to fetch employee list");
        }
    }

    public boolean isEmailAlreadyExists(String email) {
        log.info("isEmailAlreadyExists {}",email);
        return employeeRepository.existsByEmail(email);
    }

}
