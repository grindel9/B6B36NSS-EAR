package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.StudentDao;
import cz.cvut.ear.sem.dao.ThesisDao;
import cz.cvut.ear.sem.exception.DuplicatedParametersIsNotAllowed;
import cz.cvut.ear.sem.exception.NotFoundException;
import cz.cvut.ear.sem.model.SelectedTopic;
import cz.cvut.ear.sem.model.Student;
import cz.cvut.ear.sem.model.Teacher;
import cz.cvut.ear.sem.model.Thesis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ThesisService {
    private final StudentDao studentDao;
    private final ThesisDao thesisDao;

    @Autowired
    public ThesisService(StudentDao studentDao, ThesisDao thesisDao) {
        this.studentDao = studentDao;
        this.thesisDao = thesisDao;
    }

    @Transactional
    public void create(Student student, SelectedTopic selectedTopic, Teacher supervisor,
                       Teacher opponent, Teacher reviewer){
        if (supervisor == opponent || supervisor == reviewer || opponent == reviewer){
            throw new DuplicatedParametersIsNotAllowed("thesis opponent, supervisor and reviewer must not have any duplicity");
        }
        if (!student.getSelectedTopics().contains(selectedTopic)){
            throw new NotFoundException("selected topic was not found in topics");
        }
        if (student.getThesis() != null){
            throw new IllegalArgumentException("cannot add thesis, one thesis already exist");
        }
        Thesis thesis = new Thesis();
        thesis.setStudent(student);
        thesis.setOpponent(opponent);
        thesis.setReviewer(reviewer);
        thesis.setSupervisor(supervisor);

        student.setThesis(thesis);

        thesisDao.persist(thesis);
        studentDao.update(student);
    }

    @Transactional
    public void delete(Student student){
        Objects.requireNonNull(student);
        Thesis thesis = student.getThesis();
        student.setThesis(null);
        studentDao.update(student);
        thesisDao.remove(thesis);
    }
}
