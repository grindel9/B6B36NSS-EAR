package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends BaseDao<Teacher>{
    protected TeacherDao() {
        super(Teacher.class);
    }


}
