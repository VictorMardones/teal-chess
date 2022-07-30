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
import cl.vmardones.chess.engine.piece.vector.LShaped;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueenTest {

    Queen queen;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        queen = new Queen(coordinate, Alliance.BLACK);
    }

    @Test
    void constructor() {
        assertThat(new Queen(coordinate, Alliance.BLACK)).matches(Queen::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(queen.getPieceType()).isEqualTo(Piece.PieceType.QUEEN);
    }

    @Test
    void diagonalMove() {
        assertThat(queen.getMoveVectors()).contains(Diagonal.DOWN_RIGHT.getVector());
    }

    @Test
    void horizontalMove() {
        assertThat(queen.getMoveVectors()).contains(Horizontal.LEFT.getVector());
    }

    @Test
    void verticalMove() {
        assertThat(queen.getMoveVectors()).contains(Vertical.UP.getVector());
    }

    @Test
    void illegalMove() {
        assertThat(queen.getMoveVectors()).doesNotContain(LShaped.UP_UP_LEFT.getVector());
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(queen.move(move))
                .isInstanceOf(Queen.class)
                .matches(queen -> queen.getPosition().equals(destination))
                .matches(queen -> !queen.isFirstMove());
    }
}
