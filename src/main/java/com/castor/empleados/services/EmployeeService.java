package com.castor.empleados.services;

import com.castor.empleados.entiities.Employee;
import dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    Optional<Employee> findById(Long id);
    Optional<Employee> findByNumberId(Long numberId);

    Optional<EmployeeDTO> findDetailById(Long id);

    Employee save(Employee employee, MultipartFile picture) throws IOException;

    Optional<Employee>  update(Long id, Employee employee, MultipartFile picture) throws IOException;

    void remove(Long id);

}
