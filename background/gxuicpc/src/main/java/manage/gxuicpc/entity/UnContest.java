package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

@Data
@ToString
public class UnContest {
    private Long id;
    private String contestName;
    private String url;
    private Long userId;
}
