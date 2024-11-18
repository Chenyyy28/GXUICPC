package manage.mail;

import manage.mail.util.MailUtil;
import manage.mail.work.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class MailApplication implements CommandLineRunner {
    @Autowired
    MailUtil mailUtil;
    @Autowired
    SendMail sendMail;

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendMail.send();
    }
}
