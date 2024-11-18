package manage.gxuicpc.common.mail;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
@Setter
public class SendMail {
    @Autowired
    private MailUtil mailUtil;
    private List<String> to;
    private String subject;
    private String content;

    public SendMail(List<String> to, String subject, String content) {
        this.subject = subject;
        this.to = to;
        this.content = content;
    }

    public SendMail build(List<String> to, String subject, String content) {
        this.subject = subject;
        this.to = to;
        this.content = content;
        return this;
    }

    public void send() {
        to.forEach(to -> {
            SendMailTask sendMailTask = new SendMailTask(mailUtil, to, subject, content);
//            SendMailTask sendMailTask = new SendMailTask();

            Thread thread = new Thread(sendMailTask);
            thread.start();
        });
    }
}
