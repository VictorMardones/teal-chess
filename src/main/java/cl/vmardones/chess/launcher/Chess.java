/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.launcher;

import cl.vmardones.chess.cli.MainCommand;
import picocli.CommandLine;

public final class Chess {

  private Chess() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  public static void main(final String... args) {
    new CommandLine(new MainCommand()).execute(args);
  }
}
