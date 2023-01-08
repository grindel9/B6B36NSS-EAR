package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.Department;
import cz.cvut.ear.sem.model.DepartmentRequest;
import cz.cvut.ear.sem.model.Faculty;
import cz.cvut.ear.sem.model.Student;
import cz.cvut.ear.sem.model.Teacher;
import cz.cvut.ear.sem.rest.util.RestUtils;
import cz.cvut.ear.sem.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;
    private final FacultyController facultyController;

    @Autowired
    public DepartmentController(DepartmentService departmentService, FacultyController facultyController) {
        this.departmentService = departmentService;
        this.facultyController = facultyController;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Department get(@PathVariable Integer id) {
        return Optional.ofNullable(departmentService.find(id))
                .orElseThrow(() -> NotFoundException.create("Department", id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> create(@RequestBody DepartmentRequest departmentRequest) {
        final Faculty faculty = facultyController.get(departmentRequest.getFacultyId());
        departmentService.create(faculty, departmentRequest.getDepartment());
        LOG.debug("Created department {}.", departmentRequest.getDepartment());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/", departmentRequest.getDepartment().getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTeacher(@PathVariable Integer id, @RequestBody Teacher teacher) {
        final Department department = departmentService.find(id);
        departmentService.createTeacher(department, teacher);
        LOG.debug("Created teacher {}.", teacher);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}/teacher", teacher.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStudent(@PathVariable Integer id, @RequestBody Student student) {
        final Department department = departmentService.find(id);
        departmentService.createStudent(department, student);
        LOG.debug("Created student {}.", student);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}/student", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
