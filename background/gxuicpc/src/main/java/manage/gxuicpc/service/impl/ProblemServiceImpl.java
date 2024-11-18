package manage.gxuicpc.service.impl;

import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.entity.Problem;
import manage.gxuicpc.mapper.ProblemMapper;
import manage.gxuicpc.service.ProblemService;
import manage.gxuicpc.utils.RedisUtils;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;

    private static final String redisKey = RedisUtils.KEY + "Problem:";

    @Override
    @QueryRedis(key = redisKey, id = true, resType = Problem.class)
    public Problem getProblemAmount() {
        Long id = ThreadLocalUtil.getCurrentUser();
        return problemMapper.getProblemAmount(id);
    }

    @Override
    public Integer addUser(Long id) {
        return problemMapper.addUser(id);
    }

    @Override
    public int deleteById(Long id) {
        return problemMapper.deleteUser(id);
    }
}
