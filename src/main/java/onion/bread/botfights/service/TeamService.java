package onion.bread.botfights.service;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.TeamDao;
import onion.bread.botfights.exception.TeamNotFoundException;
import onion.bread.botfights.model.Team;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamDao teamDao;

    public Iterable<Team> findAll() {
        return teamDao.findAll();
    }

    public Team findById(String id) {
        return teamDao.findById(id).orElseThrow(() -> new TeamNotFoundException());
    }
}
