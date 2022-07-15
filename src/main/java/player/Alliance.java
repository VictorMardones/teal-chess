package player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Alliance {
    WHITE(-1),
    BLACK(1);

    @Getter
    private final int direction;
}
