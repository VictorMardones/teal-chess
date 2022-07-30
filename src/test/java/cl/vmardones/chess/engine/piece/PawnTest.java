/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.vector.Diagonal;
import cl.vmardones.chess.engine.piece.vector.Horizontal;
import cl.vmardones.chess.engine.piece.vector.Jump;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
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
        assertThat(pawn.getMoveOffsets()).contains(Vertical.UP.getVector());
        assertThat(pawn.getMoveOffsets()).contains(Jump.UP.getVector());
        assertThat(pawn.getMoveOffsets()).contains(Diagonal.UP_RIGHT.getVector());
    }

    @Test
    void blackMoves() {
        final var blackPawn = new Pawn(coordinate, Alliance.BLACK);

        assertThat(blackPawn.getMoveOffsets()).contains(Vertical.DOWN.getVector());
        assertThat(blackPawn.getMoveOffsets()).contains(Jump.DOWN.getVector());
        assertThat(blackPawn.getMoveOffsets()).contains(Diagonal.DOWN_RIGHT.getVector());
    }

    @Test
    void illegalMove() {
        assertThat(pawn.getMoveOffsets()).doesNotContain(Horizontal.RIGHT.getVector());
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
