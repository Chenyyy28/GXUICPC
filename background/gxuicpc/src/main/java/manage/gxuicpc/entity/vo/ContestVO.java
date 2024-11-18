package manage.gxuicpc.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContestVO {
    private Long id;
    private String description;
    private String url;
    private String author;
    private Long startTime;
    private Long endTime;
    private int roleId;
    private int weight;
    private Long createTime;
}
