/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package io;

import engine.piece.Piece;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

public final class PieceIconLoader {

    private static final String PIECE_ICON_PATH = "art/pieces";

    public static Optional<BufferedImage> loadIcon(final Piece piece, final int width, final int height) {
        return SvgImporter.importSvg(new File(getIconPath(piece)), width, height);
    }

    private static String getIconPath(final Piece piece) {
        return "%s/%s%s.svg"
                .formatted(PIECE_ICON_PATH, piece.getAlliance(), piece.toChar().toLowerCase());
    }
}
