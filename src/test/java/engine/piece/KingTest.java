package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KingTest {

    King king;
    @Mock
    BoardService boardService;
    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        king = new King(0, Alliance.WHITE, boardService);
    }

    @Test
    void isInMoveRange() {
        when(boardService.getColumn(0))
                .thenReturn(0);
        when(boardService.getColumn(1))
                .thenReturn(1);

        assertThat(king.isInMoveRange(1))
                .isTrue();
    }

    @Test
    void isNotInMoveRange() {
        when(boardService.getColumn(0))
                .thenReturn(0);
        when(boardService.getColumn(4))
                .thenReturn(4);

        assertThat(king.isInMoveRange(4))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination())
                .thenReturn(9);

        assertThat(king.move(move))
                .isInstanceOf(King.class)
                .matches(king -> king.getPosition() == 9);
    }
}