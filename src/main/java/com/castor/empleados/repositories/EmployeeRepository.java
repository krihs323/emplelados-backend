package com.castor.empleados.repositories;

import com.castor.empleados.entiities.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByNumberId(Long numberId);

}
