package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;

@Repository
public class TopicDao extends BaseDao<Topic>{
    protected TopicDao() {
        super(Topic.class);
    }
    public Collection<Topic> findByHeader(String header){
        return em.createNamedQuery("Topic.findByHeader",
                Topic.class)
                .setParameter("header", "%" + header + "%")
                .getResultList();
    }
}
