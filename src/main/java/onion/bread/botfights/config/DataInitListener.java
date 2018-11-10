package onion.bread.botfights.config;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.dao.TeamDao;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import onion.bread.botfights.model.Team;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitListener {

    private final MatchDao matchDao;

    private final TeamDao teamDao;

    @EventListener(value = ContextRefreshedEvent.class)
    public void initData() {
        matchDao.saveAll(genMatches());
        teamDao.saveAll(genTeams());
    }

    private Iterable<Team> genTeams() {
        Team team1 = Team.builder().name("NAVI").players(Arrays.asList("hz", "kto", "tam")).logo("no logo").build();
        Team team2 = Team.builder().name("EG").players(Arrays.asList("vse", "eshe", "hz")).logo("no logo").build();
        Team team3 = Team.builder().name("NP").players(Arrays.asList("bot3", "bot2", "bot1")).logo("no logo").build();
        Team team4 = Team.builder().name("VirtusPRO").players(Arrays.asList("jepa", "jopa", "joop")).logo("no logo").build();
        return Arrays.asList(team1, team2, team3, team4);
    }

    private List<Match> genMatches() {
        Match match1 = Match.builder()
                .team1("team1")
                .team2("team2")
                .team3("team3")
                .team4("team4")
                .timestamp("0")
                .status(MatchStatus.COMPLETED)
                .tournament("INT").build();
        Match match2 = Match.builder()
                .team1("team2")
                .team2("team5")
                .team3("team7")
                .team4("team8")
                .status(MatchStatus.RUNNING)
                .timestamp("1337")
                .tournament("INT").build();
        Match match3 = Match.builder()
                .team1("team9")
                .team2("team6")
                .team3("team0")
                .team4("team4")
                .status(MatchStatus.UPCOMING)
                .timestamp("1488")
                .tournament("INT").build();
        Match match4 = Match.builder()
                .team1("team2")
                .team2("team3")
                .team3("team5")
                .team4("team7")
                .status(MatchStatus.COMPLETED)
                .timestamp("228")
                .tournament("INT").build();

        return Arrays.asList(match1, match2, match3, match4);
    }

}
