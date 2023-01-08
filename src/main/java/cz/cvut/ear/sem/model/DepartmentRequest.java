package cz.cvut.ear.sem.model;

import lombok.Getter;

@Getter
public class DepartmentRequest {
    private FacultyId facultyId;
    private Department department;
}
