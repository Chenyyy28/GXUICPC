package manage.gxuicpc.entity.dto;

import lombok.Data;
import lombok.ToString;
import manage.gxuicpc.entity.Notice;

@Data
@ToString
public class ContestNoticeDTO extends Notice {
    private Integer contestId;
}
