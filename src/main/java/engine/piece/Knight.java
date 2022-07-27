/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import engine.move.Move;
import engine.piece.vector.LShaped;
import engine.piece.vector.Vector;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * The knight piece. It moves in an L shape.
 */
@AllArgsConstructor
@Getter
@ToString(includeFieldNames = false)
public class Knight implements JumpingPiece {

    private static final Collection<int[]> MOVE_OFFSETS = calculateMoveOffsets();

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Knight(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Knight move(final Move move) {
        return new Knight(move.getDestination(), alliance, false);
    }

    @Override
    public Collection<int[]> getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    private static Collection<int[]> calculateMoveOffsets() {
        return Arrays.stream(LShaped.values()).map(Vector::getVector).collect(ImmutableList.toImmutableList());
    }
}
