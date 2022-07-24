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
class KingTest {

    King king;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        king = new King(coordinate, Alliance.WHITE);
    }

    @Test
    void constructor() {
        assertThat(new King(coordinate, Alliance.BLACK)).matches(King::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(king.getPieceType()).isEqualTo(Piece.PieceType.KING);
    }

    @Test
    void diagonalMove() {
        assertThat(king.getMoveOffsets().contains(Vector.Diagonal.UP_RIGHT.getVector()))
                .isTrue();
    }

    @Test
    void horizontalMove() {
        assertThat(king.getMoveOffsets().contains(Vector.Horizontal.LEFT.getVector()))
                .isTrue();
    }

    @Test
    void verticalMove() {
        assertThat(king.getMoveOffsets().contains(Vector.Vertical.UP.getVector()))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(king.getMoveOffsets().contains(Vector.LShaped.UP_UP_LEFT.getVector()))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(king.move(move))
                .isInstanceOf(King.class)
                .matches(king -> king.getPosition().equals(destination))
                .matches(king -> !king.isFirstMove());
    }
}
