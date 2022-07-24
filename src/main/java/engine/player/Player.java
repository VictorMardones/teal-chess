/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import engine.board.Board;
import engine.board.Coordinate;
import engine.move.*;
import engine.piece.King;
import engine.piece.Piece;
import engine.piece.Rook;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

/**
 * The entity that controls the pieces in one side of the board. It can be controlled either by a
 * human or an AI.
 */
@ToString
public abstract class Player {

    @ToString.Exclude
    protected final Board board;

    @Getter
    protected final King king;

    @Getter
    @ToString.Exclude
    protected final Collection<Move> legalMoves;

    private final boolean inCheck;
    private Boolean noEscapeMoves;

    protected Player(Board board, King king, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.king = king;

        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateCastles(opponentMoves)));
        inCheck = !Player.calculateAttacksOnTile(king.getPosition(), opponentMoves)
                .isEmpty();
    }

    protected static Collection<Move> calculateAttacksOnTile(Coordinate kingPosition, Collection<Move> moves) {
        return moves.stream()
                .filter(move -> kingPosition == move.getDestination())
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * Used to check if a specific move can be performed.
     *
     * @param move The move to check
     * @return True if the move is legal
     */
    public boolean isMoveLegal(final Move move) {
        return legalMoves.contains(move);
    }

    /**
     * Checks if the player is in check, which means that the king must be protected.
     *
     * @return True if the player is in check
     */
    public boolean isInCheck() {
        return inCheck;
    }

    /**
     * Checks if the player is in checkmate, which means the game is lost. This happens when the king
     * is in check and there are no escape moves.
     *
     * @return True if the player is in checkmate
     */
    public boolean isInCheckmate() {
        return isInCheck() && hasNoEscapeMoves();
    }

    private boolean hasNoEscapeMoves() {
        if (noEscapeMoves == null) {
            noEscapeMoves = legalMoves.stream()
                    .map(this::makeMove)
                    .noneMatch(transition -> transition.getMoveStatus().isDone());
        }

        return noEscapeMoves;
    }

    /**
     * Checks if the player is in stalemate, which means that game ends in a tie. This happens when
     * the king isn't in check and there are no escape moves.
     *
     * @return True if the player is in stalemate
     */
    public boolean inInStalemate() {
        return !isInCheck() && hasNoEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(board, move, MoveStatus.ILLEGAL);
        }

        final var transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
                transitionBoard.getCurrentPlayer().getOpponent().king.getPosition(),
                transitionBoard.getCurrentPlayer().legalMoves);

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    /**
     * Obtains the player's current pieces on the board.
     *
     * @return The player's active pieces
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Obtains the player's side.
     *
     * @return The player's alliance
     */
    public abstract Alliance getAlliance();

    /**
     * Obtains the player in the other side of the board.
     *
     * @return The opponent
     */
    public abstract Player getOpponent();

    // TODO: Refactor this method, maybe use combinator pattern
    protected Collection<Move> calculateCastles(final Collection<Move> opponentLegalMoves) {

        final List<Move> castles = new ArrayList<>();

        if (!king.isFirstMove() || isInCheck() || king.getPosition().getColumn() != 'e') {
            return ImmutableList.copyOf(castles);
        }

        final var kingPosition = king.getPosition();

        if (isKingSideCastlePossible(kingPosition, opponentLegalMoves)) {
            final var rook =
                    (Rook) board.getTile(kingPosition.right(3).get()).getPiece().get();
            final var kingDestination = kingPosition.right(2).get();
            final var rookDestination = kingPosition.right(1).get();

            if (rook.isFirstMove()) {
                castles.add(new KingSideCastleMove(board, king, kingDestination, rook, rookDestination));
            }
        }

        if (isQueenSideCastlePossible(kingPosition, opponentLegalMoves)) {
            final var rook =
                    (Rook) board.getTile(kingPosition.right(3).get()).getPiece().get();
            final var kingDestination = kingPosition.left(2).get();
            final var rookDestination = kingPosition.left(1).get();

            if (rook.isFirstMove()) {
                castles.add(new QueenSideCastleMove(board, king, kingDestination, rook, rookDestination));
            }
        }

        return ImmutableList.copyOf(castles);
    }

    private boolean isKingSideCastlePossible(Coordinate kingPosition, Collection<Move> opponentLegalMoves) {
        return isTileFree(kingPosition, 1)
                && isTileFree(kingPosition, 2)
                && isTileRook(kingPosition, 3)
                && isUnreachableByEnemy(kingPosition, 1, opponentLegalMoves)
                && isUnreachableByEnemy(kingPosition, 2, opponentLegalMoves);
    }

    private boolean isQueenSideCastlePossible(Coordinate kingPosition, Collection<Move> opponentLegalMoves) {
        return isTileFree(kingPosition, -1)
                && isTileFree(kingPosition, -2)
                && isTileFree(kingPosition, -3)
                && isTileRook(kingPosition, -4)
                && isUnreachableByEnemy(kingPosition, -1, opponentLegalMoves)
                && isUnreachableByEnemy(kingPosition, -2, opponentLegalMoves)
                && isUnreachableByEnemy(kingPosition, -3, opponentLegalMoves);
    }

    private boolean isTileFree(final Coordinate kingPosition, final int offset) {
        final var destination = kingPosition.right(offset);

        return destination.isPresent() && board.containsNothing(destination.get());
    }

    private boolean isUnreachableByEnemy(
            final Coordinate kingPosition, final int offset, Collection<Move> opponentLegalMoves) {
        final var destination = kingPosition.right(offset);

        return destination.isPresent()
                && Player.calculateAttacksOnTile(destination.get(), opponentLegalMoves)
                        .isEmpty();
    }

    private boolean isTileRook(final Coordinate kingPosition, final int offset) {
        final var destination = kingPosition.right(offset);

        return destination.isPresent() && board.contains(destination.get(), Piece.PieceType.ROOK);
    }
}
