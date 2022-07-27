/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.board.Coordinate;
import engine.move.Move;
import engine.piece.vector.Diagonal;
import engine.piece.vector.Horizontal;
import engine.piece.vector.Vertical;
import engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RookTest {

    Rook rook;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        rook = new Rook(coordinate, Alliance.BLACK);
    }

    @Test
    void constructor() {
        assertThat(new Rook(coordinate, Alliance.BLACK)).matches(Rook::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(rook.getPieceType()).isEqualTo(Piece.PieceType.ROOK);
    }

    @Test
    void horizontalMove() {
        assertThat(rook.getMoveVectors()).contains(Horizontal.RIGHT.getVector());
    }

    @Test
    void verticalMove() {
        assertThat(rook.getMoveVectors()).contains(Vertical.UP.getVector());
    }

    @Test
    void illegalMove() {
        assertThat(rook.getMoveVectors()).doesNotContain(Diagonal.DOWN_RIGHT.getVector());
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(rook.move(move))
                .isInstanceOf(Rook.class)
                .matches(rook -> rook.getPosition().equals(destination))
                .matches(rook -> !rook.isFirstMove());
    }
}
