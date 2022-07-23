package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** A piece that can move in a specific set of spaces. */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class JumpingPiece implements Piece {

  protected Coordinate position;
  protected Alliance alliance;
  protected boolean firstMove;


}
