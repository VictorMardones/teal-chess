/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.launcher;

import cl.vmardones.chess.cli.MainCommand;
import picocli.CommandLine;

public final class Chess {

    private Chess() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    public static void main(String... args) {
        new CommandLine(new MainCommand()).execute(args);
    }
}
