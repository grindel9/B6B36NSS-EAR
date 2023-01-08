package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.SelectedTopicDao;
import cz.cvut.ear.sem.dao.StudentDao;
import cz.cvut.ear.sem.dao.TopicDao;
import cz.cvut.ear.sem.exception.CannotSellectMoreTopicsException;
import cz.cvut.ear.sem.model.SelectedTopic;
import cz.cvut.ear.sem.model.Student;
import cz.cvut.ear.sem.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StudentService {

    private final StudentDao studentDao;
    private final SelectedTopicDao selectedTopicDao;
    private final TopicDao topicDao;

    @Autowired
    public StudentService(StudentDao studentDao, SelectedTopicDao selectedTopicDao,
                          TopicDao topicDao) {
        this.studentDao = studentDao;
        this.selectedTopicDao = selectedTopicDao;
        this.topicDao = topicDao;
    }

    @Transactional
    public Student find(Integer id) {
        Objects.requireNonNull(id);
        return studentDao.find(id);
    }

    @Transactional
    public void update(Student student) {
        Objects.requireNonNull(student);
        studentDao.update(student);
    }

    @Transactional
    public void delete(Student student) {
        Objects.requireNonNull(student);
        studentDao.remove(student);
    }

    @Transactional
    public void addSelectedTopic(Student student, Topic topic) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(topic);
        if (student.getSelectedTopics().size() > 2) {
            throw new CannotSellectMoreTopicsException("Student cannot select more then 3 topics");
        }
        SelectedTopic selectedTopic = new SelectedTopic();
        selectedTopic.setStudent(student);
        selectedTopic.setTopic(topic);

        student.addSelectedTopics(selectedTopic);

        selectedTopicDao.persist(selectedTopic);
        studentDao.update(student);
        topicDao.update(topic);
    }


}
