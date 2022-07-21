package launcher;

import gui.Table;
import org.slf4j.impl.SimpleLogger;

public final class Chess {
    public static void main(String[] args) {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "Debug");

        new Table();
    }
}
