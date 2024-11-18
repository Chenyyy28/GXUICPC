package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Contest {
    private Long id;
    private String description;
    private String url;
    private String author;
    private Long startTime;
    private Long endTime;
    private int roleId;
    private int weight;
    private LocalDateTime createTime;
}
