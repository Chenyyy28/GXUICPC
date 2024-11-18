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
public class UserVO {
    private Long id;
    private String username;
    private String account;
    private String password;
    private String email;
    private int ranking;
    private String roleId;
    private Long createTime;
    private Long updateTime;
}
