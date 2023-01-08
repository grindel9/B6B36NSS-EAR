package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.SelectedTopic;
import cz.cvut.ear.sem.rest.util.RestUtils;
import cz.cvut.ear.sem.service.SelectedTopicService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/selected-topic")
public class SelectedTopicController {

    private static final Logger LOG = LoggerFactory.getLogger(SelectedTopicController.class);

    private final SelectedTopicService selectedTopicService;

    @Autowired
    public SelectedTopicController(SelectedTopicService selectedTopicService) {
        this.selectedTopicService = selectedTopicService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SelectedTopic get(@PathVariable Integer id) {
        return Optional.ofNullable(selectedTopicService.find(id))
                .orElseThrow(() -> NotFoundException.create("Selected topic", id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        final SelectedTopic selectedTopic = get(id);
        selectedTopicService.delete(selectedTopic);
        LOG.debug("Deleted {}.", selectedTopic);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", selectedTopic.getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
