package engine.piece;

import engine.board.BoardService;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QueenTest {

    @Mock
    BoardService boardService;

    @ParameterizedTest
    // diagonal, horizontal, vertical
    @ValueSource(ints = {63, 7, 56})
    void legalMoves(int destination) {
        var queen = new Queen(0, Alliance.BLACK, boardService);
        assertThat(queen.isInMoveRange(destination))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE, boardService);
        assertThat(queen.isInMoveRange(12))
                .isFalse();
    }
}