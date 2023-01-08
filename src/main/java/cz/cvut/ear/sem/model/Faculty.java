package cz.cvut.ear.sem.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Faculty {

    @EmbeddedId
    private FacultyId facultyId;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Department> departments;

    public void addDepartment(@NonNull Department department) {
        if (departments == null) {
            this.departments = new ArrayList<>();
        }
        departments.add(department);
    }
}
