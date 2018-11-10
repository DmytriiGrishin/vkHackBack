package onion.bread.botfights.controllers;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import onion.bread.botfights.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchDao matchDao;

    private final MatchService matchService;

    @GetMapping("/matches/completed")
    public Iterable<Match> findCompleted(@RequestParam(required = false) String team,
                            @RequestParam(required = false) String tournament,
                            @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.COMPLETED;
        return matchService.getMatches(team, tournament, timestamp, status);
    }

    @GetMapping("/matches/upcoming")
    public Iterable<Match> findUpcoming(@RequestParam(required = false) String team,
                                        @RequestParam(required = false) String tournament,
                                        @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.UPCOMING;
        return matchService.getMatches(team, tournament, timestamp, status);
    }

    @GetMapping("/matches")
    public Iterable<Match> find() {
        return matchDao.findAll();
    }

    @GetMapping("/matches/running")
    public Iterable<Match> findRunning(@RequestParam(required = false) String team,
                                        @RequestParam(required = false) String tournament,
                                        @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.RUNNING;
        return matchService.getMatches(team, tournament, timestamp, status);
    }
}
