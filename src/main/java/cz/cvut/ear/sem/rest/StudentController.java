package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.Department;
import cz.cvut.ear.sem.model.SelectedTopic;
import cz.cvut.ear.sem.model.Student;
import cz.cvut.ear.sem.model.Teacher;
import cz.cvut.ear.sem.model.Topic;
import cz.cvut.ear.sem.rest.util.RestUtils;
import cz.cvut.ear.sem.service.StudentService;
import cz.cvut.ear.sem.service.ThesisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final DepartmentController departmentController;
    private final TopicController topicController;
    private final TeacherController teacherController;
    private final SelectedTopicController selectedTopicController;
    private final ThesisService thesisService;

    @Autowired
    public StudentController(StudentService studentService, DepartmentController departmentController, TopicController topicController,
                             TeacherController teacherController, SelectedTopicController selectedTopicController, ThesisService thesisService) {
        this.studentService = studentService;
        this.departmentController = departmentController;
        this.topicController = topicController;
        this.teacherController = teacherController;
        this.selectedTopicController = selectedTopicController;
        this.thesisService = thesisService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student get(@PathVariable Integer id) {
        var a = Optional.ofNullable(studentService.find(id))
                .orElseThrow(() -> NotFoundException.create("Student", id));
        return a;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Student student,
                                       @RequestParam Optional<Integer> departmentId) {
        get(id);
        if (departmentId.isPresent()) {
            final Department department = departmentController.get(departmentId.get());
            student.setDepartment(department);
        }
        studentService.update(student);
        LOG.debug("Created student {}.", student);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        final Student student = get(id);
        studentService.delete(student);
        LOG.debug("Created student {}.", student);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addSelectedTopic(@PathVariable Integer id, @RequestParam Integer topicId) {
        final Student student = get(id);
        final Topic topic = topicController.get(topicId);
        studentService.addSelectedTopic(student, topic);
        LOG.debug("Add topic {} to student {}.", topic, student);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/thesis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> selectThesis(@PathVariable Integer id, @RequestParam Integer selectedTopicId, @RequestParam Integer supervisorId,
                                             @RequestParam Integer opponentId, @RequestParam Integer reviewerId) {
        final Student student = get(id);
        final SelectedTopic selectedTopic = selectedTopicController.get(selectedTopicId);
        final Teacher supervisor = teacherController.get(supervisorId);
        final Teacher opponent = teacherController.get(opponentId);
        final Teacher reviewer = teacherController.get(reviewerId);
        thesisService.create(student, selectedTopic, supervisor, opponent, reviewer);
        LOG.debug("Add thesis for student {}.", student);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}/thesis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteThesis(@PathVariable Integer id) {
        final Student student = get(id);
        if (student.getThesis() == null) {
            throw NotFoundException.create("thesis", student.getId());
        }
        thesisService.delete(student);
        LOG.debug("Deleted thesis in student {}.", student.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", student.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
