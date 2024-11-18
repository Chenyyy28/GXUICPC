package manage.gxuicpc.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class User {
    private Long id;
    private String account;
    private String password;
    private String username;
    private String email;
    private int ranking;
    private short roleId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
