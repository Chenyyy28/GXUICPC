package manage.gxuicpc.service.impl;

import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.entity.TimeInterval;
import manage.gxuicpc.mapper.TimeMapper;
import manage.gxuicpc.service.TimeService;
import manage.gxuicpc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {
    @Autowired
    TimeMapper timeMapper;
    private static final String redisKey = RedisUtils.KEY + "time";

    @Override
    @DeleteRedis(key = redisKey, id = false)
    public int update(TimeInterval timeInterval) {
        return timeMapper.update(timeInterval);
    }

    @Override
    @QueryRedis(key = redisKey, id = false, resType = List.class)
    public List<Long> getTime() {
        TimeInterval timeInterval = timeMapper.getTime();
        List<Long> times = new ArrayList<>(2);
        times.add(timeInterval.getStartTime());
        times.add(timeInterval.getEndTime());
        return times;
    }
}
