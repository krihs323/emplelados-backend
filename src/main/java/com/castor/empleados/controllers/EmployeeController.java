package com.castor.empleados.controllers;

import com.castor.empleados.entiities.Employee;
import com.castor.empleados.services.EmployeeService;
import dto.EmployeeDTO;
import dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/empleados")
//@CrossOrigin(origins = "*")
public class EmployeeController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> list() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> openImage(@PathVariable Long id) {
        Optional<Employee> employeeDetail = employeeService.findById(id);

        if(employeeDetail.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(employeeDetail.get().getPicture());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> details(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDetail = employeeService.findDetailById(id);

        if(employeeDetail.isPresent()) {
            return ResponseEntity.ok(employeeDetail.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDTO> saveEmployee(
            @ModelAttribute Employee employee,
            @RequestPart(value = "file", required = false) MultipartFile picture) throws IOException {

        employeeService.save(employee, picture);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Empleado creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute Employee employee, @RequestParam("file") MultipartFile file) throws IOException {

        Optional<Employee> employeeUpdated = employeeService.update(id, employee, file);
        if (employeeUpdated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Empleado actualizado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<Employee> employeeDeleted = employeeService.findById(id);
        if (employeeDeleted.isPresent()) {
            employeeService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
