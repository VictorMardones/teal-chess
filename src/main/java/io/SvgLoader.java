/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package io;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SvgLoader {

    public static Optional<BufferedImage> load(final String path, final int width, final int height) {

        final var iconResource = ResourceImporter.get(path);

        try {
            return SvgImporter.get(iconResource, width, height);
        } catch (IOException e) {
            log.warn("Could not load the SVG file " + path);
            return Optional.empty();
        }
    }
}
