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
class RookTest {

    @Mock
    BoardService boardService;

    @ParameterizedTest
    // horizontal, vertical
    @ValueSource(ints = {7, 56})
    void legalMoves(int destination) {
        var rook = new Rook(0, Alliance.BLACK, boardService);
        assertThat(rook.isInMoveRange(destination))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var rook = new Rook(0, Alliance.BLACK, boardService);
        assertThat(rook.isInMoveRange(9))
                .isFalse();
    }
}