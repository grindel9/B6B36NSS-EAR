package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Thesis;
import org.springframework.stereotype.Repository;

@Repository
public class ThesisDao extends BaseDao<Thesis>{
    protected ThesisDao() {
        super(Thesis.class);
    }
}
