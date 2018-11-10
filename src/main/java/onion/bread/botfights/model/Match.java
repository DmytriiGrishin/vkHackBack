package onion.bread.botfights.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Table(name = "MATCH")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    public String team1;
    @Column
    public String team2;
    @Column
    public String team3;
    @Column
    public String team4;
    @Column
    public String timestamp;
    @Column
    public String tournament;
    @Enumerated(EnumType.STRING)
    public MatchStatus status;
}
