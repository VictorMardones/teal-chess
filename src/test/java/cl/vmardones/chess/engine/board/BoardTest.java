/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.piece.King;
import cl.vmardones.chess.engine.piece.Pawn;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardTest {

  Board.BoardBuilder builder;

  @Mock King whiteKing;

  @Mock King blackKing;

  @Mock Pawn enPassantPawn;

  @Mock Piece piece;
  @Mock Coordinate coordinate;

  @BeforeEach
  void setUp() {
    builder = Board.builder(whiteKing, blackKing);
  }

  @Test
  void contains() {
    when(piece.getPosition()).thenReturn(Coordinate.of("e2"));
    when(piece.getPieceType()).thenReturn(Piece.PieceType.BISHOP);

    var board = builder.piece(piece).build();

    assertThat(board.contains(Coordinate.of("e2"), Piece.PieceType.BISHOP)).isTrue();
  }

  @Test
  void containsNothing() {
    var board = builder.build();

    assertThat(board.containsNothing(coordinate)).isTrue();
  }

  @Test
  void nextTurnBuilder() {
    var board = builder.build();
    var nextTurnBoard = board.nextTurnBuilder().build();

    assertThat(board.getWhiteKing()).isEqualTo(nextTurnBoard.getWhiteKing());
    assertThat(board.getBlackKing()).isEqualTo(nextTurnBoard.getBlackKing());
  }

  @Test
  void piece() {
    when(piece.getPosition()).thenReturn(Coordinate.of("d7"));
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);

    var board = builder.piece(piece).build();

    assertThat(board.getWhitePieces()).containsOnlyOnce(piece);
  }

  @Test
  void withoutPiece() {
    var board = builder.withoutPiece(piece).build();

    assertThat(board.getWhitePieces()).isEmpty();
  }

  @Test
  void withoutPieceItHadBefore() {
    when(piece.getPosition()).thenReturn(Coordinate.of("d7"));
    when(piece.getAlliance()).thenReturn(Alliance.WHITE);

    var board = builder.piece(piece).build();
    var nextTurnBoard = board.nextTurnBuilder().withoutPiece(piece).build();

    assertThat(board.getWhitePieces()).containsOnlyOnce(piece);
    assertThat(nextTurnBoard.getWhitePieces()).isEmpty();
  }

  @Test
  void enPassantPawn() {
    var board = builder.enPassantPawn(enPassantPawn).build();

    assertThat(board.getEnPassantPawn()).isEqualTo(enPassantPawn);
  }

  @Test
  void noEnPassantPawnNextTurn() {
    var board = builder.enPassantPawn(enPassantPawn).build();
    var nextTurnBoard = board.nextTurnBuilder().build();

    assertThat(board.getEnPassantPawn()).isEqualTo(enPassantPawn);
    assertThat(nextTurnBoard.getEnPassantPawn()).isNull();
  }
}
