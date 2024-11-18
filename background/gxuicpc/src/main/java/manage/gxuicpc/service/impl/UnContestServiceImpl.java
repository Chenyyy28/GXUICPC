package manage.gxuicpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.entity.UnContest;
import manage.gxuicpc.entity.UnContestPage;
import manage.gxuicpc.mapper.UnContestMapper;
import manage.gxuicpc.service.UnContestService;
import manage.gxuicpc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UnContestServiceImpl implements UnContestService {
    @Autowired
    UnContestMapper unContestMapper;
    private static final String redisKey = RedisUtils.KEY + "unContest_";

    @QueryRedis(key = redisKey, id = true,page = true, resType = PageInfo.class)
    @Override
    public PageInfo<UnContest> getUnContestList(UnContestPage unContestPage) {
        PageHelper.startPage(unContestPage.getCurrentPage(), unContestPage.getPageSize());
        List<UnContest> unContests = unContestMapper.getUnContestList(unContestPage);
        PageInfo pageInfo = new PageInfo<>(unContests);
        pageInfo.setTotal(unContestMapper.getUnContestList(unContestPage).size());
        return pageInfo;
    }

    @Override
    @DeleteRedis(key = redisKey, id = true)
    public Long addContest(UnContest unContest) {
        return unContestMapper.addContest(unContest);
    }

    @DeleteRedis(key = redisKey, id = true)
    @Override
    public long deleteContest(Long id) {
        try {
            unContestMapper.deleteContest(id);
            return 1;
        } catch (Exception e) {
            log.info("发生错误：{}", e);
            return 0;
        }
    }
}
