package manage.gxuicpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class GxuicpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GxuicpcApplication.class, args);
    }

}
