package com.example.employeecrud.repository;

import com.example.employeecrud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmail(String email);
}
