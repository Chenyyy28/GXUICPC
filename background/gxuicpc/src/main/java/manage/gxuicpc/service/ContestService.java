package manage.gxuicpc.service;

import com.github.pagehelper.PageInfo;
import manage.gxuicpc.entity.Contest;
import manage.gxuicpc.entity.ContestPage;
import manage.gxuicpc.entity.dto.ContestNoticeDTO;
import manage.gxuicpc.entity.vo.ContestVO;

import java.util.List;

public interface ContestService {
    PageInfo<List<ContestVO>> getContests(ContestPage contestPage);

    long addContest(Contest contest);

    long updateContest(Contest contest);

    long deleteContest(Long id);

    int sendNoticeEmail(ContestNoticeDTO contestNoticeDTO);
}
