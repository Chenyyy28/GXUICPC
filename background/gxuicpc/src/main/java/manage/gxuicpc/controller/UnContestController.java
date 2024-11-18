package manage.gxuicpc.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.UnContest;
import manage.gxuicpc.entity.UnContestPage;
import manage.gxuicpc.service.UnContestService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uncontested")
@Slf4j
@Tag(name = "获取补题列表相关接口")
public class UnContestController {
    @Autowired
    UnContestService unContestService;

    @GetMapping
    @Operation(summary = "(分页)查询补题列表")
    public Result<PageInfo<UnContest>> getUnContest(UnContestPage unContestPage) {
        log.info("the user: {} want to get the un_contest by:{}", ThreadLocalUtil.getCurrentUser(), unContestPage);
        PageInfo<UnContest> pageInfo = unContestService.getUnContestList(unContestPage);
        return Result.success(pageInfo);
    }

    @PostMapping
    @Operation(summary = "添加补题")
    public Result<Long> addUnContest(@RequestBody UnContest unContest) {
        log.info("the user: {} want to add the un_contest: {}", ThreadLocalUtil.getCurrentUser(), unContest);
        unContest.setUserId(ThreadLocalUtil.getCurrentUser());
        long ok = unContestService.addContest(unContest);
        if (ok != 0)
            return Result.success(ok);
        else return Result.fail(ErrorMessage.ADD_FAILED.getCode(), ErrorMessage.ADD_FAILED.getMsg());
    }

    @DeleteMapping
    @Operation(summary = "删除补题")
    public Result<Long> deleteContest(Long id) {
        log.info("the user: {} want to delete the un_contest: {}", ThreadLocalUtil.getCurrentUser(), id);
        long ok = unContestService.deleteContest(id);
        if (ok != 0)
            return Result.success(ok);
        else return Result.fail(ErrorMessage.DELETE_FAILED.getCode(), ErrorMessage.DELETE_FAILED.getMsg());
    }
}
