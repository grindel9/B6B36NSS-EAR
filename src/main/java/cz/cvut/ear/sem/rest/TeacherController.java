package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.exception.NotInRelatedTableCollection;
import cz.cvut.ear.sem.model.Department;
import cz.cvut.ear.sem.model.Teacher;
import cz.cvut.ear.sem.model.Topic;
import cz.cvut.ear.sem.rest.util.RestUtils;
import cz.cvut.ear.sem.service.TeacherService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final DepartmentController departmentController;
    private final TopicController topicController;

    @Autowired
    public TeacherController(TeacherService teacherService, DepartmentController departmentController, TopicController topicController) {
        this.teacherService = teacherService;
        this.departmentController = departmentController;
        this.topicController = topicController;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Teacher get(@PathVariable Integer id) {
        return Optional.ofNullable(teacherService.find(id))
                .orElseThrow(() -> NotFoundException.create("Teacher", id));
    }

    @PostMapping(value = "/{id}/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTopic(@PathVariable Integer id, @RequestBody Topic topic,
                                            @RequestParam Integer departmentId, @RequestParam Optional<Integer> parentId) {
        final Teacher teacher = get(id);
        final Department department = departmentController.get(departmentId);
        if (!teacher.getDepartments().contains(department)){
           throw NotInRelatedTableCollection.create("Department", id, "Teacher");
        }
        if(parentId.isPresent()){
            final Topic ancestorTopic = topicController.get(parentId.get());
            teacherService.createTopic(teacher, department, topic, ancestorTopic);
        }else {
            teacherService.createTopic(teacher, department, topic);
        }
        LOG.debug("Created topic {}.", topic);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}/topic", topic.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
