/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.io;

import jakarta.validation.constraints.NotNull;
import java.io.InputStream;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class ResourceImporter {

  @Generated
  private ResourceImporter() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  static InputStream get(@NotNull final String path) {
    return ResourceImporter.class.getClassLoader().getResourceAsStream(path);
  }
}
