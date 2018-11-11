package onion.bread.botfights.controllers;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.model.Team;
import onion.bread.botfights.service.TeamService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/teams")
    public Iterable<Team> findAll() {
        return teamService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/teams/team")
    public Team findById(@RequestParam String id) {
        return teamService.findById(id);
    }
}
