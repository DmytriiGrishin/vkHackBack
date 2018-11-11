package onion.bread.botfights;

import javafx.util.Pair;

import java.util.List;

public class PlayerData {

    public int map [][];
    public List<Pair<Integer, Integer>> data;

    public PlayerData(int [][]map, List<Pair<Integer, Integer>> data) {
        this.map = map;
        this.data = data;
    }
}
