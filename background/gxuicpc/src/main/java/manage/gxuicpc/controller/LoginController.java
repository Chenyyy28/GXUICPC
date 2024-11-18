package manage.gxuicpc.controller;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.service.UserService;
import manage.gxuicpc.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
@Slf4j
@Tag(name = "登录相关接口", description = "登录以及校验等操作")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "用户登录", description = "校验登录，以及签发token")
    @PostMapping
    Result login(@RequestBody User user) {
        boolean ok = userService.checkLogin(user);
        log.info("the user: {} want to login",user.getUsername());
        if (ok) {
            //获取用户id
            Long id = userService.getUserId(user);
            //生成通过jwt生成token
            String token = jwtTokenUtil.getToken(id);
            User user1 =  userService.getUserById(id);
            user1.setPassword("**********");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("token", token);
            hashMap.put("user",user1);
            JSONObject jsonObject = new JSONObject(hashMap);
            log.info("login successfully");
            //返回token
            return Result.success(jsonObject);
        } else {
            return Result.fail("登录失败");
        }
    }
}
