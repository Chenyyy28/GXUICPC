package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.TimeInterval;
import manage.gxuicpc.service.TimeService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time")
@Tag(name = "排名时间区间")
@Slf4j
public class TimeController {
    @Autowired
    TimeService timeService;

    @PutMapping
    @Operation(summary = "修改时间")
    public Result update(@RequestBody List<Long> time) {
        log.info("the user: {} will update the time_interval to: {}", ThreadLocalUtil.getCurrentUser(),time);
        TimeInterval timeInterval = new TimeInterval(time.get(0), time.get(1));
        int ok = timeService.update(timeInterval);
        if (ok == 1) {
            return Result.success(1);
        } else {
            return Result.fail(ErrorMessage.UPDATE_FAILED.getCode(), ErrorMessage.UPDATE_FAILED.getMsg());
        }
    }

    @GetMapping
    @Operation(summary = "获取时间")
    public Result getTime() {
        log.info("the user: {} will get the time_interval", ThreadLocalUtil.getCurrentUser());
        List<Long> times = timeService.getTime();
        if (times != null)
            return Result.success(times);
        else {
            return Result.fail("失败！！！");
        }
    }
}
