/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.board;

import cl.vmardones.chess.engine.piece.King;
import cl.vmardones.chess.engine.piece.Pawn;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.player.Alliance;
import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.IntStream;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/** The game board, made of 8x8 tiles. */
@Slf4j
@ToString
public class Board {
  public static final int SIDE_LENGTH = 8;
  public static final int MIN_TILES = 0;
  public static final int MAX_TILES = SIDE_LENGTH * SIDE_LENGTH;

  private final List<Tile> tiles;

  @Getter private final Collection<Piece> whitePieces;

  @Getter private final King whiteKing;

  @Getter private final Collection<Piece> blackPieces;

  @Getter private final King blackKing;

  @Getter private final Pawn enPassantPawn;

  private Board(final BoardBuilder builder) {
    tiles = createTiles(builder);
    log.debug("Current gameboard: {}", tiles);

    whiteKing = builder.whiteKing;
    log.debug("White king: {}", whiteKing);
    whitePieces = calculateActivePieces(tiles, Alliance.WHITE);
    log.debug("White pieces: {}", whitePieces);

    blackKing = builder.blackKing;
    log.debug("Black king: {}", blackKing);
    blackPieces = calculateActivePieces(tiles, Alliance.BLACK);
    log.debug("Black pieces: {}", blackPieces);

    enPassantPawn = builder.enPassantPawn;
    log.debug("En passant pawn: {}", enPassantPawn);
  }

  private List<Tile> createTiles(final BoardBuilder builder) {
    return IntStream.range(MIN_TILES, MAX_TILES)
        .mapToObj(Coordinate::of)
        .map(coordinate -> Tile.create(coordinate, builder.boardConfig.get(coordinate)))
        .collect(ImmutableList.toImmutableList());
  }

  private Collection<Piece> calculateActivePieces(
      final List<Tile> gameBoard, final Alliance alliance) {
    return gameBoard.stream()
        .map(Tile::getPiece)
        .flatMap(Optional::stream)
        .filter(piece -> piece.getAlliance() == alliance)
        .collect(ImmutableList.toImmutableList());
  }

  /* Methods for checking the board */

  public Tile getTile(@NotNull final Coordinate coordinate) {
    return tiles.get(coordinate.index());
  }

  public boolean contains(
      @NotNull final Coordinate coordinate, @NotNull final Piece.PieceType pieceType) {
    final var piece = getTile(coordinate).getPiece();

    return piece.isPresent() && piece.get().getPieceType() == pieceType;
  }

  public boolean containsNothing(@NotNull final Coordinate coordinate) {
    return getTile(coordinate).getPiece().isEmpty();
  }

  /* Board builders */

  /**
   * The board is a complex object, so this builder is the standard method to create it. Both white
   * and black kings must be supplied for the board to be legal. This builder is intended for
   * creating a new board from scratch.
   *
   * @return The board builder
   */
  public static BoardBuilder builder(@NotNull final King whiteKing, @NotNull final King blackKing) {
    return new BoardBuilder(whiteKing, blackKing);
  }

  /**
   * A special builder intended to be used when players make a move. This can only be used after the
   * board has been initialized at least once. It keeps the current state of the board and lets you
   * specify only the differences from the previous turn.
   *
   * @return The next turn builder
   */
  public BoardBuilder nextTurnBuilder() {
    return new BoardBuilder(this);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class BoardBuilder {

    private final Map<Coordinate, Piece> boardConfig = new HashMap<>();
    private final King whiteKing;
    private final King blackKing;
    private Pawn enPassantPawn;

    private BoardBuilder(final Board board) {
      whiteKing = board.whiteKing;
      blackKing = board.blackKing;

      board.getWhitePieces().forEach(this::piece);
      board.getBlackPieces().forEach(this::piece);
    }

    public BoardBuilder piece(final Piece piece) {
      boardConfig.put(piece.getPosition(), piece);
      return this;
    }

    public BoardBuilder withoutPiece(final Piece piece) {
      boardConfig.remove(piece.getPosition(), piece);
      return this;
    }

    public BoardBuilder enPassantPawn(final Pawn pawn) {
      this.enPassantPawn = pawn;
      return this;
    }

    public Board build() {
      return new Board(this);
    }
  }
}
