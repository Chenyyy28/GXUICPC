package manage.mail.work;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manage.mail.util.MailUtil;

@AllArgsConstructor
@Slf4j
@NoArgsConstructor
public class SendMailTask implements Runnable {
    private MailUtil mailUtil;
    private String to;
    private String subject;
    private String content;


    @Override
    public void run() {
        boolean ok = mailUtil.sendGeneralEmail(subject, content, to);
        log.info("{}", subject);
    }
}
