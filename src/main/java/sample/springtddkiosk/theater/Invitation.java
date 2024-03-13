package sample.springtddkiosk.theater;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Invitation {
    private boolean used = false;

    public void use() {
        if (used) {
            throw new IllegalArgumentException("이미 사용된 초대장입니다");
        }
        this.used = true;
    }

    @Builder
    private Invitation(boolean used) {
        this.used = used;
    }

    public static Invitation create() {
        return Invitation.builder()
            .used(false)
            .build();
    }

}
