package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Faculty;
import cz.cvut.ear.sem.model.FacultyId;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class FacultyDao extends BaseDao<Faculty> {
    protected FacultyDao() {
        super(Faculty.class);
    }

    public Faculty find(FacultyId embeddedId) {
        Objects.requireNonNull(embeddedId);
        return em.find(type, embeddedId);
    }
}
