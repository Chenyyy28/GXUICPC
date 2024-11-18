package manage.gxuicpc.entity;

import lombok.Data;

@Data
public class ContestPage {
    private Integer currentPage;
    private Integer pageSize;
    private String contestName;
    private int roleId;

    public String toString() {
        return currentPage.toString() + "_" + pageSize.toString() + "_" + contestName;
    }
}
