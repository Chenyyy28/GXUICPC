package manage.gxuicpc.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.Contest;
import manage.gxuicpc.entity.ContestPage;
import manage.gxuicpc.entity.dto.ContestNoticeDTO;
import manage.gxuicpc.entity.vo.ContestVO;
import manage.gxuicpc.service.ContestService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contest")
@Tag(name = "训练相关接口")
@Slf4j
public class ContestController {

    @Autowired
    ContestService contestService;

    @GetMapping
    @Operation(summary = "获取训练列表")
    public Result getContest(ContestPage contestPage) {
        log.info("The user: {} will get the contests", ThreadLocalUtil.getCurrentUser());
        PageInfo<List<ContestVO>> pageInfo = contestService.getContests(contestPage);
        return Result.success(pageInfo);
    }

    @PostMapping
    @Operation(summary = "添加训练")
    public Result<Long> addContest(@RequestBody Contest contest) {
        log.info("The user: {} want to add the contest: {}", ThreadLocalUtil.getCurrentUser(), contest);
        long ok = contestService.addContest(contest);
        if (ok != 0)
            return Result.success(ok);
        else return Result.fail(ErrorMessage.ADD_FAILED.getCode(), ErrorMessage.ADD_FAILED.getMsg());
    }

    @PutMapping
    @Operation(summary = "修改训练")
    public Result<Long> updateContest(@RequestBody Contest contest) {
        log.info("The user: {} want to update the contest to: {}", ThreadLocalUtil.getCurrentUser(), contest);
        long ok = contestService.updateContest(contest);
        if (ok != 0)
            return Result.success(ok);
        else return Result.fail(ErrorMessage.UPDATE_FAILED.getCode(), ErrorMessage.UPDATE_FAILED.getMsg());
    }

    @DeleteMapping
    @Operation(summary = "删除训练")
    public Result<Long> deleteContest(Long id) {
        log.info("The user: {} will delete the contestId: {}", ThreadLocalUtil.getCurrentUser(), id);
        long ok = contestService.deleteContest(id);
        if (ok != 0)
            return Result.success(ok);
        else return Result.fail(ErrorMessage.DELETE_FAILED.getCode(), ErrorMessage.DELETE_FAILED.getMsg());
    }

    @PostMapping("/notice")
    @Operation(summary = "一键发送训练提醒邮件")
    public Result notice(@RequestBody ContestNoticeDTO contestNoticeDTO) {
        log.info("the user: {} will send the email of contest: {}", ThreadLocalUtil.getCurrentUser(), contestNoticeDTO.getContestId());
        contestService.sendNoticeEmail(contestNoticeDTO);
        return Result.success(null);
    }

}
