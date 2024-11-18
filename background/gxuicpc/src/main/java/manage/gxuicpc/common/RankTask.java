package manage.gxuicpc.common;

import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.entity.Contest;
import manage.gxuicpc.entity.Rank;
import manage.gxuicpc.entity.RankFactors;
import manage.gxuicpc.mapper.ContestMapper;
import manage.gxuicpc.mapper.ProblemMapper;
import manage.gxuicpc.mapper.RankMapper;
import manage.gxuicpc.mapper.UserMapper;
import manage.gxuicpc.service.ProblemService;
import manage.gxuicpc.service.TimeService;
import manage.gxuicpc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@EnableScheduling
@Slf4j
public class RankTask {
    @Autowired
    TimeService timeService;
    @Autowired
    ProblemService problemService;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    ContestMapper contestMapper;
    @Autowired
    RankMapper rankMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    UserMapper userMapper;
    private static final String redisKey = RedisUtils.KEY + "rank";

    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(cron = "0/10 * * * * ? ")
    public void updateRank() {
        getRank(RoleId.ACTIVE.getId());
        getRank(RoleId.PREPARATION.getId());
        clearRedis();
    }

    private void clearRedis() {
        Set<String> s = redisUtils.keys("*");
        redisUtils.deleteKeys(s);
    }

    @DeleteRedis(key = redisKey, id = false)
    public void getRank(Integer roleId) {
        log.info("开始更新榜单");
        List<Long> times = timeService.getTime();
        if (times == null) {
            log.error("榜单更新错误！！！");
            return;
        }
        Long start = times.get(0);
        Long end = times.get(1);
        //找到所有需要排名的
        List<Rank> ranks = problemMapper.getUser(roleId);
        //找到所有时间区间内成员可参加的比赛
        List<Contest> contests = contestMapper.getContestByTime(roleId, start, end);
        //对每个比赛都找对应的总人数
        Map<Long, Integer> maxPeo = new HashMap<>();
        contests.forEach(contest -> {
            int res = contestMapper.getPeople(contest.getId());
            maxPeo.put(contest.getId(), res);
        });
        //每个人对每个比赛进行求和
        ranks.forEach(rank -> {
            rank.setScore(0);
            contests.forEach(contest -> {
                RankFactors rankFactors = rankMapper.getFactors(rank.getId(), contest.getId());
                Integer score = cal(rankFactors, maxPeo.get(contest.getId()), contest.getWeight());
                rank.setScore(rank.getScore() + score);
            });
            rankMapper.updateRank(rank.getId(), rank.getScore());
        });
        ranks.sort((Rank p1, Rank p2) -> {
            Integer s1 = p1.getScore();
            Integer s2 = p2.getScore();
            return s2.compareTo(s1);
        });

        for (int i = 0; i < ranks.size(); i++) {
            userMapper.updateRank(ranks.get(i).getId(), i + 1);
        }
    }

    private Integer cal(RankFactors rankFactors, Integer cnt, int weight) {
        if (rankFactors == null) {
            return 0;
        }
        int raceNum = rankFactors.getRaceNum();
        int supplement = rankFactors.getSupplement();
        int rank = rankFactors.getRanking();
        return (raceNum * 100 + supplement * 75 + (cnt - rank + 1) * 25) * weight;
    }
}
