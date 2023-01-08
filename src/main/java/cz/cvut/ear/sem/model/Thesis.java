package cz.cvut.ear.sem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Thesis extends AbstractEntity{
    @OneToOne(mappedBy = "thesis")
    @JsonIgnore
    private Student student;

    @ManyToOne
    private Teacher supervisor;

    @ManyToOne
    private Teacher opponent;

    @ManyToOne
    private Teacher reviewer;
}
