package cl.vmardones.chess.engine.game;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.engine.player.Player;

record Turn (Board board, Alliance moveMaker, Player whitePlayer, Player blackPlayer) {
}
