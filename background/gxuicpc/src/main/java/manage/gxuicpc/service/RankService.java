package manage.gxuicpc.service;

import manage.gxuicpc.entity.Problem;
import manage.gxuicpc.entity.Rank;
import manage.gxuicpc.entity.RankFactors;
import manage.gxuicpc.entity.RankInfo;

import java.util.List;

public interface RankService {
    int addRankFactor(RankFactors rankFactors);

    List<Rank> getPrepareRank();

    List<Rank> getActiveRank();

    List<RankInfo> getRankInfo(Long contestId);
}
