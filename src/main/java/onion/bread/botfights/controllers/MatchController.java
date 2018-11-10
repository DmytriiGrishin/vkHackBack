package onion.bread.botfights.controllers;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Match;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchDao matchDao;

    @GetMapping("/matches/completed")
    public Iterable<Match> find(@RequestParam(required = false) String teamId,
                            @RequestParam(required = false) String tournamentID,
                            @RequestParam(required = false) String timestamp) {
        if (teamId == null) {
            if (tournamentID == null) {
                if (timestamp == null) {
                    return matchDao.findAll();
                } else {
                    return matchDao.findAllByTimestamp(timestamp);
                }
            } else {
                if (timestamp == null) {
                    return matchDao.findAllByTournament(tournamentID);
                } else {
                    return matchDao.findAllByTimestampAndTournament(timestamp, tournamentID);
                }
            }
        } else {
            if (tournamentID == null) {
                if (timestamp == null) {
                    return matchDao.findAllByTeam(teamId);
                } else {
                    return matchDao.findAllByTimestampAndTeam(timestamp, teamId);
                }
            } else {
                if (timestamp == null) {
                    return matchDao.findAllByTournamentAndTeam(tournamentID, teamId);
                } else {
                    return matchDao.findAllByTimestampAndTournamentAndTeam(timestamp, tournamentID, teamId);
                }
            }
        }
    }
}
