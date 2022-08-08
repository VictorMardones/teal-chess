/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.io;

import cl.vmardones.chess.engine.piece.Piece;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import lombok.Generated;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PieceIconLoader {

  private static final String PIECE_ICON_PATH = "art/pieces";

  @Generated
  private PieceIconLoader() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  public static Optional<BufferedImage> load(
      @NonNull final Piece piece, final int width, final int height) {

    final var iconResource = ResourceImporter.get(getIconPath(piece));

    try {
      return SvgImporter.get(iconResource, width, height);
    } catch (final IOException e) {
      log.warn("Could not load the piece {}", getIconPath(piece));
      return Optional.empty();
    }
  }

  private static String getIconPath(final Piece piece) {
    return "%s/%s%s.svg"
        .formatted(PIECE_ICON_PATH, piece.getAlliance(), piece.toSingleChar().toLowerCase());
  }
}
