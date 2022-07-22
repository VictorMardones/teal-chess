package engine.piece;

import engine.board.BoardService;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BishopTest {

    @Mock
    BoardService boardService;

    @Test
    void diagonalMove() {
        when(boardService.sameColor(0, 9))
                .thenReturn(true);

        var bishop = new Bishop(0, Alliance.BLACK, boardService);
        assertThat(bishop.isInMoveRange(9))
                .isTrue();
    }

    @Test
    void illegalMove() {
        when(boardService.sameColor(0, 1))
                .thenReturn(false);

        var bishop = new Bishop(0, Alliance.WHITE, boardService);
        assertThat(bishop.isInMoveRange(1))
                .isFalse();
    }
}