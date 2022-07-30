/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.launcher;

import cl.vmardones.chess.gui.Table;
import org.slf4j.impl.SimpleLogger;

public final class Chess {

    private Chess() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    public static void main(String[] args) {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "Debug");

        System.setProperty("awt.useSystemAAFontSettings", "on");

        new Table();
    }
}
