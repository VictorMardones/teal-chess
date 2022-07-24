/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PawnTest {

    Pawn pawn;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        pawn = new Pawn(coordinate, Alliance.WHITE);
    }

    @Test
    void constructor() {
        assertThat(new Pawn(coordinate, Alliance.BLACK)).matches(Pawn::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(pawn.getPieceType()).isEqualTo(Piece.PieceType.PAWN);
    }

    @Test
    void whiteMoves() {
        assertThat(pawn.getMoveOffsets().contains(Vector.Vertical.UP.getVector()))
                .isTrue();
        assertThat(pawn.getMoveOffsets().contains(Vector.Jump.UP.getVector())).isTrue();
        assertThat(pawn.getMoveOffsets().contains(Vector.Diagonal.UP_RIGHT.getVector()))
                .isTrue();
    }

    @Test
    void blackMoves() {
        final var blackPawn = new Pawn(coordinate, Alliance.BLACK);

        assertThat(blackPawn.getMoveOffsets().contains(Vector.Vertical.DOWN.getVector()))
                .isTrue();
        assertThat(blackPawn.getMoveOffsets().contains(Vector.Jump.DOWN.getVector()))
                .isTrue();
        assertThat(blackPawn.getMoveOffsets().contains(Vector.Diagonal.DOWN_RIGHT.getVector()))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(pawn.getMoveOffsets().contains(Vector.Horizontal.RIGHT.getVector()))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(pawn.move(move))
                .isInstanceOf(Pawn.class)
                .matches(pawn -> pawn.getPosition().equals(destination))
                .matches(pawn -> !pawn.isFirstMove());
    }
}
