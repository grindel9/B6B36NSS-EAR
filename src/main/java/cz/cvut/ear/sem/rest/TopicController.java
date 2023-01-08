package cz.cvut.ear.sem.rest;

import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.Topic;
import cz.cvut.ear.sem.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private static final Logger LOG = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic get(@PathVariable Integer id) {
        return Optional.ofNullable(topicService.find(id))
                .orElseThrow(() -> NotFoundException.create("Topic", id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Topic> topicsMatchingHeader(@RequestParam String header) {
        return Optional.ofNullable(topicService.findByHeader(header))
                .orElseThrow(() -> NotFoundException.create("Topic", header));
    }
}
