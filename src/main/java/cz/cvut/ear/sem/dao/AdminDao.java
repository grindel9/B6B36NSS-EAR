package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao extends BaseDao<Admin>{
        public AdminDao() {
            super(Admin.class);
        }
}
