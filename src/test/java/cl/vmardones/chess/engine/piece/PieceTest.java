/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.board.Tile;
import cl.vmardones.chess.engine.player.Alliance;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PieceTest {

  @Spy Piece piece;

  @Mock Tile destinationTile;

  @Mock Piece destinationPiece;

  @Test
  void isWhite() {
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);

    assertThat(piece.isWhite()).isTrue();
  }

  @Test
  void isNotWhite() {
    when(piece.getAlliance()).thenReturn(Alliance.BLACK);

    assertThat(piece.isWhite()).isFalse();
  }

  @Test
  void isBlack() {
    when(piece.getAlliance()).thenReturn(Alliance.BLACK);

    assertThat(piece.isBlack()).isTrue();
  }

  @Test
  void isNotBlack() {
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);

    assertThat(piece.isBlack()).isFalse();
  }

  @Test
  void isAllyOf() {
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);
    when(destinationPiece.getAlliance()).thenReturn(Alliance.WHITE);

    assertThat(piece.isAllyOf(destinationPiece)).isTrue();
    assertThat(piece.isEnemyOf(destinationPiece)).isFalse();
  }

  @Test
  void isEnemyOf() {
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);
    when(destinationPiece.getAlliance()).thenReturn(Alliance.BLACK);

    assertThat(piece.isAllyOf(destinationPiece)).isFalse();
    assertThat(piece.isEnemyOf(destinationPiece)).isTrue();
  }

  @Test
  void isEmptyAccesible() {
    when(destinationTile.getPiece()).thenReturn(Optional.empty());

    assertThat(piece.canAccess(destinationTile)).isTrue();
  }

  @Test
  void isEnemyAccessible() {
    when(piece.getAlliance()).thenReturn(Alliance.BLACK);
    when(destinationTile.getPiece()).thenReturn(Optional.of(destinationPiece));
    when(destinationPiece.getAlliance()).thenReturn(Alliance.WHITE);

    assertThat(piece.canAccess(destinationTile)).isTrue();
  }

  @Test
  void isNotAccesible() {
    when(piece.getAlliance()).thenReturn(Alliance.BLACK);
    when(destinationTile.getPiece()).thenReturn(Optional.of(destinationPiece));
    when(destinationPiece.getAlliance()).thenReturn(Alliance.BLACK);

    assertThat(piece.canAccess(destinationTile)).isFalse();
  }
}
