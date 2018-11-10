package onion.bread.botfights.dao;

import onion.bread.botfights.model.Match;

import onion.bread.botfights.model.MatchStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchDao extends CrudRepository<Match, String> {
    @Query("Select m from Match m " +
            "where team1 = ?1 or team2 = ?1 or team3 = ?1 or team4 = ?1 and status = ?2")
    Iterable<Match> findAllByTeam(String command, MatchStatus status);

    @Query("Select m from Match m " +
            "where tournament = ?1 and status = ?2")
    Iterable<Match> findAllByTournament(String tournament, MatchStatus status);
    @Query("Select m from Match m " +
            "where timestamp = ?1 and status = ?2")
    Iterable<Match> findAllByTimestamp(String timestamp, MatchStatus status);

    @Query("Select m from Match m " +
            "where timestamp = ?1 and " +
            "(team1 = ?2 or team2 = ?2 or team3 = ?2 or team4 = ?2) and status = ?3 ")
    Iterable<Match> findAllByTimestampAndTeam(String timestamp, String command1, MatchStatus status);
    @Query("Select m from Match m " +
            "where timestamp = ?1 and tournament = ?2 and status = ?3")
    Iterable<Match> findAllByTimestampAndTournament(String timestamp, String tournament, MatchStatus status);

    @Query("Select m from Match m " +
            "where timestamp = ?1 and tournament = ?3 and " +
            "(team1 = ?2 or team2 = ?2 or team3 = ?2 or team4 = ?2) and status = ?4")
    Iterable<Match> findAllByTimestampAndTournamentAndTeam(String timestamp, String command, String tournament, MatchStatus status);

    @Query("Select m from Match m " +
            "where tournament = ?1 and " +
            "(team1 = ?2 or team2 = ?2 or team3 = ?2 or team4 = ?2) and status = ?3")
    Iterable<Match> findAllByTournamentAndTeam(String tournament, String teamId, MatchStatus status);

    @Query("Select m from Match m " +
            "where status = ?1")
    Iterable<Match> findAllByStatus(MatchStatus status);
}
