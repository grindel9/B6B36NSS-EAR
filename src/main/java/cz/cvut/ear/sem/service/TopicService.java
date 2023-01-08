package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.TopicDao;
import cz.cvut.ear.sem.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
public class TopicService {
    private final TopicDao topicDao;

    @Autowired
    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Transactional(readOnly = true)
    public Topic find(Integer id) {
        return topicDao.find(id);
    }

    @Transactional(readOnly = true)
    public Collection<Topic> findByHeader(String header) {
        return topicDao.findByHeader(header);
    }

}
