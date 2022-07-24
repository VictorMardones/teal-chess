/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.board.Tile;
import engine.move.Move;
import engine.move.PawnCaptureMove;
import engine.move.PawnJump;
import engine.move.PawnMove;
import engine.player.Alliance;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The pawn piece. It only moves forward (depending on the side) and can eat other pieces
 * diagonally. A very weak piece, but it can be promoted when getting to the last rank at the
 * opposite side.
 */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
@Slf4j
public class Pawn implements JumpingPiece {

    private static final Collection<int[]> WHITE_OFFSETS = calculateWhiteOffsets();
    private static final Collection<int[]> BLACK_OFFSETS = calculateBlackOffsets();

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Pawn(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Optional<Move> createMove(Tile destination, Board board) {
        if (isCaptureMove(destination)) {
            return createCaptureMove(board, destination.getCoordinate());
        }

        if (isFirstMovePossible(board)) {
            return createJumpMove(board, destination.getCoordinate());
        }

        return createForwardMove(board, destination.getCoordinate());
    }

    private boolean isCaptureMove(Tile destination) {
        return !getPosition().sameColumnAs(destination.getCoordinate());
    }

    private Optional<Move> createCaptureMove(Board board, Coordinate destination) {
        final var capturablePiece = board.getTile(destination).getPiece();

        if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
            return Optional.of(new PawnCaptureMove(board, this, destination, capturablePiece.get()));
        }

        return Optional.empty();
    }

    private Optional<Move> createJumpMove(Board board, Coordinate destination) {
        return Optional.of(new PawnJump(board, this, destination));
    }

    private boolean isFirstMovePossible(final Board board) {

        var forward = position.down(alliance.getDirection());
        var destination = position.down(2 * alliance.getDirection());

        if (forward.isEmpty() || destination.isEmpty()) {
            return false;
        }

        return isFirstMove()
                && board.getTile(forward.get()).getPiece().isEmpty()
                && board.getTile(destination.get()).getPiece().isEmpty();
    }

    private Optional<Move> createForwardMove(Board board, Coordinate destination) {
        if (board.getTile(destination).getPiece().isPresent()) {
            return Optional.empty();
        }

        // TODO: Deal with promotions
        return Optional.of(new PawnMove(board, this, destination));
    }

    @Override
    public Pawn move(final Move move) {
        return new Pawn(move.getDestination(), alliance, false);
    }

    @Override
    public Collection<int[]> getMoveOffsets() {
        return switch (getAlliance()) {
            case BLACK -> BLACK_OFFSETS;
            case WHITE -> WHITE_OFFSETS;
        };
    }

    private static Collection<int[]> calculateWhiteOffsets() {
        return Stream.of(Vector.Jump.UP, Vector.Vertical.UP, Vector.Diagonal.UP_LEFT, Vector.Diagonal.UP_RIGHT)
                .map(Vector::getVector)
                .collect(ImmutableList.toImmutableList());
    }

    private static Collection<int[]> calculateBlackOffsets() {
        return Stream.of(Vector.Jump.DOWN, Vector.Vertical.DOWN, Vector.Diagonal.DOWN_LEFT, Vector.Diagonal.DOWN_RIGHT)
                .map(Vector::getVector)
                .collect(ImmutableList.toImmutableList());
    }
}
