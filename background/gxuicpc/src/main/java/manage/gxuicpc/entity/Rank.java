package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Rank {
    private Long id;
    private String username;
    private int ranking;
    private int score;
    private int codeforces;
    private int luogu;
    private int vjudge;
    private int nowcoder;
}
