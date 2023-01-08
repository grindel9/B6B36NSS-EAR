package cz.cvut.ear.sem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NamedQuery(name = "Topic.findByHeader",
        query = "select t from Topic t where t.header like :header")
public class Topic extends AbstractEntity{
    private String header;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Teacher teacher;

    @OneToMany(mappedBy = "ancestorTopic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<Topic> childTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Topic ancestorTopic;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<SelectedTopic> selectedTopics;

    public void addChildTopic(Topic topic){
        topic.setAncestorTopic(this);
        if (childTopic == null) {
            this.childTopic = new ArrayList<>();
        }
        childTopic.add(topic);
    }
}
