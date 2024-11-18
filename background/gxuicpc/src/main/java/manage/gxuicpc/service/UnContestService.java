package manage.gxuicpc.service;

import com.github.pagehelper.PageInfo;
import manage.gxuicpc.entity.UnContest;
import manage.gxuicpc.entity.UnContestPage;

public interface UnContestService {
    PageInfo<UnContest> getUnContestList(UnContestPage unContestPage);

    Long addContest(UnContest unContest);

    long deleteContest(Long id);
}
