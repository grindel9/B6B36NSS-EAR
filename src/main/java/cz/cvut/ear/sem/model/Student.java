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
@DiscriminatorValue("student")
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter
@Setter
public class Student extends User{
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Department department;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<SelectedTopic> selectedTopics;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(nullable = false)
    private StudyType studyType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
    private Thesis thesis;

    public void addSelectedTopics(SelectedTopic topic){
        Objects.requireNonNull(topic);
        if (selectedTopics == null) {
            this.selectedTopics = new ArrayList<>();
        }
        selectedTopics.add(topic);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(Authority.STUDENT.toString())
        );
    }
}
