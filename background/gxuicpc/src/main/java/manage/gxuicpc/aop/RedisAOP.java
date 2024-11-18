package manage.gxuicpc.aop;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.utils.RedisUtils;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Aspect
@Slf4j
public class RedisAOP {
    @Pointcut("@annotation(manage.gxuicpc.annotation.QueryRedis)")
    public void queryRedis() {
    }

    @Pointcut("@annotation(manage.gxuicpc.annotation.DeleteRedis)")
    public void updateRedis() {
    }

    @Autowired
    RedisUtils redisUtils;

    @Around("@annotation(queryRedis)")
    public Object aroundQuery(ProceedingJoinPoint proceedingJoinPoint, QueryRedis queryRedis) throws Throwable {
        String key = queryRedis.key();
        if (queryRedis.id() == true) {
            key = key + ThreadLocalUtil.getCurrentUser().toString();
        }
        if (queryRedis.page()) {
            key = addKey(key, proceedingJoinPoint.getArgs()[0]);
        }
        Object res;
        Object value = redisUtils.get(key);
        if (value == null || value == "") {
            res = proceedingJoinPoint.proceed();
            value = JSON.toJSONString(res);
            redisUtils.set(key, value);
        } else {
            value = redisUtils.get(key);
            res = JSON.parseObject((String) value, queryRedis.resType());
        }
        return res;
    }

    private String addKey(String key, Object arg) {
        return key + ":" + arg.toString();
    }

    @Around("@annotation(deleteRedis)")
    public Object aroundDelete(ProceedingJoinPoint proceedingJoinPoint, DeleteRedis deleteRedis) throws Throwable {
        String key = deleteRedis.key();
        if (deleteRedis.id() == true) {
            key = key + ThreadLocalUtil.getCurrentUser().toString();
        }
        Set<String> s = redisUtils.keys(key + ":*");
        redisUtils.deleteKeys(s);
        redisUtils.delete(key);
        return proceedingJoinPoint.proceed();
    }
}
