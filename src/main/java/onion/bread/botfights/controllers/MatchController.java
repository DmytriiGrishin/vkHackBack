package onion.bread.botfights.controllers;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import onion.bread.botfights.PlayerData;
import onion.bread.botfights.QLearning;
import onion.bread.botfights.dao.MatchDao;
import onion.bread.botfights.model.Entry;
import onion.bread.botfights.model.Map;
import onion.bread.botfights.model.Match;
import onion.bread.botfights.model.MatchStatus;
import onion.bread.botfights.service.MatchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchDao matchDao;

    private final MatchService matchService;

    private Map map;

    @GetMapping("/matches/start")
    public void start() {
        List<PlayerData> calculate = QLearning.calculate();

        List<List<List<Entry>>> result = calculate.get(0).data.stream().map(pair -> {
            List<List<Entry>> data = Arrays.stream(calculate.get(0).map).map(ar -> {
                List<Entry> list = new ArrayList<>();
                for (int i : ar) {
                    list.add(Entry.builder()
                            .background(i == 0 ? "stand" : "wall")
                            .build());
                }
                return list;
            }).collect(Collectors.toList());
            Integer y = pair.getValue();
            Integer x = pair.getKey();
            data.get(y).get(x).person = "player";
            data.get(y).get(x).color = "#031DE2";
            return data;
        }).collect(Collectors.toList());

        for (int i = 0; i < calculate.get(1).data.size(); i++) {
            Pair<Integer, Integer> pair = calculate.get(1).data.get(i);
            if (result.size() <=  i) {
                List<List<Entry>> data = Arrays.stream(calculate.get(1).map).map(ar -> {
                    List<Entry> list = new ArrayList<>();
                    for (int a : ar) {
                        list.add(Entry.builder()
                                .background(a == 0 ? "stand" : "wall")
                                .build());
                    }
                    return list;
                }).collect(Collectors.toList());
                result.add(i, data);
            }
            List<List<Entry>> lists = result.get(i);
            Integer y = pair.getValue();
            Integer x = pair.getKey();
            lists.get(y).get(x).person = "player";
            lists.get(y).get(x).color = "#E21303";
        }

        for (int i = 0; i < calculate.get(2).data.size(); i++) {
            Pair<Integer, Integer> pair = calculate.get(2).data.get(i);
            if (result.size() <=  i) {
                List<List<Entry>> data = Arrays.stream(calculate.get(2).map).map(ar -> {
                    List<Entry> list = new ArrayList<>();
                    for (int a : ar) {
                        list.add(Entry.builder()
                                .background(a == 0 ? "stand" : "wall")
                                .build());
                    }
                    return list;
                }).collect(Collectors.toList());
                result.add(i, data);
            }
            List<List<Entry>> lists = result.get(i);
            Integer y = pair.getValue();
            Integer x = pair.getKey();
            lists.get(y).get(x).person = "player";
            lists.get(y).get(x).color = "#E2D403";
        }

        for (int i = 0; i < calculate.get(3).data.size(); i++) {
            Pair<Integer, Integer> pair = calculate.get(3).data.get(i);
            if (result.size() <=  i) {
                List<List<Entry>> data = Arrays.stream(calculate.get(3).map).map(ar -> {
                    List<Entry> list = new ArrayList<>();
                    for (int a : ar) {
                        list.add(Entry.builder()
                                .background(a == 0 ? "stand" : "wall")
                                .build());
                    }
                    return list;
                }).collect(Collectors.toList());
                result.add(i, data);
            }
            List<List<Entry>> lists = result.get(i);
            Integer y = pair.getValue();
            Integer x = pair.getKey();
            lists.get(y).get(x).person = "player";
            lists.get(y).get(x).color = "#8B00A3";
        }
        Map map = new Map();
        this.map = map;
        map.setData(result);
    }

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

    @CrossOrigin(origins = "http://localhost:4200")
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


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/matches/match")
    public List<List<Entry>> getStream(@RequestParam Integer frame) {
        try {return map.getData().get(frame);}
        catch  (IndexOutOfBoundsException | NullPointerException e) {
            start();
        }
        return null;
    }
}
