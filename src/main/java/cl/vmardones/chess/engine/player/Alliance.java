/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.player;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/** Represents the chess piece's color, which can be white or black. */
@AllArgsConstructor
@Getter
public enum Alliance {
  /** The white side, on the bottom of the board. */
  WHITE(1),
  /** The black side, on the top of the board. */
  BLACK(-1);

  private final int direction;

  /**
   * Chooses the first player of this alliance.
   *
   * @param players Players to choose from
   * @return The chosen player
   */
  public Player choosePlayer(@NonNull final Collection<Player> players) {
    return players.stream().filter(player -> player.getAlliance() == this).findFirst().orElse(null);
  }

  public Alliance getOpposite() {
    return switch (this) {
      case WHITE -> BLACK;
      case BLACK -> WHITE;
    };
  }

  public int getOppositeDirection() {
    return getOpposite().getDirection();
  }

  @Override
  public String toString() {
    return "" + super.toString().toLowerCase().charAt(0);
  }
}
