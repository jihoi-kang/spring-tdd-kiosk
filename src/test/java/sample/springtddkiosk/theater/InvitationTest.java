package sample.springtddkiosk.theater;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvitationTest {

    @DisplayName("초대장을 초기화하면 사용여부는 거짓이다")
    @Test
    void init() {
        // given
        Invitation invitation = Invitation.create();

        // when

        // then
        Assertions.assertThat(invitation.isUsed()).isFalse();
    }

    @DisplayName("초대장을 사용하면 사용여부는 참이다")
    @Test
    void use() {
        // given
        Invitation invitation = Invitation.create();

        // when
        invitation.use();

        // then
        Assertions.assertThat(invitation.isUsed()).isTrue();
    }

    @DisplayName("사용한 초대장을 또 사용하려고 하면 에러가 발생한다")
    @Test
    void useWhenAlreadyUsed() {
        // given
        Invitation invitation = Invitation.builder().used(true).build();

        // when & then
        assertThatThrownBy(invitation::use)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 사용된 초대장입니다");
    }

}