package launcher;

import gui.Table;
import org.slf4j.impl.SimpleLogger;

public final class Chess {

  private Chess() {
    throw new IllegalStateException("You cannot instantiate me!");
  }

  public static void main() {
    System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "Debug");

    new Table();
  }
}
