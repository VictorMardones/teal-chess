package engine.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardTest {

    Board board = Board.createStandardBoard();

    @Test
    void toText() {
        assertThat(board.toText())
                .contains("r  n  b  q  k  b  n  r")
                .contains("p  p  p  p  p  p  p  p")
                .contains("-  -  -  -  -  -  -  -")
                .contains("P  P  P  P  P  P  P  P")
                .contains("R  N  B  Q  K  B  N  R");
    }
}
