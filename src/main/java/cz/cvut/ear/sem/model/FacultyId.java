package cz.cvut.ear.sem.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class FacultyId implements Serializable {
    private String name;
    private String street;
    private int houseNumber;
    private String city;
    private int zipCode;
}
