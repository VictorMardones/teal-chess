package engine.board;

import com.google.common.collect.ImmutableList;
import engine.move.Move;
import engine.piece.*;
import engine.player.Alliance;
import engine.player.BlackPlayer;
import engine.player.Player;
import engine.player.WhitePlayer;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The game board, made of 8x8 tiles.
 */
@Slf4j
@ToString
public final class Board {

    private final List<Tile> gameBoard;
    @Getter
    private final Collection<Piece> whitePieces;
    @Getter
    private final Collection<Piece> blackPieces;

    @Getter
    private final Player currentPlayer;
    @Getter
    private final WhitePlayer whitePlayer;
    @Getter
    private final BlackPlayer blackPlayer;

    private Board(Builder builder) {
        gameBoard = createGameBoard(builder);
        log.debug("Current gameboard: {}", gameBoard);

        whitePieces = calculateActivePieces(gameBoard, Alliance.WHITE);
        log.debug("White pieces: {}", whitePieces);
        blackPieces = calculateActivePieces(gameBoard, Alliance.BLACK);
        log.debug("Black pieces: {}", blackPieces);

        final Collection<Move> whiteLegalMoves = calculateLegalMoves(whitePieces);
        log.debug("White legal moves: {}", whiteLegalMoves);
        final Collection<Move> blackLegalMoves = calculateLegalMoves(blackPieces);
        log.debug("Black legal moves: {}", blackLegalMoves);

        whitePlayer = new WhitePlayer(this, builder.whiteKing, whiteLegalMoves, blackLegalMoves);
        log.debug("White player: {}", whitePlayer);
        blackPlayer = new BlackPlayer(this, builder.blackKing, blackLegalMoves, whiteLegalMoves);
        log.debug("Black player: {}", blackPlayer);

        currentPlayer = builder.nextTurn.choosePlayer(ImmutableList.of(whitePlayer, blackPlayer));
        log.debug("Current player: {}", currentPlayer.getAlliance());
    }

    private List<Tile> createGameBoard(final Builder builder) {
        return IntStream.range(BoardUtils.MIN_TILES, BoardUtils.MAX_TILES)
                .mapToObj(i -> Tile.create(i, builder.boardConfig.get(i)))
                .collect(ImmutableList.toImmutableList());
    }

    // TODO: Parse a text file to create the board
    public static Board createStandardBoard() {
        final var whiteKing = new King(60, Alliance.WHITE);
        final var blackKing = new King(4, Alliance.BLACK);

        final var builder = new Builder(whiteKing, blackKing);

        builder.withPiece(new Rook(0, Alliance.BLACK))
                .withPiece(new Knight(1, Alliance.BLACK))
                .withPiece(new Bishop(2, Alliance.BLACK))
                .withPiece(new Queen(3, Alliance.BLACK))
                .withPiece(blackKing)
                .withPiece(new Bishop(5, Alliance.BLACK))
                .withPiece(new Knight(6, Alliance.BLACK))
                .withPiece(new Rook(7, Alliance.BLACK));

        IntStream.range(8, 16)
                .forEach(i -> builder.withPiece(new Pawn(i, Alliance.BLACK)));

        IntStream.range(48, 56)
                .forEach(i -> builder.withPiece(new Pawn(i, Alliance.WHITE)));

        builder.withPiece(new Rook(56, Alliance.WHITE))
                .withPiece(new Knight(57, Alliance.WHITE))
                .withPiece(new Bishop(58, Alliance.WHITE))
                .withPiece(new Queen(59, Alliance.WHITE))
                .withPiece(whiteKing)
                .withPiece(new Bishop(61, Alliance.WHITE))
                .withPiece(new Knight(62, Alliance.WHITE))
                .withPiece(new Rook(63, Alliance.WHITE));

        return builder.withNextTurn(Alliance.WHITE)
                .build();
    }

    public Tile getTile(final int coordinate) {
        return gameBoard.get(coordinate);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        return gameBoard.stream()
                .filter(Tile::isOccupied)
                .map(Tile::getPiece)
                .filter(tile -> tile.getAlliance() == alliance)
                .collect(ImmutableList.toImmutableList());
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        return pieces.stream()
                .map(piece -> piece.calculateLegalMoves(this))
                .flatMap(Collection::stream)
                .collect(ImmutableList.toImmutableList());
    }

    public Collection<Move> getCurrentPlayerLegalMoves() {
        return ImmutableList.copyOf(currentPlayer.getLegalMoves());
    }

    public String toText() {
        final var builder = new StringBuilder();

        IntStream.range(BoardUtils.MIN_TILES, BoardUtils.MAX_TILES)
                .mapToObj(i -> String.format(getFormat(i), gameBoard.get(i)))
                .forEach(builder::append);

        return builder.toString();
    }

    private String getFormat(final int coordinate) {
        return (coordinate + 1) % BoardUtils.NUMBER_OF_RANKS == 0 ? "%s \n" : "%s ";
    }

    public static class Builder {

        private final Map<Integer, Piece> boardConfig;
        private Alliance nextTurn;
        private King whiteKing;
        private King blackKing;

        public Builder(King whiteKing, King blackKing) {
            boardConfig = new HashMap<>();
            this.whiteKing = whiteKing;
            this.blackKing = blackKing;
        }

        public Builder withPiece(final Piece piece) {
            boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        public Builder withNextTurn(final Alliance nextTurn) {
            this.nextTurn = nextTurn;
            return this;
        }

        public Builder withWhiteKing(final King whiteKing) {
            this.whiteKing = whiteKing;
            return this;
        }

        public Builder withBlackKing(final King blackKing) {
            this.blackKing = blackKing;
            return this;
        }

        public Builder withEnPassantPawn(Pawn pawn) {
            // TODO: Implement this
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
