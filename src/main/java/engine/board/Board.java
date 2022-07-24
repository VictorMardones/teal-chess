package engine.board;

import com.google.common.collect.ImmutableList;
import engine.move.Move;
import engine.piece.*;
import engine.player.Alliance;
import engine.player.BlackPlayer;
import engine.player.Player;
import engine.player.WhitePlayer;
import java.util.*;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/** The game board, made of 8x8 tiles. */
@Slf4j
@ToString
public class Board {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 64;
    public static final int NUMBER_OF_RANKS = 8;

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

        currentPlayer = builder.nextTurn.choosePlayer(List.of(whitePlayer, blackPlayer));
        log.debug("Current player: {}", currentPlayer.getAlliance());
    }

    private List<Tile> createGameBoard(final Builder builder) {
        return IntStream.range(MIN_TILES, MAX_TILES)
                .mapToObj(Coordinate::of)
                .map(coordinate -> Tile.create(coordinate, builder.boardConfig.get(coordinate)))
                .collect(ImmutableList.toImmutableList());
    }

    // TODO: Parse a text file to create the board
    public static Board createStandardBoard() {
        final var whiteKing = new King(Coordinate.of("e1"), Alliance.WHITE);
        final var blackKing = new King(Coordinate.of("e8"), Alliance.BLACK);

        final var builder = new Builder(whiteKing, blackKing);

        builder.withPiece(new Rook(Coordinate.of("a8"), Alliance.BLACK))
                .withPiece(new Knight(Coordinate.of("b8"), Alliance.BLACK))
                .withPiece(new Bishop(Coordinate.of("c8"), Alliance.BLACK))
                .withPiece(new Queen(Coordinate.of("d8"), Alliance.BLACK))
                .withPiece(blackKing)
                .withPiece(new Bishop(Coordinate.of("f8"), Alliance.BLACK))
                .withPiece(new Knight(Coordinate.of("g8"), Alliance.BLACK))
                .withPiece(new Rook(Coordinate.of("h8"), Alliance.BLACK));

        IntStream.range(8, 16)
                .mapToObj(Coordinate::of)
                .map(coordinate -> new Pawn(coordinate, Alliance.BLACK))
                .forEach(builder::withPiece);

        IntStream.range(48, 56)
                .mapToObj(Coordinate::of)
                .map(coordinate -> new Pawn(coordinate, Alliance.WHITE))
                .forEach(builder::withPiece);

        builder.withPiece(new Rook(Coordinate.of("a1"), Alliance.WHITE))
                .withPiece(new Knight(Coordinate.of("b1"), Alliance.WHITE))
                .withPiece(new Bishop(Coordinate.of("c1"), Alliance.WHITE))
                .withPiece(new Queen(Coordinate.of("d1"), Alliance.WHITE))
                .withPiece(whiteKing)
                .withPiece(new Bishop(Coordinate.of("f1"), Alliance.WHITE))
                .withPiece(new Knight(Coordinate.of("g1"), Alliance.WHITE))
                .withPiece(new Rook(Coordinate.of("h1"), Alliance.WHITE));

        return builder.withNextTurn(Alliance.WHITE).build();
    }

    public Tile getTile(Coordinate coordinate) {
        return gameBoard.get(coordinate.index());
    }

    public boolean contains(Coordinate coordinate, Piece.PieceType pieceType) {
        var piece = getTile(coordinate).getPiece();

        return piece.isPresent() && piece.get().getPieceType() == pieceType;
    }

    public boolean containsNothing(Coordinate coordinate) {
        return getTile(coordinate).getPiece().isEmpty();
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        return gameBoard.stream()
                .map(Tile::getPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(piece -> piece.getAlliance() == alliance)
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

        IntStream.range(MIN_TILES, MAX_TILES)
                .mapToObj(Coordinate::of)
                .map(coordinate -> String.format(getFormat(coordinate), gameBoard.get(coordinate.index())))
                .forEach(builder::append);

        return builder.toString();
    }

    private String getFormat(final Coordinate coordinate) {
        return (coordinate.index() + 1) % NUMBER_OF_RANKS == 0 ? "%s  \n" : "%s  ";
    }

    public static class Builder {

        private final Map<Coordinate, Piece> boardConfig;
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
