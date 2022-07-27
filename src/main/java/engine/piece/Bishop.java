/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import engine.move.Move;
import engine.piece.vector.Diagonal;
import engine.piece.vector.Vector;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The bishop piece. It can move diagonally.
 */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Bishop implements SlidingPiece {

    private static final Collection<int[]> MOVE_VECTORS = calculateMoveVectors();

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Bishop(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Bishop move(final Move move) {
        return new Bishop(move.getDestination(), alliance, false);
    }

    @Override
    public Collection<int[]> getMoveVectors() {
        return MOVE_VECTORS;
    }

    private static Collection<int[]> calculateMoveVectors() {
        return Arrays.stream(Diagonal.values()).map(Vector::getVector).collect(ImmutableList.toImmutableList());
    }
}
