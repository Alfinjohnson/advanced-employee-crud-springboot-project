package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.payload.request.CreateEmployeeRequest;
import com.example.employee.payload.request.UpdateEmployeeRequest;
import com.example.employee.payload.response.CreateEmployeeResponse;
import com.example.employee.payload.response.GetEmployeeResponse;
import com.example.employee.payload.response.UpdateEmployeeResponse;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.utility.expectionHandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.employee.utility.APPConst.*;

@Service
@Slf4j(topic = "EmployeeService")
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;
    public GetEmployeeResponse findById(String employeeId) {

        log.info("inside create Employee service");
        try {
            Optional<Employee> getEmployeeList = employeeRepository.findById(employeeId);

            if (getEmployeeList.isPresent()) {
                Employee employee = getEmployeeList.get();
                log.info("Employees found {}",getEmployeeList);
                // calling mapper class map to DTO Class GetEmployeeResponse
                GetEmployeeResponse getEmployeeResponse = modelMapper.map(employee, GetEmployeeResponse.class);
                return  getEmployeeResponse;
            } else throw new CustomException(HttpStatus.NOT_FOUND, "no record found with employeeId: "+employeeId);
        }
        catch (CustomException ex){
            log.info(String.valueOf(ex));
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to fetch employee record. employeeId: "+employeeId);
        }
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

    public List<GetEmployeeResponse> findAll() {
        log.info("inside create Employee service");
        try {
            List<Employee> getEmployeeList = employeeRepository.findAll();
            if (getEmployeeList.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "no employees found");
            log.info("Employees found {}",getEmployeeList);
            // calling mapper class map to DTO Class GetAllEmployeeResponse
            List<GetEmployeeResponse> getEmployeeListResponse = getEmployeeList.stream()
                    .map(employee -> modelMapper.map(employee, GetEmployeeResponse.class))
                    .collect(Collectors.toList());
            return getEmployeeListResponse;
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

    public UpdateEmployeeResponse updateEmployee(String employeeId, UpdateEmployeeRequest newUpdateEmployeeDTO) {
        log.info("inside updateEmployee service");
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();


            if (!isStringNullOrEmptyOrBlank(newUpdateEmployeeDTO.getEmployeeName())){
                existingEmployee.setEmployeeName(newUpdateEmployeeDTO.getEmployeeName());
            }

            if (!isStringNullOrEmptyOrBlank(newUpdateEmployeeDTO.getAge())){
                existingEmployee.setAge(newUpdateEmployeeDTO.getAge());
            }
            if (!isStringNullOrEmptyOrBlank(newUpdateEmployeeDTO.getEmail())){
                existingEmployee.setEmail(newUpdateEmployeeDTO.getEmail());
            }

            if (!isStringNullOrEmptyOrBlank(newUpdateEmployeeDTO.getSalaryAmount())){
                existingEmployee.setSalaryAmount(newUpdateEmployeeDTO.getSalaryAmount());
            }
            if (!isArrayNullOrEmpty(newUpdateEmployeeDTO.getDegreeDetails())){
                existingEmployee.setDegreeDetails(newUpdateEmployeeDTO.getDegreeDetails());
            }

            Employee updateEmployee = employeeRepository.save(existingEmployee);
            UpdateEmployeeResponse getEmployeeResponse;
            getEmployeeResponse = modelMapper.map(updateEmployee, UpdateEmployeeResponse.class);

            // Save the updated employee to the database
            return getEmployeeResponse;
        } else throw new CustomException(HttpStatus.NOT_FOUND,"no record found with employeeId: "+employeeId);

    }
}
