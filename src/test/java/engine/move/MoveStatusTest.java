package engine.move;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveStatusTest {

    @Test
    void isDone() {
        assertThat(MoveStatus.DONE.isDone())
                .isTrue();
    }

    @Test
    void isNotDone() {
        assertThat(MoveStatus.LEAVES_PLAYER_IN_CHECK.isDone())
                .isFalse();
        assertThat(MoveStatus.ILLEGAL.isDone())
                .isFalse();
    }
}