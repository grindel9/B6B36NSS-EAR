package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.DepartmentDao;
import cz.cvut.ear.sem.dao.FacultyDao;
import cz.cvut.ear.sem.model.Faculty;
import cz.cvut.ear.sem.model.FacultyId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
public class FacultyService {
    private final FacultyDao facultyDao;
    private final DepartmentDao departmentDao;

    @Autowired
    public FacultyService(FacultyDao facultyDao, DepartmentDao departmentDao) {
        this.facultyDao = facultyDao;
        this.departmentDao = departmentDao;
    }

    @Transactional(readOnly = true)
    public Faculty find(FacultyId embeddedId) {
        return facultyDao.find(embeddedId);
    }

    @Transactional
    public void create(Faculty faculty){
        Objects.requireNonNull(faculty);
        facultyDao.persist(faculty);
    }

}
