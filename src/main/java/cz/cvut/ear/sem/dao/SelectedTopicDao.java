package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.model.SelectedTopic;
import org.springframework.stereotype.Repository;

@Repository
public class SelectedTopicDao extends BaseDao<SelectedTopic>{
    protected SelectedTopicDao() {
        super(SelectedTopic.class);
    }
}
