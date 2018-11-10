package onion.bread.botfights.dao;

import onion.bread.botfights.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamDao extends MongoRepository<Team, String> {
}
