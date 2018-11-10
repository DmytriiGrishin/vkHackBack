package onion.bread.botfights.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Team {
    @Id
    public String name;

    public List<String> players;
}
