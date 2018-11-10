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
    public Iterable<Match> findCompleted(@RequestParam(required = false) String teamId,
                            @RequestParam(required = false) String tournamentID,
                            @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.COMPLETED;
        return getMatches(teamId, tournamentID, timestamp, status);
    }

    @GetMapping("/matches/upcoming")
    public Iterable<Match> findUpcoming(@RequestParam(required = false) String teamId,
                                @RequestParam(required = false) String tournamentID,
                                @RequestParam(required = false) String timestamp) {
        MatchStatus status = MatchStatus.UPCOMING;
        return getMatches(teamId, tournamentID, timestamp, status);
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
