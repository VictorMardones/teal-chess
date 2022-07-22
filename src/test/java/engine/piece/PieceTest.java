package engine.piece;

import engine.board.Tile;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PieceTest {

    @Spy
    static Piece piece;
    @Mock
    static Tile destinationTile;
    @Mock
    static Piece destinationPiece;

    @Test
    void isWhite() {
        when(piece.getAlliance())
                .thenReturn(Alliance.WHITE);

        assertThat(piece.isWhite())
                .isTrue();
    }

    @Test
    void isNotWhite() {
        when(piece.getAlliance())
                .thenReturn(Alliance.BLACK);

        assertThat(piece.isWhite())
                .isFalse();
    }

    @Test
    void isBlack() {
        when(piece.getAlliance())
                .thenReturn(Alliance.BLACK);

        assertThat(piece.isBlack())
                .isTrue();
    }

    @Test
    void isNotBlack() {
        when(piece.getAlliance())
                .thenReturn(Alliance.WHITE);

        assertThat(piece.isBlack())
                .isFalse();
    }

    @Test
    void isEmptyAccesible() {
        when(destinationTile.getPiece())
                .thenReturn(Optional.empty());

        assertThat(piece.isAccessible(piece, destinationTile))
                .isTrue();
    }

    @Test
    void isEnemyAccessible() {
        when(destinationTile.getPiece())
                .thenReturn(Optional.of(destinationPiece));
        when(piece.getAlliance())
                .thenReturn(Alliance.BLACK);
        when(destinationPiece.getAlliance())
                .thenReturn(Alliance.WHITE);

        assertThat(piece.isAccessible(piece, destinationTile))
                .isTrue();
    }

    @Test
    void isNotAccesible() {
        when(destinationTile.getPiece())
                .thenReturn(Optional.of(destinationPiece));
        when(piece.getAlliance())
                .thenReturn(Alliance.BLACK);
        when(destinationPiece.getAlliance())
                .thenReturn(Alliance.BLACK);

        assertThat(piece.isAccessible(piece, destinationTile))
                .isFalse();
    }
}