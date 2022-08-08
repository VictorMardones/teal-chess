/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.launcher;

import cl.vmardones.chess.cli.MainCommand;
import lombok.Generated;
import picocli.CommandLine;

public final class Chess {

  /*
  By default, JaCoCo excludes any method annotated with something that contains "Generated" from test coverage reports.
  lombok.Generated is used for this purpose, to exclude private constructors in utility classes from the reports.
  */
  @Generated
  private Chess() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  public static void main(final String... args) {
    new CommandLine(new MainCommand()).execute(args);
  }
}
