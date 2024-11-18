package manage.gxuicpc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class JwtTokenUtil {
    // 生成token
    // Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码
    @Value("${jwt.timeout}")
    private int timeout;
    @Value("${jwt.secret}")
    private String secret;
    MacAlgorithm alg = Jwts.SIG.HS384; //or HS384 or HS256
    SecretKey key = alg.key().build();
//    Date date = new Date(new Date().getTime() + 30 * 60L * 1000L);

    public String getToken(Long id) {
        Map<String, Object> inputClaims = new HashMap<>();
        inputClaims.put("userId", id);
        Date date = new Date(new Date().getTime() + 30 * 60L * 1000L);
//        log.info("签发时间:{}，过期时间：{}",new Date(),date);

        // 签名方式或者加密方式，二选一
        String token = Jwts.builder()
                .claims(inputClaims)
                .issuedAt(new Date()) // 设置签发时间为当前时间。
                .expiration(date)  // 设置 JWT 的过期时间为 30 分钟后。
                .id(UUID.randomUUID().toString()) // 设置一个随机生成的唯一标识符。
                .signWith(key) // 签名方式
                .compact(); // 生成 JWT 并压缩为一个字符串形式
//        log.info("签发的key:{}", key);
        return token;
    }

    public Long parseJWT(String token) {
        Long id;
//        log.info("验证的key:{}", key);
        try {

            Claims claims = Jwts.parser()
                    .verifyWith(key) // 指定用于验证的密钥。
                    .build()
                    .parseSignedClaims(token) // 签名方式
                    .getPayload(); // 获取解密后的声明（claims）内容。
            id = Long.valueOf((Integer)claims.get("userId"));
        } catch (Exception e) {
//            log.info("发生的错误：{}", e);
            id = -1L;
        }
        return id;
    }
}
