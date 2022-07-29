/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.board.Tile;
import engine.move.CaptureMove;
import engine.move.MajorMove;
import engine.move.Move;
import engine.player.Alliance;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A chess piece.
 */
public interface Piece {

    Coordinate getPosition();

    Alliance getAlliance();

    PieceType getPieceType();

    boolean isFirstMove();

    /**
     * Calculates all the moves that a piece can do.
     *
     * @param board Current state of the game board
     * @return List of possible moves
     */
    default Collection<Move> calculateLegals(final Board board) {
        return calculatePossibleDestinations(board).stream()
                .map(board::getTile)
                .filter(this::isAccessible)
                .map(tile -> createMove(tile, board))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }

    Collection<Coordinate> calculatePossibleDestinations(final Board board);

    default boolean isInMoveRange(final Board board, Coordinate coordinate) {
        return calculateLegals(board).stream()
                .map(Move::getDestination)
                .anyMatch(destination -> destination == coordinate);
    }

    default boolean isWhite() {
        return getAlliance() == Alliance.WHITE;
    }

    default boolean isBlack() {
        return !isWhite();
    }

    default boolean isEnemyOf(Piece other) {
        if (other == null) {
            return false;
        }

        return getAlliance() != other.getAlliance();
    }

    default String toChar() {
        return getPieceType().pieceName;
    }

    default boolean isRook() {
        return getPieceType() == PieceType.ROOK;
    }

    /**
     * Checks if the given piece can get the destination. This happens only if the destination is free
     * or has a piece that can be captured.
     *
     * @param destination The target destination
     * @return True if the piece can get to the destination
     */
    default boolean isAccessible(final Tile destination) {
        final var pieceAtDestination = destination.getPiece();
        return pieceAtDestination.isEmpty() || isEnemyOf(pieceAtDestination.get());
    }

    Piece move(final Move move);

    /**
     * Creates a move, based on the piece and the destination.
     *
     * @param destination The destination tile
     * @param board       The current game board
     * @return A move, selected depending on the source and destination
     */
    default Optional<Move> createMove(final Tile destination, final Board board) {
        if (destination.getPiece().isEmpty()) {
            return Optional.of(new MajorMove(board, this, destination.getCoordinate()));
        }

        final var capturablePiece = destination.getPiece();

        if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
            return Optional.of(new CaptureMove(board, this, destination.getCoordinate(), capturablePiece.get()));
        }

        return Optional.empty();
    }

    @AllArgsConstructor
    @Getter
    enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;
    }
}
