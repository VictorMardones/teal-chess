package player;

import board.Board;
import board.Move;
import piece.King;
import piece.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;

    public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        king = establishKing();
    }

    private King establishKing() {
        return getActivePieces().stream()
                .filter(Piece::isKing)
                .findFirst()
                .map(piece -> (King) piece)
                .orElseThrow(() -> new IllegalStateException("You can't play without a king!"));
    }

    public abstract Collection<Piece> getActivePieces();
}
