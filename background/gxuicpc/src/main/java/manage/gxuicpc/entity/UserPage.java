package manage.gxuicpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@ToString
public class UserPage {
    private Integer currentPage;
    private Integer pageSize;
    //按照权限搜索
    private Integer role;
    //按照名字搜索
    private String username;

    public String toString() {
        return currentPage.toString() + "_" + pageSize.toString() + "_" + role + "_" + username;
    }
}
