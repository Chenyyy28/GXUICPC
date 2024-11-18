package manage.gxuicpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Problem {
    private Long id;
    private int score;
    private int cfProblem;
    private int nkProblem;
    private int luoguProblem;
    private int atcoderProblem;
    private int vjudgeProblem;

}
