package sample.springtddkiosk.spring.client.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MailSendClient {

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        log.info("메일 전송");
        throw new IllegalArgumentException("메일 전송 실패");
//        return true;
    }
}
