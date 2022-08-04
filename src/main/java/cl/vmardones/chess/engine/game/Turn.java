/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.game;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.engine.player.Player;
import com.google.common.collect.ImmutableList;
import java.util.Collection;

record Turn(Board board, Alliance moveMaker, Player whitePlayer, Player blackPlayer) {

  Player getPlayer() {
    return switch (moveMaker) {
      case WHITE -> whitePlayer;
      case BLACK -> blackPlayer;
    };
  }

  Collection<Move> getPlayerLegals() {
    return ImmutableList.copyOf(getPlayer().getLegals());
  }

  Player getOpponent() {
    return switch (moveMaker) {
      case WHITE -> blackPlayer;
      case BLACK -> whitePlayer;
    };
  }

  Collection<Move> getOpponentLegals() {
    return ImmutableList.copyOf(getOpponent().getLegals());
  }
}
