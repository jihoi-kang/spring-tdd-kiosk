package sample.springtddkiosk.theater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketStatus {
    BEFORE_ENTER("입장전"),
    COMPLETE_ENTER("입장후");

    private final String text;
}
