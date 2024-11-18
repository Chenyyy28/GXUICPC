package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.Problem;
import manage.gxuicpc.service.ProblemService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
@Slf4j
@Tag(name = "获取刷题量接口")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @GetMapping
    @Operation(summary = "获取刷题量")
    public Result<Problem> getProblem() {
        log.info("the user: {} want to get the problem_pass", ThreadLocalUtil.getCurrentUser());
        return Result.success(problemService.getProblemAmount());
    }
}
