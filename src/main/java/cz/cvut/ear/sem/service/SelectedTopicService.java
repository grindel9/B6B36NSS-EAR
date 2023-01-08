package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.SelectedTopicDao;
import cz.cvut.ear.sem.model.SelectedTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SelectedTopicService {

    private final SelectedTopicDao selectedTopicDao;

    @Autowired
    public SelectedTopicService(SelectedTopicDao selectedTopicDao) {
        this.selectedTopicDao = selectedTopicDao;
    }

    @Transactional
    public SelectedTopic find(Integer id) {
        Objects.requireNonNull(id);
        return selectedTopicDao.find(id);
    }

    @Transactional
    public void delete(SelectedTopic selectedTopic) {
        Objects.requireNonNull(selectedTopic);
        selectedTopicDao.remove(selectedTopic);
    }
}
