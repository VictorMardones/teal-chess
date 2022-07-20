package launcher;

import gui.Table;

public final class Chess {
    public static void main(String[] args) {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "Debug");

        new Table();
    }
}
