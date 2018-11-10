package onion.bread.botfights.controllers;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchDao matchDao;

    @GetMapping("/matches/completed")
    public Iterable<Match> findCompleted(@RequestParam(required = false) String team,
                            @RequestParam(required = false) String tournament,
                            @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.COMPLETED;
        return getMatches(team, tournament, timestamp, status);
    }

    @GetMapping("/matches/upcoming")
    public Iterable<Match> findUpcoming(@RequestParam(required = false) String team,
                                        @RequestParam(required = false) String tournament,
                                        @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.UPCOMING;
        return getMatches(team, tournament, timestamp, status);
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
        return getMatches(team, tournament, timestamp, status);
    }

    private Iterable<Match> getMatches(String teamId, String tournamentID, String timestamp, MatchStatus status) {
        if (teamId == null) {
            if (tournamentID == null) {
                if (timestamp == null) {
                    return matchDao.findAllByStatus(status);
                } else {
                    return matchDao.findAllByTimestamp(timestamp, status);
                }
            } else {
                if (timestamp == null) {
                    return matchDao.findAllByTournament(tournamentID, status);
                } else {
                    return matchDao.findAllByTimestampAndTournament(timestamp, tournamentID, status);
                }
            }
        } else {
            if (tournamentID == null) {
                if (timestamp == null) {
                    return matchDao.findAllByTeam(teamId, status);
                } else {
                    return matchDao.findAllByTimestampAndTeam(timestamp, teamId, status);
                }
            } else {
                if (timestamp == null) {
                    return matchDao.findAllByTournamentAndTeam(tournamentID, teamId, status);
                } else {
                    return matchDao.findAllByTimestampAndTournamentAndTeam(timestamp, tournamentID, teamId, status);
                }
            }
        }
    }
}
