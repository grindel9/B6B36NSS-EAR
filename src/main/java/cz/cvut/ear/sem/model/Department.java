package cz.cvut.ear.sem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Department extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Faculty faculty;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Student> students;
    @ManyToMany
    @JoinTable(
            name = "department_teacher",
            joinColumns= @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonIgnore
    private Collection<Teacher> teachers;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("header")
    private Collection<Topic> topics;

    public void addStudent(@NonNull Student student) {
        Objects.requireNonNull(student);
        if (students == null) {
            this.students = new ArrayList<>();
        }
        students.add(student);
    }

    public void addTeacher(@NonNull Teacher teacher) {
        Objects.requireNonNull(teacher);
        if (teachers == null) {
            this.teachers = new ArrayList<>();
        }
        teachers.add(teacher);
    }

    public void addTopic(@NonNull Topic topic) {
        Objects.requireNonNull(topic);
        if (topics == null) {
            this.topics = new ArrayList<>();
        }
        topics.add(topic);
    }
}
