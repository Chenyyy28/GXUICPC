package manage.gxuicpc.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.ErrorMessage;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.common.excel.UserListener;
import manage.gxuicpc.entity.UserPage;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.entity.dto.PwdDTO;
import manage.gxuicpc.entity.dto.UserDTO;
import manage.gxuicpc.entity.dto.UserExcelDTO;
import manage.gxuicpc.entity.vo.UserVO;
import manage.gxuicpc.service.UserService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "用户操作相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserListener userListener;

    @Operation(summary = "获取用户列表")
    @GetMapping
    public Result<PageInfo<UserVO>> getUsers(UserPage userPage) {
        log.info("the user: {} get the list of user", ThreadLocalUtil.getCurrentUser());
        PageInfo<UserVO> userVOS = userService.getUserPage(userPage);
        return Result.success(userVOS);
    }

    @Operation(summary = "添加新用户")
    @PostMapping
    public Result addUser(@RequestBody User user) {
        log.info("the user: {} want to add a user: {}", ThreadLocalUtil.getCurrentUser(), user);
        Long id = userService.addUser(user);
        return Result.success(id);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping
    public Result deleteById(Long id) {
        log.info("the user: {} want to delete the list of user: {}", ThreadLocalUtil.getCurrentUser(), id);
        int ok = userService.deleteById(id);
        return Result.success(ok);
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public Result updateById(@RequestBody UserDTO userDTO) {
        log.info("the user: {} want to update user: {} to :{}", ThreadLocalUtil.getCurrentUser(), userDTO.getId(), userDTO);
        int ok = userService.updateById(userDTO);
        return Result.success(ok);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result updatePwd(@RequestBody PwdDTO pwdDTO) {
        int status = userService.updatePwd(pwdDTO.getPrev(), pwdDTO.getNow());
        log.info("the user: {} want to update password", ThreadLocalUtil.getCurrentUser());
        if (status == -1) {
            return Result.fail(ErrorMessage.UPDATE_PWD_FAILED.getCode(), ErrorMessage.UPDATE_PWD_FAILED.getMsg());
        }
        return Result.success(null);
    }

    @Operation(summary = "上传excel文件导入数据")
    @PostMapping("/upload")
    public Result upload(@RequestParam("user-data") MultipartFile multipartFile) throws IOException {
        EasyExcel.read(multipartFile.getInputStream(), UserExcelDTO.class,userListener).sheet().doRead();
        return Result.success(null);
    }
}
