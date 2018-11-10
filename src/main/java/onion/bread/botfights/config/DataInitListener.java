package onion.bread.botfights.config;

import lombok.RequiredArgsConstructor;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitListener {

    private final MatchDao matchDao;

    @EventListener(value = ContextRefreshedEvent.class)
    public void initData() {
        matchDao.saveAll(genMatches());
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
                .status(MatchStatus.UPCOMING)
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
