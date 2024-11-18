package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.mail.SendMail;
import manage.gxuicpc.utils.MailUtil;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("hello")
@Slf4j
@Tag(name = "测试接口", description = "发送hello")
public class HelloController {
    @Operation(summary = "测试网络", description = "返回hello")
    @GetMapping
    String hello() {
        return "Hello Gxu ICPC";
    }
}
