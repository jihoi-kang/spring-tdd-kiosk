package sample.springtddkiosk.spring.api.service.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sample.springtddkiosk.spring.client.mail.MailSendClient;
import sample.springtddkiosk.spring.domain.history.mail.MailSendHistory;
import sample.springtddkiosk.spring.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일을 전송한다")
    @Test
    void sendMail() {
        // given
        given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
            .willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        Assertions.assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}