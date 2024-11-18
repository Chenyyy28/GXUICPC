package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Account {
    private Long userId;
    private String uid;
    private String platform;
}
