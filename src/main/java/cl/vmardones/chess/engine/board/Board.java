/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.*;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.engine.player.BlackPlayer;
import cl.vmardones.chess.engine.player.Player;
import cl.vmardones.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import java.util.*;
import java.util.stream.IntStream;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/** The game board, made of 8x8 tiles. */
@Slf4j
@ToString
public final class Board {

  public static final int MIN_TILES = 0;
  public static final int MAX_TILES = 64;
  public static final int NUMBER_OF_RANKS = 8;

  private final List<Tile> gameBoard;

  @Getter private final Collection<Piece> whitePieces;

  @Getter private final Collection<Piece> blackPieces;

  @Getter private final Player currentPlayer;

  @Getter private final WhitePlayer whitePlayer;

  @Getter private final BlackPlayer blackPlayer;

  @Getter private final Pawn enPassantPawn;

  private Board(final BoardBuilder builder) {
    gameBoard = createGameBoard(builder);
    log.debug("Current gameboard: {}", gameBoard);

    whitePieces = calculateActivePieces(gameBoard, Alliance.WHITE);
    log.debug("White pieces: {}", whitePieces);
    blackPieces = calculateActivePieces(gameBoard, Alliance.BLACK);
    log.debug("Black pieces: {}", blackPieces);

    enPassantPawn = builder.enPassantPawn;
    log.debug("En passant pawn: {}", enPassantPawn);

    final Collection<Move> whiteLegals = calculateLegals(whitePieces);
    log.debug("White legals: {}", whiteLegals);
    final Collection<Move> blackLegals = calculateLegals(blackPieces);
    log.debug("Black legals: {}", blackLegals);

    whitePlayer = new WhitePlayer(this, builder.whiteKing, whiteLegals, blackLegals);
    log.debug("White player: {}", whitePlayer);
    blackPlayer = new BlackPlayer(this, builder.blackKing, blackLegals, whiteLegals);
    log.debug("Black player: {}", blackPlayer);

    currentPlayer = builder.moveMaker.choosePlayer(List.of(whitePlayer, blackPlayer));
    log.debug("Current player: {}", currentPlayer.getAlliance());
  }

  private List<Tile> createGameBoard(final BoardBuilder builder) {
    return IntStream.range(MIN_TILES, MAX_TILES)
        .mapToObj(Coordinate::of)
        .map(coordinate -> Tile.create(coordinate, builder.boardConfig.get(coordinate)))
        .collect(ImmutableList.toImmutableList());
  }

  /**
   * Creates a standard chessboard, which consists of a rank filled with 8 pawns on each side with a
   * formation of 8 major pieces behind.
   *
   * @return The standard chessboard
   */
  // TODO: Parse a text file to create the board
  public static Board createStandardBoard() {

    final var whiteKing = new King(Coordinate.of("e1"), Alliance.WHITE);
    final var blackKing = new King(Coordinate.of("e8"), Alliance.BLACK);

    final var builder = new BoardBuilder(whiteKing, blackKing);

    builder
        .piece(new Rook(Coordinate.of("a8"), Alliance.BLACK))
        .piece(new Knight(Coordinate.of("b8"), Alliance.BLACK))
        .piece(new Bishop(Coordinate.of("c8"), Alliance.BLACK))
        .piece(new Queen(Coordinate.of("d8"), Alliance.BLACK))
        .piece(blackKing)
        .piece(new Bishop(Coordinate.of("f8"), Alliance.BLACK))
        .piece(new Knight(Coordinate.of("g8"), Alliance.BLACK))
        .piece(new Rook(Coordinate.of("h8"), Alliance.BLACK));

    IntStream.range(8, 16)
        .mapToObj(Coordinate::of)
        .map(coordinate -> new Pawn(coordinate, Alliance.BLACK))
        .forEach(builder::piece);

    IntStream.range(48, 56)
        .mapToObj(Coordinate::of)
        .map(coordinate -> new Pawn(coordinate, Alliance.WHITE))
        .forEach(builder::piece);

    builder
        .piece(new Rook(Coordinate.of("a1"), Alliance.WHITE))
        .piece(new Knight(Coordinate.of("b1"), Alliance.WHITE))
        .piece(new Bishop(Coordinate.of("c1"), Alliance.WHITE))
        .piece(new Queen(Coordinate.of("d1"), Alliance.WHITE))
        .piece(whiteKing)
        .piece(new Bishop(Coordinate.of("f1"), Alliance.WHITE))
        .piece(new Knight(Coordinate.of("g1"), Alliance.WHITE))
        .piece(new Rook(Coordinate.of("h1"), Alliance.WHITE));

    return builder.moveMaker(Alliance.WHITE).build();
  }

