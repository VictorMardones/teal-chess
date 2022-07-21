package engine.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void toText() {
        var board = Board.createStandardBoard();

        assertThat(board.toText())
                .contains("r  n  b  q  k  b  n  r")
                .contains("p  p  p  p  p  p  p  p")
                .contains("-  -  -  -  -  -  -  -")
                .contains("P  P  P  P  P  P  P  P")
                .contains("R  N  B  Q  K  B  N  R");
    }
}