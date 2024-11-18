package manage.gxuicpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum RoleId {
    MANAGER("管理员", 0),
    ACTIVE("现役成员", 1),
    RETIRE("退役成员", 2),
    PREPARATION("预备役", 3);
    private final String role;
    private final int id;
}
