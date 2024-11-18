package manage.gxuicpc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum PlatformEnum {
    CODEFORCES("codeforces"),
    LUOGU("luogu"),
    NOWCODER("nowcoder"),
    VJUDGE("vjudge");
    private final String platform;

    public String setPlatform() {
        return this.platform;
    }
}
