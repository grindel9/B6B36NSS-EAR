package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.Faculty;
import cz.cvut.ear.sem.model.FacultyId;
import cz.cvut.ear.sem.rest.util.RestUtils;
import cz.cvut.ear.sem.service.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private static final Logger LOG = LoggerFactory.getLogger(FacultyController.class);

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Faculty get(@RequestBody FacultyId embeddedId) {
        return Optional.ofNullable(facultyService.find(embeddedId))
                .orElseThrow(() -> NotFoundException.create("Faculty", embeddedId.toString()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> create(@RequestBody Faculty faculty) {
        facultyService.create(faculty);
        LOG.debug("Created faculty {}.", faculty);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", faculty.getFacultyId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
