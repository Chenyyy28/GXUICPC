package manage.mail.work;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manage.mail.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
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

    public void send() {
        log.info(mailUtil.toString());
    }
}
