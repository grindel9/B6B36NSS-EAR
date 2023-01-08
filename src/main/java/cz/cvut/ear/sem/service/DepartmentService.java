package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.DepartmentDao;
import cz.cvut.ear.sem.dao.FacultyDao;
import cz.cvut.ear.sem.dao.StudentDao;
import cz.cvut.ear.sem.dao.TeacherDao;
import cz.cvut.ear.sem.model.Department;
import cz.cvut.ear.sem.model.Faculty;
import cz.cvut.ear.sem.model.Student;
import cz.cvut.ear.sem.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class DepartmentService {
    private final FacultyDao facultyDao;
    private final DepartmentDao departmentDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DepartmentService(FacultyDao facultyDao, DepartmentDao departmentDao, TeacherDao teacherDao,
                             StudentDao studentDao, PasswordEncoder passwordEncoder
    ) {
        this.facultyDao = facultyDao;
        this.departmentDao = departmentDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Department find(Integer id) {
        Objects.requireNonNull(id);
        return departmentDao.find(id);
    }

    @Transactional
    public void create(Faculty faculty, Department department) {
        Objects.requireNonNull(faculty);
        Objects.requireNonNull(department);
        faculty.addDepartment(department);
        department.setFaculty(faculty);
        departmentDao.persist(department);
        facultyDao.update(faculty);
    }

    @Transactional
    public void createTeacher(Department department, Teacher teacher) {
        Objects.requireNonNull(department);
        Objects.requireNonNull(teacher);
        teacher.encodePassword(passwordEncoder);
        teacher.addDepartment(department);
        department.addTeacher(teacher);
        teacherDao.persist(teacher);
        departmentDao.update(department);
    }

    @Transactional
    public void createStudent(Department department, Student student) {
        Objects.requireNonNull(department);
        Objects.requireNonNull(student);
        student.encodePassword(passwordEncoder);
        student.setDepartment(department);
        department.addStudent(student);
        studentDao.persist(student);
        departmentDao.update(department);
    }

}
