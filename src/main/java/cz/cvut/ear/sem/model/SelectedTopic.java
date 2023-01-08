package cz.cvut.ear.sem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SelectedTopic extends AbstractEntity {
    @ManyToOne
    private Topic topic;

    @ManyToOne
    @JsonIgnore
    private Student student;
}
