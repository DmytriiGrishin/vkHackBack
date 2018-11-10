package onion.bread.botfights.service;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchDao matchDao;

    public Iterable<Match> getMatches(String teamId, String tournamentID, String timestamp, MatchStatus status) {
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
