/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.io;

import jakarta.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SvgLoader {

  @Generated
  private SvgLoader() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  public static Optional<BufferedImage> load(
      @NotNull final String path, final int width, final int height) {

    final var iconResource = ResourceImporter.get(path);

    try {
      return SvgImporter.get(iconResource, width, height);
    } catch (final IOException e) {
      log.warn("Could not load the SVG file {}", path);
      return Optional.empty();
    }
  }
}
