package onion.bread.botfights.dao;

import onion.bread.botfights.model.Match;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDao extends CrudRepository<Match, String> {
    @Query("Select m from Match m " +
            "where team1 = ?1 or team2 = ?1 or team3 = ?1 or team4 = ?1")
    List<Match> findAllByTeam(String command);

    List<Match> findAllByTournament(String tournament);
    List<Match> findAllByTimestamp(String timestamp);

    @Query("Select m from Match m " +
            "where timestamp = ?1 and " +
            "(team1 = ?2 or team2 = ?2 or team3 = ?2 or team4 = ?2)")
    List<Match> findAllByTimestampAndTeam(String timestamp, String command1);

    List<Match> findAllByTimestampAndTournament(String timestamp, String tournament);

    @Query("Select m from Match m " +
            "where timestamp = ?1 and tournament = ?3 and " +
            "(team1 = ?2 or team2 = ?2 or team3 = ?2 or team4 = ?2)")
    List<Match> findAllByTimestampAndTournamentAndTeam(String timestamp, String command, String tournament);

    @Query("Select m from Match m " +
            "where tournament = ?0 and " +
            "(team1 = ?1 or team2 = ?1 or team3 = ?1 or team4 = ?1)")
    List<Match> findAllByTournamentAndTeam(String tournament, String teamId);
}
