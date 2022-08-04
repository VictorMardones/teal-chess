/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.game;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.BoardService;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.move.MoveTransition;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.engine.player.BlackPlayer;
import cl.vmardones.chess.engine.player.Player;
import cl.vmardones.chess.engine.player.WhitePlayer;
import java.util.Collection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {

  private final GameState gameState;
  private final GameHistory gameHistory;
  private final BoardService boardService;

  public Game() {
    gameState = new GameState();
    gameHistory = new GameHistory();
    boardService = new BoardService();

    registerTurn(createFirstTurn());
  }

  private void registerTurn(final Turn turn) {
    gameState.setCurrentTurn(turn);
    gameHistory.add(gameState.save());
  }

  private Turn createFirstTurn() {
    return createTurn(boardService.createStandardBoard(), Alliance.WHITE);
  }

  private Turn createTurn(final Board board, final Alliance nextMoveMaker) {
    final var whiteLegals = calculateWhiteLegals(board);
    log.debug("White legals: {}", whiteLegals);
    final var blackLegals = calculateBlackLegals(board);
    log.debug("Black legals: {}", blackLegals);

    final var whitePlayer = new WhitePlayer(board, board.getWhiteKing(), whiteLegals, blackLegals);
    log.debug("White player: {}", whitePlayer);
    final var blackPlayer = new BlackPlayer(board, board.getBlackKing(), blackLegals, whiteLegals);
    log.debug("Black player: {}", blackPlayer);

    final var turn = new Turn(board, nextMoveMaker, whitePlayer, blackPlayer);
    registerTurn(turn);

    return turn;
  }

  private Collection<Move> calculateWhiteLegals(final Board board) {
    return boardService.calculateLegals(board, board.getWhitePieces());
  }

  private Collection<Move> calculateBlackLegals(final Board board) {
    return boardService.calculateLegals(board, board.getBlackPieces());
  }

  public Turn createNextTurn(@NonNull final Move move) {
    return createTurn(move.execute(), getOpponent().getAlliance());
  }

  public Board getBoard() {
    return gameState.getCurrentTurn().board();
  }

  public Player getCurrentPlayer() {
    return gameState.getCurrentTurn().getPlayer();
  }

  /**
   * Obtains the player in the other side of the board.
   *
   * @return The opponent
   */
  public Player getOpponent() {
    return gameState.getCurrentTurn().getOpponent();
  }

  public Player getWhitePlayer() {
    return gameState.getCurrentTurn().whitePlayer();
  }

  public Player getBlackPlayer() {
    return gameState.getCurrentTurn().blackPlayer();
  }

  public MoveTransition performMove(@NonNull final Move move) {
    return getCurrentPlayer().makeMove(getCurrentPlayer(), move);
  }
}
