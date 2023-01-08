package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.DepartmentDao;
import cz.cvut.ear.sem.dao.TeacherDao;
import cz.cvut.ear.sem.dao.TopicDao;
import cz.cvut.ear.sem.model.Department;
import cz.cvut.ear.sem.model.Teacher;
import cz.cvut.ear.sem.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TeacherService {
    private final TeacherDao teacherDao;
    private final TopicDao topicDao;
    private final DepartmentDao departmentDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao, TopicDao topicDao,DepartmentDao departmentDao) {
        this.teacherDao = teacherDao;
        this.topicDao = topicDao;
        this.departmentDao = departmentDao;
    }

    @Transactional(readOnly = true)
    public Teacher find(Integer id) {
        return teacherDao.find(id);
    }

    @Transactional
    public void createTopic(Teacher teacher, Department department, Topic topic, Topic ancestorTopic){
        Objects.requireNonNull(topic);
        Objects.requireNonNull(ancestorTopic);

        ancestorTopic.addChildTopic(topic);
        topic.setAncestorTopic(ancestorTopic);

        createTopic(teacher, department, topic);

        topicDao.update(ancestorTopic);
    }

    @Transactional
    public void createTopic(Teacher teacher, Department department, Topic topic){
        Objects.requireNonNull(teacher);
        Objects.requireNonNull(department);
        Objects.requireNonNull(topic);

        teacher.addTopic(topic);
        department.addTopic(topic);
        topic.setDepartment(department);
        topic.setTeacher(teacher);

        topicDao.persist(topic);
        teacherDao.update(teacher);
        departmentDao.update(department);
    }
}
