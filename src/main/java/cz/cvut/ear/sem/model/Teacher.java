package cz.cvut.ear.sem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("teacher")
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter
@Setter
public class Teacher extends User{

    @ManyToMany
    @JoinTable(
            name = "department_teacher",
            joinColumns= @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @JsonIgnore
    private Collection<Department> departments;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("header")
    private Collection<Topic> topics;

    private Title title;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Thesis> supervisorsThesis;

    @OneToMany(mappedBy = "opponent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Thesis> opponentsThesis;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Thesis> reviewersThesis;

    public void addTopic(Topic topic){
        Objects.requireNonNull(topic);
        if (topics == null) {
            this.topics = new ArrayList<>();
        }
        topics.add(topic);
    }

    public void addDepartment(Department department){
        Objects.requireNonNull(department);
        if (departments == null) {
            this.departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(Authority.TEACHER.toString())
        );
    }
}
