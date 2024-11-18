package manage.gxuicpc.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String account;
    private String password;
    private String username;
    private String email;
    private int ranking;
    private String roleId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
