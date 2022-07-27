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
import engine.piece.vector.Diagonal;
import engine.piece.vector.Jump;
import engine.piece.vector.Vector;
import engine.piece.vector.Vertical;
import engine.player.Alliance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
            return createCaptureMove(board, destination);
        }

        if (isJumpPossible(board)) {
            return createJumpMove(board, destination);
        }

        return createForwardMove(board, destination);
    }

    private boolean isCaptureMove(Tile destination) {
        return !getPosition().sameColumnAs(destination.getCoordinate());
    }

    private Optional<Move> createCaptureMove(Board board, Tile destination) {
        final var capturablePiece = destination.getPiece();

        if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
            return Optional.of(new PawnCaptureMove(board, this, destination.getCoordinate(), capturablePiece.get()));
        }

        return Optional.empty();
    }

    private Optional<Move> createJumpMove(Board board, Tile destination) {
        return Optional.of(new PawnJump(board, this, destination.getCoordinate()));
    }

    private boolean isJumpPossible(final Board board) {

        var forward = position.up(alliance.getDirection());
        var destination = position.up(2 * alliance.getDirection());

        if (forward.isEmpty() || destination.isEmpty()) {
            return false;
        }

        return isFirstMove()
                && isAccessible(board.getTile(forward.get()))
                && isAccessible(board.getTile(destination.get()));
    }

    private Optional<Move> createForwardMove(Board board, Tile destination) {
        if (destination.getPiece().isPresent()) {
            return Optional.empty();
        }

        // TODO: Deal with promotions
        return Optional.of(new PawnMove(board, this, destination.getCoordinate()));
    }

    @Override
    public Pawn move(final Move move) {
        return new Pawn(move.getDestination(), alliance, false);
    }

    @Override
    public Collection<int[]> getMoveOffsets() {
        return switch (getAlliance()) {
            case BLACK -> calculateBlackOffsets();
            case WHITE -> calculateWhiteOffsets();
        };
    }

    private Collection<int[]> calculateWhiteOffsets() {
        final List<Vector> moves = new ArrayList<>(List.of(Vertical.UP, Diagonal.UP_LEFT, Diagonal.UP_RIGHT));

        if (isFirstMove()) {
            moves.add(Jump.UP);
        }

        return moves.stream().map(Vector::getVector).collect(ImmutableList.toImmutableList());
    }

    private Collection<int[]> calculateBlackOffsets() {
        final List<Vector> moves = new ArrayList<>(List.of(Vertical.DOWN, Diagonal.DOWN_LEFT, Diagonal.DOWN_RIGHT));

        if (isFirstMove()) {
            moves.add(Jump.DOWN);
        }

        return moves.stream().map(Vector::getVector).collect(ImmutableList.toImmutableList());
    }
}
