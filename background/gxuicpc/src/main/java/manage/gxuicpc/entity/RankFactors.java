package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RankFactors {
    private Long contestId;
    private Long userId;
    private int raceNum;
    private int ranking;
    private int supplement;
}