  /**
   * The board is a complex object, so this builder is the standard method to create it. Both white
   * and black kings must be supplied for the board to be legal. This builder is intended for
   * creating a new board from scratch.
   *
   * @return The board builder
   */
  public static BoardBuilder builder(final King whiteKing, final King blackKing) {
    return new BoardBuilder(whiteKing, blackKing);
  }

  /**
   * A special builder intended to be used when players make a move. This can only be used after the
   * board has been initialized at least once. It keeps the current state of the board and lets you
   * specify only the differences from the previous turn. The next move maker will be the current
   * player's opponent.
   *
   * @return The next turn builder
   */
  public BoardBuilder nextTurnBuilder() {
    return new BoardBuilder(this);
  }

  public Tile getTile(final Coordinate coordinate) {
    return gameBoard.get(coordinate.index());
  }

  public boolean contains(final Coordinate coordinate, final Piece.PieceType pieceType) {
    final var piece = getTile(coordinate).getPiece();

    return piece.isPresent() && piece.get().getPieceType() == pieceType;
  }

  public boolean containsNothing(final Coordinate coordinate) {
    return getTile(coordinate).getPiece().isEmpty();
  }

  private Collection<Piece> calculateActivePieces(
      final List<Tile> gameBoard, final Alliance alliance) {
    return gameBoard.stream()
        .map(Tile::getPiece)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(piece -> piece.getAlliance() == alliance)
        .collect(ImmutableList.toImmutableList());
  }

  private Collection<Move> calculateLegals(final Collection<Piece> pieces) {
    return pieces.stream()
        .map(piece -> piece.calculateLegals(this))
        .flatMap(Collection::stream)
        .collect(ImmutableList.toImmutableList());
  }

  public Collection<Move> getCurrentPlayerLegals() {
    return ImmutableList.copyOf(currentPlayer.getLegals());
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

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class BoardBuilder {

    private final Map<Coordinate, Piece> boardConfig = new HashMap<>();
    private final King whiteKing;
    private final King blackKing;
    private Alliance moveMaker;
    private Pawn enPassantPawn;

    private BoardBuilder(final Board board) {
      whiteKing = board.getWhitePlayer().getKing();
      blackKing = board.getBlackPlayer().getKing();

      board.getWhitePieces().forEach(this::piece);
      board.getBlackPieces().forEach(this::piece);

      moveMaker(board.getCurrentPlayer().getOpponent().getAlliance());
    }

    public BoardBuilder piece(final Piece piece) {
      boardConfig.put(piece.getPosition(), piece);
      return this;
    }

    public BoardBuilder withoutPiece(final Piece piece) {
      boardConfig.remove(piece.getPosition(), piece);
      return this;
    }

    private BoardBuilder moveMaker(final Alliance moveMaker) {
      this.moveMaker = moveMaker;
      return this;
    }

    public BoardBuilder enPassantPawn(final Pawn pawn) {
      this.enPassantPawn = pawn;
      return this;
    }

    public Board build() {
      Objects.requireNonNull(whiteKing);
      Objects.requireNonNull(blackKing);

      return new Board(this);
    }
  }
}
