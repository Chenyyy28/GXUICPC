package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.Rank;
import manage.gxuicpc.entity.RankFactors;
import manage.gxuicpc.entity.RankInfo;
import manage.gxuicpc.service.RankService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rank")
@Tag(name = "排名接口")
@Slf4j
public class RankController {
    @Autowired
    RankService rankService;

    @PostMapping
    @Operation(summary = "添加用户训练信息")
    public Result addRankFactor(@RequestBody RankFactors rankFactors) {
        log.info("the user: {} want to add the rank_factors: {}", ThreadLocalUtil.getCurrentUser(),rankFactors);
        int ok = rankService.addRankFactor(rankFactors);
        if (ok == 1) {
            return Result.success(1);
        } else {
            return Result.fail(ErrorMessage.ADD_FAILED.getCode(), ErrorMessage.ADD_FAILED.getMsg());
        }
    }

    @GetMapping
    @Operation(summary = "获取排名信息")
    public Result getRank(String role) {
        List<Rank> ranks = new ArrayList<>();
        if (StringUtils.equals(role, "prepare")) {
            log.info("the user: {} want to get the prepare_rank", ThreadLocalUtil.getCurrentUser());
            ranks = rankService.getPrepareRank();
        } else {
            log.info("the user: {} want to get the active_rank", ThreadLocalUtil.getCurrentUser());
            ranks = rankService.getActiveRank();
        }
        if (ranks != null) {
            return Result.success(ranks);
        } else {
            return Result.fail(ErrorMessage.ADD_FAILED.getCode(), ErrorMessage.ADD_FAILED.getMsg());
        }
    }

    @GetMapping("/info")
    public Result getContestRank(Long contestId){
        log.info("the user: {} want to get the information of contest: {} ", ThreadLocalUtil.getCurrentUser(),contestId);
        List<RankInfo> ranks = rankService.getRankInfo(contestId);
        return Result.success(ranks);
    }
}
