package manage.gxuicpc.service.impl;

import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.common.RoleId;
import manage.gxuicpc.entity.*;
import manage.gxuicpc.mapper.ContestMapper;
import manage.gxuicpc.mapper.ProblemMapper;
import manage.gxuicpc.mapper.RankMapper;
import manage.gxuicpc.service.RankService;
import manage.gxuicpc.service.TimeService;
import manage.gxuicpc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RankServiceImpl implements RankService {
    @Autowired
    RankMapper rankMapper;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    ContestMapper contestMapper;
    @Autowired
    TimeService timeService;
    private static final String redisKey = RedisUtils.KEY + "rank";
    private static final String redisKey1 = RedisUtils.KEY + "rank:preparation";

    private static final String redisKey2 = RedisUtils.KEY + "rank:active";

    @Override
    @DeleteRedis(key = redisKey, id = false)
    public int addRankFactor(RankFactors rankFactors) {
        try {
            int cnt = rankMapper.checkExist(rankFactors);
            if (cnt != 0) {
                rankMapper.updateFactors(rankFactors);
            } else {
                rankMapper.addRankFactor(rankFactors);
            }
            return 1;
        } catch (Exception e) {
            log.error("发生错误：", e);
            return 0;
        }
    }

    @Override
    @QueryRedis(key = redisKey1, id = false, resType = List.class)
    public List<Rank> getPrepareRank() {
        List<Rank> ranks = problemMapper.getUser(RoleId.PREPARATION.getId());
        ranks.sort((o1, o2) -> {
            Integer s1 = o1.getRanking();
            Integer s2 = o2.getRanking();
            return s1.compareTo(s2);
        });
        return ranks;
    }

    @Override
    @QueryRedis(key = redisKey2, id = false, resType = List.class)
    public List<Rank> getActiveRank() {
        List<Rank> ranks = problemMapper.getUser(RoleId.ACTIVE.getId());
        ranks.sort((o1, o2) -> {
            Integer s1 = o1.getRanking();
            Integer s2 = o2.getRanking();
            return s1.compareTo(s2);
        });
        return ranks;
    }

    /**
     * 获取对应训练的排名
     * @param contestId
     * @return
     */
    @Override
    @QueryRedis(key = redisKey,id=false,page = true,resType = List.class)
    public List<RankInfo> getRankInfo(Long contestId) {
        List<RankInfo> ranks = rankMapper.getContestInfo(contestId);
        ranks.sort((o1, o2) -> {
            Integer s1 = o1.getRanking();
            Integer s2 = o2.getRanking();
            return s1.compareTo(s2);
        });
        return ranks;
    }

}
