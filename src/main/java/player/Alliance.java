package player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Alliance {
    WHITE(-1),
    BLACK(1);

    private final int direction;
}
