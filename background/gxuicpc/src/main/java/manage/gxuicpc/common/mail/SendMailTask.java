package manage.gxuicpc.common.mail;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@AllArgsConstructor
@Slf4j
public class SendMailTask implements Runnable {
    private MailUtil mailUtil;
    private String to;
    private String subject;
    private String content;

    public SendMailTask(MailUtil mailUtil, String to, String subject, String content) {
        this.mailUtil = mailUtil;
        this.subject = subject;
        this.to = to;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            boolean ok = mailUtil.sendGeneralEmail(subject, content, to);
            if (ok) {
                log.info("send the email which subject is {} to {} succeed", subject, to);
            } else {
                log.warn("send the email which subject is {} to {} failed", subject, to);
            }
        } catch (Exception e) {
            log.error("send the email which subject is {} to {} error", subject, to);
        }
    }
}
