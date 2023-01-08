package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDao extends BaseDao<Department> {
    protected DepartmentDao() {
        super(Department.class);
    }
}
