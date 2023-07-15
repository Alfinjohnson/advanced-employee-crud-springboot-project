package com.example.employeecrud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employee")
@Getter
@Setter
@ToString
public class Employee {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.example.employeecrud.utility.CustomUUIDGenerator")
    @Column(name = "employeeId", nullable = false)
    private String employeeId;

    @NotNull(message = "Employee name can't be blank")
    private String employeeName;

    private String salaryAmount;

    @NotNull(message = "Age can't be blank")
    private String age;

    private String email;

    private String[] degreeDetails;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Employee employee = (Employee) o;
        return getEmployeeId() != null && Objects.equals(getEmployeeId(), employee.getEmployeeId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
