package com.castor.empleados.services;

import com.castor.empleados.entiities.Employee;
import com.castor.empleados.entiities.Position;
import com.castor.empleados.exceptions.ValidationsException;
import com.castor.empleados.repositories.EmployeeRepository;
import com.castor.empleados.repositories.PositionRepository;
import dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {

        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeDTO> files = employees.stream().map(
                x -> {
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("api/empleados/images/")
                            .path(x.getId().toString())
                            .toUriString();
                    EmployeeDTO  employeeDTO = new EmployeeDTO();
                    employeeDTO.setId(x.getId());
                    employeeDTO.setEntryDate(x.getEntryDate());
                    employeeDTO.setName(x.getName());
                    employeeDTO.setNumberId(x.getNumberId());
                    employeeDTO.setUrl(fileDownloadUri);
                    employeeDTO.setIdPosition(x.getPosition().getIdPosition());
                    employeeDTO.setPosition(x.getPosition().getPosition());
                    return employeeDTO;
                }
        ).collect(Collectors.toList());


        return files;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee;
    }

    @Override
    public Optional<Employee> findByNumberId(Long numberId) {
        Optional<Employee> employee = employeeRepository.findByNumberId(numberId);
        return employee;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findDetailById(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/empleados/images/")
                .path(employee.get().getId().toString())
                .toUriString();

        //output
        EmployeeDTO  employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.get().getId());
        employeeDTO.setEntryDate(employee.get().getEntryDate());
        employeeDTO.setName(employee.get().getName());
        employeeDTO.setNumberId(employee.get().getNumberId());
        employeeDTO.setIdPosition(employee.get().getPosition().getIdPosition());
        employeeDTO.setPosition(employee.get().getPosition().getPosition());
        employeeDTO.setUrl(fileDownloadUri);

        return Optional.ofNullable(employeeDTO);
    }

    @Override
    @Transactional
    public Employee save(Employee employee, MultipartFile picture) throws IOException {

        //Validate numberId repited
        Optional<Employee> employeebyNumberId = employeeRepository.findByNumberId(employee.getNumberId());
        if(employeebyNumberId.isPresent()){
            System.out.println("empleado duplicado " + employeebyNumberId);
            throw new ValidationsException("Número de documento " + employeebyNumberId.get().getNumberId() + " duplicado" );
        }

        Optional<Position> position = positionRepository.findById(employee.getIdPosition());

        Employee emp = new Employee();
        emp.setNumberId(employee.getNumberId());
        emp.setPosition(position.get());
        emp.setName(employee.getName());
        emp.setEntryDate(employee.getEntryDate());
        emp.setPicture(picture.getBytes());

        return employeeRepository.save(emp);
    }

    @Override
    @Transactional
    public Optional<Employee> update(Long id, Employee employee, MultipartFile picture) throws IOException {

        //Validate duplicate
        Optional<Employee> employeebyNumberId = employeeRepository.findByNumberId(employee.getNumberId());
        if(employeebyNumberId.isPresent()){
            if (employeebyNumberId.get().getId()!=employee.getId()){
                throw new ValidationsException("Número de documento " + employeebyNumberId.get().getNumberId() + " duplicado" );
            }
        }

        Optional<Employee> empleadoFind = employeeRepository.findById(id);
        Optional<Position> position = positionRepository.findById(employee.getIdPosition());

        Employee employeeOptional = null;
        if (empleadoFind.isPresent()) {
            Employee employeeDb = empleadoFind.orElseThrow();
            employeeDb.setName(employee.getName());
            employeeDb.setNumberId(employee.getNumberId());
            employeeDb.setPosition(position.get());
            employeeDb.setPicture(picture.getBytes());

            employeeOptional = employeeRepository.save(employeeDb);
        }
        return Optional.ofNullable(employeeOptional);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        employeeRepository.deleteById(id);
    }


}
