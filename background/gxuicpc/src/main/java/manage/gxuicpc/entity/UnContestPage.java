package manage.gxuicpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
//@ToString
public class UnContestPage {
    private Integer currentPage;
    private Integer pageSize;
    private Integer userId;
    private String contestName;

    public String toString() {
        return currentPage.toString() + "_" + pageSize.toString() + "_" + userId.toString() + "_" + contestName;
    }
}
