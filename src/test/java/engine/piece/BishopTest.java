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
class BishopTest {

    Bishop bishop;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        bishop = new Bishop(coordinate, Alliance.BLACK);
    }

    @Test
    void constructor() {
        assertThat(new Bishop(coordinate, Alliance.BLACK)).matches(Bishop::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(bishop.getPieceType()).isEqualTo(Piece.PieceType.BISHOP);
    }

    @Test
    void diagonalMove() {
        assertThat(bishop.getMoveVectors().contains(Vector.Diagonal.UP_RIGHT.getVector()))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(bishop.getMoveVectors().contains(Vector.Vertical.UP.getVector()))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(bishop.move(move))
                .isInstanceOf(Bishop.class)
                .matches(bishop -> bishop.getPosition().equals(destination))
                .matches(bishop -> !bishop.isFirstMove());
    }
}
