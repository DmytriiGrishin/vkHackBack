package onion.bread.botfights.model;

import lombok.Data;

import java.util.List;

@Data
public class Map {


    List<List<List<Entry>>> data;
}
