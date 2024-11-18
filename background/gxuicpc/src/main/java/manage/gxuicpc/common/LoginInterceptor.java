package manage.gxuicpc.common;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.utils.JwtTokenUtil;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头的token
        String token = request.getHeader("user-token");
        //解析token
        Long id = jwtTokenUtil.parseJWT(token);
        //token解析失败
        if (id == -1) {
//            log.info("token解析失败");
            response.setStatus(ErrorMessage.LOGIN_EXPIRED.getCode());
            String result = JSON.toJSONString(new Result<>().fail(ErrorMessage.LOGIN_EXPIRED.getCode(), ErrorMessage.LOGIN_EXPIRED.getMsg()));
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result);
            return false;
        }
        ThreadLocalUtil.addCurrentUser(id);
        return true;
    }
}
