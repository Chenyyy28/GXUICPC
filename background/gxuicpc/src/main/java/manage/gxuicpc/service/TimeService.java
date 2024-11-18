package manage.gxuicpc.service;

import manage.gxuicpc.entity.TimeInterval;

import java.util.List;

public interface TimeService {
    int update(TimeInterval timeInterval);

    List<Long> getTime();
}
