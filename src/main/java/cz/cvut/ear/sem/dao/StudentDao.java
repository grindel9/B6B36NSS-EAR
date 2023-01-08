package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDao<Student> {
    protected StudentDao() {
        super(Student.class);
    }
}
