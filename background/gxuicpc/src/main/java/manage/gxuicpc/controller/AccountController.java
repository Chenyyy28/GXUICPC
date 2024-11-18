package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.vo.AccountVO;
import manage.gxuicpc.service.AccountService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "刷题账号信息相关接口")
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    @Operation(summary = "获取刷题账号")
    public Result getAccount() {
        log.info("The user: {} get the accounts", ThreadLocalUtil.getCurrentUser());
        AccountVO accountVO = accountService.getAccount();
        return Result.success(accountVO);
    }

    @PostMapping
    @Operation(summary = "修改刷题账号")
    public Result updateAccount(@RequestBody AccountVO accountVO) {
        log.info("The user: {} update the accounts to {}", ThreadLocalUtil.getCurrentUser(),accountVO);
        int ok = accountService.updateAccount(accountVO);
        if (ok == 1) {
            return Result.success(ok);
        } else {
            return Result.fail(ErrorMessage.UPDATE_FAILED.getCode(), ErrorMessage.UPDATE_FAILED.getMsg());
        }
    }

}
